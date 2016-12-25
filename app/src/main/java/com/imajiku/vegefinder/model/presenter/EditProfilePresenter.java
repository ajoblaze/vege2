package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.EditProfileModel;
import com.imajiku.vegefinder.model.request.EditProfileRequest;
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

    public void registerProfile(EditProfileRequest request) {
        model.registerProfile(request);
    }

    public void successRegisterProfile() {
        view.successRegisterProfile();
    }

    public void failedRegisterProfile() {
        view.failedRegisterProfile();
    }

    public void updateProfile(EditProfileRequest request) {
        model.updateProfile(request);
    }

    public void successUpdateProfile() {
        view.successUpdateProfile();
    }

    public void failedUpdateProfile() {
        view.failedUpdateProfile();
    }

    public void updatePhotoProfile(EditProfileRequest request) {
        model.updatePhotoProfile(request);
    }

    public void successUpdatePhotoProfile() {
        view.successUpdatePhotoProfile();
    }

    public void failedUpdatePhotoProfile() {
        view.failedUpdatePhotoProfile();
    }

    public void changePassword(String email, String pass) {
        model.changePassword(email, pass);
    }

    public void successResetPassword() {
        view.successResetPassword();
    }

    public void failedResetPassword() {
        view.failedResetPassword();
    }
}

