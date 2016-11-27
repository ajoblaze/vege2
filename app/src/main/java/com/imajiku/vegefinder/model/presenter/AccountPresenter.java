package com.imajiku.vegefinder.model.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.imajiku.vegefinder.model.model.AccountModel;
import com.imajiku.vegefinder.model.view.AccountView;
import com.imajiku.vegefinder.pojo.UserProfile;

/**
 * Created by Alvin on 2016-10-08.
 */
public class AccountPresenter {
    private AccountView view;
    private AccountModel model;
    private static final String LOGIN_PREF = "login_pref";
    private static final String LOGIN_KEY = "login";

    public AccountPresenter(AccountView v) {
        this.view =v;
    }

    public AccountView getView() {
        return view;
    }

    public void setModel(AccountModel model) {
        this.model = model;
    }

    public void getProfile(int userId) {
        model.getProfile(userId);
    }

    public void successGetProfile(UserProfile profile) {
        view.successGetProfile(profile);
    }

    public void failedGetProfile() {
        view.failedGetProfile();
    }

    public void logout() {
        SharedPreferences preferences = getContext().getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LOGIN_KEY);
        editor.apply();
//        model.logout();
    }

    private Context getContext(){
        return (Context) view;
    }
}

