package com.bumil.asynctask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {

        super.onStart();

        EditText editId = (EditText) findViewById(R.id.edit_id); //아이디 불러오기
        EditText editPw = (EditText) findViewById(R.id.edit_password);

        editId.setText(null);
        editPw.setText(null);
        editPw.clearFocus();
        editId.clearFocus();

    }

    public void onClick(View view) {
        //Toast.makeText(MainActivity.this, "'로그인'버튼 클릭하였습니다.", Toast.LENGTH_SHORT).show();

        EditText editId = (EditText) findViewById(R.id.edit_id); //아이디 불러오기
        String id = editId.getText().toString();

        EditText editPw = (EditText) findViewById(R.id.edit_password);
        String pw = editPw.getText().toString();

        ContentValues parcel = new ContentValues();
        parcel.put("userId", id);
        parcel.put("userPw", pw);

        String url = "http://172.17.17.153:8080/smartcity/androidTest/login"; //회사
        //String url = "http://192.168.55.68:8080/smartcity/androidTest/login"; //집


        NetworkTask networkTask = new NetworkTask(url, parcel);
        networkTask.execute();

    }

    public void register(View view) {
        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
         MainActivity.this.startActivity(registerIntent);
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON_RESULT", s);
            Gson gson = new Gson();
            Data data = gson.fromJson(s, Data.class);

            Log.d("JSON_RESULT", data.getResult());
            String returnVal = data.getResult();
            EditText editId = (EditText) findViewById(R.id.edit_id); //아이디 창 가져오기
            EditText editPw = (EditText) findViewById(R.id.edit_password); //비밀번호 창 가져오기
            TextView msg = (TextView) findViewById(R.id.login_msg);

            if(returnVal.equals("success")){ //로그인 성공
                //다음페이지로 이동 후 토스트에 '***님(id) 로그인 하였습니다.' 표시
                //페이지에 '*** 님 환영합니다.' 표시
                //인텐트에 사용자 정의 클래스를 넣어 두번째 액티비티에 보낸다.

                String id = editId.getText().toString(); //아이디 불러오기

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("로그인 성공")
                        .setNegativeButton("확인", null)
                        .create()
                        .show();

                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("userId", id);
                startActivity(intent);

            }else if(returnVal.equals("inCorrectUserInfo")){ //아이디 or 비밀번호가 잘못되었을 경우
                //id, pw 초기화
                //'아이디 or 비밀번호 가 잘못되었습니다. 확인 후 다시 입력해주세요.' 문구 다이얼로그로 뜨기
                editId.setText(null);
                editPw.setText(null);
                editPw.clearFocus();
                editId.clearFocus();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("아이디 or 비밀번호 가 잘못되었습니다.\n확인 후 다시 입력해주세요.")
                        .setNegativeButton("다시 시도", null)
                        .create()
                        .show();

            }else if(returnVal.equals("unkownUser")){ //아이디 or 비밀번호가 잘못되었을 경우
                //id, pw 초기화
                //'존재 하지 않는 아이디 입니다. 회원가입 후 이용해주세요.' 문구 다이얼로그로 뜨기
                editId.setText(null);
                editPw.setText(null);
                editPw.clearFocus();
                editId.clearFocus();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("존재 하지 않는 아이디 입니다.\n회원가입 후 이용해주세요.")
                        .setNegativeButton("확인", null)
                        .create()
                        .show();

            }else if(returnVal.equals("enabledUser")){ //사용하지 않는 사용자 경우
                //id, pw 초기화
                //'해당 사용자는 현재 사용중지 되어있습니다. 해제 후 이용 해주시기 바랍니다.' 문구 다이얼로그로 뜨기
                editId.setText(null);
                editPw.setText(null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("해당 사용자는 현재 사용중지 되어있습니다.\n해제 후 이용 해주시기 바랍니다.")
                        .setNegativeButton("다시 시도", null)
                        .create()
                        .show();

            }

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            String text = s;


        }
    }

}
