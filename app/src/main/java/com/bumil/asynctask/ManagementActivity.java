package com.bumil.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ManagementActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        userList = new ArrayList<User>();

        /*userList.add(new User("홍길동", "홍길동", "홍길동", "20"));
        userList.add(new User("김갑수", "김갑수", "김갑수", "28"));
        userList.add(new User("강세정", "강세정", "강세정", "26"));
        userList.add(new User("이유리", "이유리", "이유리", "15")); 예시 데이터*/

        adapter = new UserListAdapter(getApplicationContext(), userList, this);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID, depCodeNm, userName, regDt;
            while (count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userId");
                depCodeNm = object.getString("depCodeNm");
                userName = object.getString("userNm");

                String dateStr = object.getString("regDt");
                Date parseDate = new Date(Long.parseLong(dateStr));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul")); //한국날짜로 변환
                String formatDate = sdf.format(parseDate);

                regDt = formatDate;
                User user = new User(userID, depCodeNm, userName, regDt);
                if(!userID.equals("admin")){
                    userList.add(user);
                }
                count++;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
