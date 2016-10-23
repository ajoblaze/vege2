package com.imajiku.vegefinder.model.presenter;


import android.content.Context;

import com.imajiku.vegefinder.activity.RegisterProfileActivity;
import com.imajiku.vegefinder.model.RegisterProfileModel;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.model.presenter.view.RegisterProfileView;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterProfilePresenter {
    private RegisterProfileView view;
    private RegisterProfileModel model;

    public RegisterProfilePresenter(RegisterProfileView v) {
        this.view =v;
    }

    public RegisterProfileView getView() {
        return view;
    }

    public void setModel(RegisterProfileModel model) {
        this.model = model;
    }

    public void submitRegister(String username, String email, String password) {
        model.register(username, email, password);
    }

    public void successRegister(String code, String email) {
        view.sendActivationCode(code, email);
    }
}

