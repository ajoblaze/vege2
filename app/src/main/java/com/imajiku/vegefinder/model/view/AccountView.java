package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.UserProfile;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface AccountView {
    void successGetProfile(UserProfile profile);

    void failedGetProfile();

    void successLogout();

    void failedLogout();
}
