package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.EditProfileModel;
import com.imajiku.vegefinder.model.request.RegisterProfileRequest;
import com.imajiku.vegefinder.model.view.EditProfileView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class EditProfilePresenter {
    private EditProfileView view;
    private EditProfileModel model;

    public EditProfilePresenter(EditProfileView v) {
        this.view =v;
    }

    public EditProfileView getView() {
        return view;
    }

    public void setModel(EditProfileModel model) {
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

