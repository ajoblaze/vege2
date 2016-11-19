package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.RegisterProfileModel;
import com.imajiku.vegefinder.model.request.RegisterProfileRequest;
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

    public void registerProfile(RegisterProfileRequest request) {
        model.registerProfile(request);
    }

    public void successRegisterProfile() {
        view.successRegisterProfile();
    }

    public void failedRegisterProfile() {
        view.failedRegisterProfile();
    }
}

