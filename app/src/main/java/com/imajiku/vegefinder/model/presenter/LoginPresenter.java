package com.imajiku.vegefinder.model.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.imajiku.vegefinder.model.LoginModel;
import com.imajiku.vegefinder.model.presenter.view.LoginView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class LoginPresenter {
    private LoginView v;
    private LoginModel model;
    private static final String LOGIN_PREF = "login_pref";
    private static final String VERIFY_PREF = "verify_pref";
    private static final String LOGIN_KEY = "login";

    public LoginPresenter(LoginView v) {
        this.v=v;
    }

    public LoginView getView() {
        return v;
    }

    public void setModel(LoginModel model) {
        this.model = model;
    }

    public void login(int method, String email, String pass) {
        SharedPreferences preferences = getContext().getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(LOGIN_KEY, method);
        editor.apply();
        if(checkFlag(email)){
            model.login(email, pass);
        }else{
            getView().failedLogin();
        }
    }

    private boolean checkFlag(String email) {
        SharedPreferences preferences = getContext().getSharedPreferences(VERIFY_PREF, Context.MODE_PRIVATE);
        return preferences.getBoolean(email, false);
    }

    public void logout() {
        SharedPreferences preferences = getContext().getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LOGIN_KEY);
        editor.apply();
    }

    public int getCurrentLogin(){
        SharedPreferences preferences = getContext().getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        return preferences.getInt(LOGIN_KEY, -1);
    }

    private Context getContext(){
        return (Context) v;
    }

    public void successLogin() {
        getView().successLogin();
    }
}

