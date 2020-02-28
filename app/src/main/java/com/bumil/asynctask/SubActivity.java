package com.bumil.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        TextView title = (TextView) findViewById(R.id.edit_title);
        Button managementButton = (Button) findViewById(R.id.managementButton);

        //다음페이지로 이동 후 토스트에 '***님(id) 로그인 하였습니다.' 표시
        //페이지에 '*** 님 환영합니다.' 표시

        if(userId.equals(""))
            Toast.makeText(this, "입력한 아이디가 없습니다!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,  userId + " 님 로그인 하였습니다.", Toast.LENGTH_SHORT).show();

        title.setText(userId + "님 환영합니다.");
        //title.setTextColor(0xFFFF0000);

        if(!userId.equals("admin")){
            managementButton.setEnabled(false);
        }

    }

    public void logOut(View view) {
        finish();

        Log.v("logOut","로그아웃");
    }
}
