package com.bumil.asynctask;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private  Activity parentActivity;

    public UserListAdapter(Context context, List<User> userList, Activity parentActivity){
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.user, null);

        final TextView userID = (TextView) v.findViewById(R.id.userID);
        TextView depCodeNm = (TextView) v.findViewById(R.id.depcodeNm);
        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView regDt = (TextView) v.findViewById(R.id.regDt);

        userID.setText(userList.get(i).getUserID());
        depCodeNm.setText(userList.get(i).getDepCodeNm());
        userName.setText(userList.get(i).getUserName());
        regDt.setText(userList.get(i).getRegDt());

        v.setTag(userList.get(i).getUserID());

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String msg = jsonResponse.getString("msg");
                            if(success){
                                userList.remove(i);
                                notifyDataSetChanged();
                            }else{
                                if(msg.equals("notFoundUser")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                    builder.setMessage("존재하지않는 사용자 입니다. 확인 후 삭제 해주세요.")
                                            .setNegativeButton("확인", null)
                                            .create()
                                            .show();
                                }
                            }
                        }catch (Exception e){
                           e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(userID.getText().toString(), responseListener);
                deleteRequest.setRetryPolicy(new DefaultRetryPolicy(1000000000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
            }
        });

        return v;
    }
}
