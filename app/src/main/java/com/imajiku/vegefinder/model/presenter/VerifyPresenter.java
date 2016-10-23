package com.imajiku.vegefinder.model.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.imajiku.vegefinder.model.presenter.view.VerifyView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyPresenter {
    private VerifyView view;
    private static final String VERIFY_PREF = "verify_pref";

    public VerifyPresenter(VerifyView v) {
        this.view =v;
    }

    public VerifyView getView() {
        return view;
    }

    public void putFlag(String email) {
        SharedPreferences preferences = getContext().getSharedPreferences(VERIFY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(email, true);
        editor.apply();
    }

    public void removeFlag(String email) {
        SharedPreferences preferences = getContext().getSharedPreferences(VERIFY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(email);
        editor.apply();
    }

    private Context getContext(){
        return (Context) view;
    }
}

