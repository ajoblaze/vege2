package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.UserProfile;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface EditProfileView {
    void successRegisterProfile();

    void failedRegisterProfile();

    void successUpdateProfile();

    void failedUpdateProfile();

    void successUpdatePhotoProfile();

    void failedUpdatePhotoProfile();

    void successResetPassword();

    void failedResetPassword();
}
