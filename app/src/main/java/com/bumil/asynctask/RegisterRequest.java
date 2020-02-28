package com.bumil.asynctask;

import androidx.annotation.StringRes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //final static private String URL = "http://192.168.55.68:8080/smartcity/androidTest/register"; //집
    final static private String URL = "http://172.17.17.153:8080/smartcity/androidTest/register"; //회사

    private Map<String, String> parameters;

    public RegisterRequest(String userId, String userPw, String userName, int userAge, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("userPw", userPw);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge + "");
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
