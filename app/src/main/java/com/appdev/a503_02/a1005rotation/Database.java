package com.appdev.a503_02.a1005rotation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


//데이터베이스 클래스(SQLiteOpenHelper 상속)
class WordDBHelper extends SQLiteOpenHelper{

    //생성자
    public WordDBHelper(Context context){
        super(context, "engword.db", null, 1); //engword.db는 저장할 파일명
    }

    //처음 사용될 때 호출되는 메소드
    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블을 생성하는 SQL 실행
        db.execSQL("create table dic(id integer primary key autoincrement, eng text, kor text);");
    }

    //버전이 변경되면 호출되는 메소드
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion){
        //테이블 삭제
        db.execSQL("drop table if exists dic");

        //테이블 다시 생성
        onCreate(db);

    }

}


public class Database extends AppCompatActivity {

    private WordDBHelper dbHelper;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        dbHelper = new WordDBHelper(this);
        result = (TextView)findViewById(R.id.txview);

        //Log.e("tag1", "이벤트발생");
        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                //SQL로 insert
                //db.execSQL("insert into dic(eng,kor) values('apple','사과');");

                //SQL을 이용하지 않고 데이터를 삽입
                ContentValues values = new ContentValues();
                values.put("eng", "orange");
                values.put("kor", "오렌지");
                db.insert("dic", null, values);

                result.setText("삽입 성공");
                dbHelper.close();

            }
        });

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor cursor=db.rawQuery("select * from dic",null); //? 있으면 넣을 곳, ?가 없으면 null
                String Result="";
                //데이터 전체 순회
                while(cursor.moveToNext()){
                    String eng=cursor.getString(1);
                    String han=cursor.getString(2);
                    Result = Result + (eng+", "+han+"\n");
                }
                if(Result.length()==0){
                    result.setText("데이터가 없다");
                }else{
                    result.setText(Result);
                }
                cursor.close();

            }
        });

        //갱신 버튼을 눌렀을 때 - eng 값이 orange 인 데이터를 kor 값을 글로 변경하기
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update dic set kor = '귤' where eng ='orange'");
                dbHelper.close();
                result.setText("수정 성공");


            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("delete from dic where eng = 'orange'");
                dbHelper.close();
                result.setText("삭제 성공");
            }
        });



    }
}
