package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.RegisterProfileModel;
import com.imajiku.vegefinder.model.view.RegisterProfileView;
import com.imajiku.vegefinder.model.request.RegisterRequest;

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

    public void submitRegister(RegisterRequest request) {
        model.register(request);
    }

    public void successRegister(String code, String email) {
        view.successRegister(code, email);
    }
}

