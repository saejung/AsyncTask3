package com.bumil.asynctask;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.user, null);

        TextView userID = (TextView) v.findViewById(R.id.userID);
        TextView depCodeNm = (TextView) v.findViewById(R.id.depcodeNm);
        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView regDt = (TextView) v.findViewById(R.id.regDt);

        userID.setText(userList.get(i).getUserID());
        depCodeNm.setText(userList.get(i).getDepCodeNm());
        userName.setText(userList.get(i).getUserName());
        regDt.setText(userList.get(i).getRegDt());

        v.setTag(userList.get(i).getUserID());
        return v;
    }
}
