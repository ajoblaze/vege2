package com.imajiku.vegefinder.model.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.imajiku.vegefinder.model.model.AccountModel;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
import com.imajiku.vegefinder.model.view.AccountView;
import com.imajiku.vegefinder.pojo.Resto;
import com.imajiku.vegefinder.pojo.UserProfile;
import com.imajiku.vegefinder.utility.CurrentUser;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class AccountPresenter {
    private AccountView view;
    private AccountModel model;
    private static final String LOGIN_PREF = "login_pref";
    private static final String LOGIN_KEY = "login";
    private static final String USER_ID_KEY = "user_id";

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
        CurrentUser.logout(getContext());
        model.logout();
    }

    public void successLogout(){
        view.successLogout();
    }

    public void failedLogout(){
        view.failedLogout();
    }

    private Context getContext(){
        return (Context) view;
    }

    public void getBookmarks(SortFilterRequest request) {
        model.getBookmarks(request);
    }

    public void successGetBookmarks(ArrayList<Resto> list) {
        view.successGetBookmarks(list);
    }

    public void failedGetBookmarks() {
        view.failedGetBookmarks();
    }

    public void getBeenHere(SortFilterRequest request) {
        model.getBeenHere(request);
    }

    public void successGetBeenHere(ArrayList<Resto> data) {
        view.successGetBeenHere(data);
    }

    public void failedGetBeenHere() {
        view.failedGetBeenHere();
    }
}

