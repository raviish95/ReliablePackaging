package com.awizom.reliablepackaging.Model;

public class LoginModel {

    public int ClientID;
    public String UserID;
    public String UserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
