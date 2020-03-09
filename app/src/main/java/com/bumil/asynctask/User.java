package com.bumil.asynctask;

public class User {

    String userID;
    String depCodeNm;
    String regDt;
    String userPassword;
    String userName;
    String userAge;

    public User(String userID, String depCodeNm, String userName, String regDt) {
        this.userID = userID;
        this.depCodeNm = depCodeNm;
        this.userName = userName;
        this.regDt = regDt;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getDepCodeNm() {
        return depCodeNm;
    }

    public void setDepCodeNm(String depCodeNm) {
        this.depCodeNm = depCodeNm;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }


}
