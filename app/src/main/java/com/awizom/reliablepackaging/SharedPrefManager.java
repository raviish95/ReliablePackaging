package com.awizom.reliablepackaging;

import android.content.Context;
import android.content.SharedPreferences;

import com.awizom.reliablepackaging.Model.LoginModel;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "reliablesharepref";


    private static final String Key_ClientID = "ClientID";
    private static final String Key_UserID = "UserID";
    private static final String Key_UserName = "UserName";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(LoginModel loginModel) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Key_UserID, loginModel.UserID);
        editor.putString(Key_UserName, loginModel.UserName);
        editor.putInt(Key_ClientID, loginModel.ClientID);
        editor.apply();
        return true;
    }


    public LoginModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        LoginModel token = new LoginModel();


        token.ClientID = sharedPreferences.getInt(Key_ClientID, 0);

        token.UserID = sharedPreferences.getString(Key_UserID, "");
        token.UserName = sharedPreferences.getString(Key_UserName, "");
        return token;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(Key_ClientID, null) != null)
            return true;
        return false;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


}
