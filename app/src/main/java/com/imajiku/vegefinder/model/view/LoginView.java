package com.imajiku.vegefinder.model.view;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface LoginView {
    void successLogin(int userId, String s);

    void failedLogin(String s);
}
