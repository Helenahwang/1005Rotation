package com.appdev.a503_02.a1005rotation;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileInputOutputStream extends AppCompatActivity {

    public void click(View v){
        final EditText edit = (EditText)findViewById(R.id.edit);
        switch (v.getId()){
            case R.id.idsavebtn:
                //입력한 내용 가져오기
                String msg = edit.getText().toString();

                //기록하기 위한 파일 스트림 만들기 - close를 하지 않아도 자동으로 닫히도록 try( )안에서 생성
                try(FileOutputStream fos1 = openFileOutput("sample.txt", MODE_PRIVATE);)
                {
                    fos1.write(msg.getBytes());
                    fos1.flush();
                    edit.setText("파일에 문자열 기록 성공");

                }catch(Exception e){}

                break;

            case R.id.idreadbtn:

                try(FileInputStream fis1 = openFileInput("sample.txt")){

                    //파일의 크기 만큼의 배열을 생서
                    byte [] b = new byte[fis1.available()];
                    //파일 내용을 b에 저장
                    fis1.read(b);
                    //파일 내용을 문자열로 변환해서 출력
                    edit.setText(new String(b));


                }catch(Exception e){
                    edit.setText("파일이 존재하지 않습니다.");
                }

                break;

            case R.id.iddeletebtn:

                boolean result = deleteFile("sample.txt");
                if(result == true)
                    edit.setText("삭제 성공");
                else
                    edit.setText("삭제 실패");
                break;


            case R.id.idreadresourcebtn:

                try(InputStream fis2 = getResources().openRawResource(R.raw.res)){
                 /*

                 try(InputStream fis2 = getResources().openRawResource(R.raw.res);


                    //읽기 스트림을 가지고 문자열 단위로 읽을 수 있는 문자 읽기 스트림 만들기
                    //문자 읽기 스트림 만들기
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis2)))
                {
                    //읽은 내용을 저장할 수 있는 문자열 객체 생성
                    //읽은 내용을 추가할 수 있도록 하기 위해서 생성
                    StringBuilder sb = new StringBuilder();
                    while(true){
                        String line = br.readLine();
                        if(line == null) break;
                        sb.append(line+"\n");
                    }
                    String message = sb.toString();
                    edit.setText(message);

                    */




                    byte [] b = new byte[fis2.available()];
                    fis2.read(b);
                    edit.setText(new String(b));





                }catch (Exception e){
                    edit.setText(e.getMessage());
                }

                break;


            case R.id.imagecopy:
                try(InputStream fis = getResources().openRawResource(R.raw.dongwooklee);
                    FileOutputStream fos = openFileOutput("dongwooklee2.jpg", Context.MODE_PRIVATE);
                ){
                    while(true){
                        byte [] b = new byte[1024];
                        //fis에서 읽어서 b에 저장
                        int r = fis.read(b);
                        //읽은 게 없으면 종료
                        if(r<=0) break;

                        //읽은 내용이 있으면 fos에 기록
                        fos.write(b,0, r);

                    }
                }catch (Exception e){ }

                break;
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_input_output_stream);
    }
}
