package com.imajiku.vegefinder.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alvin on 2016-12-11.
 */
public class CurrentUser {
    private static final String LOGIN_PREF = "login_pref";
    private static final String LOGIN_KEY = "login";
    private static final String PASSWORD_KEY = "password";
    private static final String USER_ID_KEY = "user_id";

    public static void setId(Context context, int id) {
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, id);
        editor.apply();
    }

    public static int getId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        return preferences.getInt(USER_ID_KEY, -1);
    }

    public static void setPassword(Context context, String pass) {
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PASSWORD_KEY, pass);
        editor.apply();
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        return preferences.getString(PASSWORD_KEY, "null");
    }

    public static void logout(Context context){
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LOGIN_KEY);
        editor.remove(USER_ID_KEY);
        editor.remove(PASSWORD_KEY);
        editor.apply();
    }
}
