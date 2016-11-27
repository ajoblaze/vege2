package com.imajiku.vegefinder.model.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.imajiku.vegefinder.model.model.LoginModel;
import com.imajiku.vegefinder.model.view.LoginView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class LoginPresenter {
    private LoginView view;
    private LoginModel model;
    private static final String LOGIN_PREF = "login_pref";
    private static final String LOGIN_KEY = "login";

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public LoginView getView() {
        return view;
    }

    public void setModel(LoginModel model) {
        this.model = model;
    }

    public void login(int method, String email, String pass) {
        SharedPreferences preferences = getContext().getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(LOGIN_KEY, method);
        editor.apply();
        model.login(email, pass);
    }

    public int getCurrentLogin(){
        SharedPreferences preferences = getContext().getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        return preferences.getInt(LOGIN_KEY, -1);
    }

    private Context getContext(){
        return (Context) view;
    }

    public void successLogin() {
        view.successLogin();
    }

    public void failedLogin() {
        view.failedLogin();
    }

    public void logout() {

    }
}

