package com.bumil.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        TextView title = (TextView) findViewById(R.id.edit_title);
        title.setSelected(true);
        Button managementButton = (Button) findViewById(R.id.managementButton);

        //다음페이지로 이동 후 토스트에 '***님(id) 로그인 하였습니다.' 표시
        //페이지에 '*** 님 환영합니다.' 표시

        if(userId.equals(""))
            Toast.makeText(this, "입력한 아이디가 없습니다!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,  userId + " 님 로그인 하였습니다.", Toast.LENGTH_SHORT).show();

        title.setText(userId + "님 SJK 어플리케이션에 오신것을 환영합니다.");
        //title.setTextColor(0xFFFF0000);

        if(!userId.equals("admin")){
            //managementButton.setEnabled(false);
            managementButton.setVisibility(View.GONE);
        }

    }

    class BackGroundTask extends AsyncTask<Void, Void, String>{
        String target;

        @Override
        protected void onPreExecute(){ // 초기화
            target = "http://172.17.17.153:8080/smartcity/androidTest/userList";
        }

        @Override
        protected String doInBackground(Void... voids){ //실질적으로 실행되는 부분
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
                httpURLConnection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){ //상속만 받고 사용하지않음
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent = new Intent(SubActivity.this, ManagementActivity.class);
            intent.putExtra("userList", result);
            SubActivity.this.startActivity(intent);
        }

    }

    public void userMng(View view) {
        new BackGroundTask().execute();
    }

    public void logOut(View view) {
        finish();

        Log.v("logOut","로그아웃");
    }
}
