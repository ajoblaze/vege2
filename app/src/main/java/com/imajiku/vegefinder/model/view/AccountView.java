package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.Resto;
import com.imajiku.vegefinder.pojo.UserProfile;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface AccountView {
    void successGetProfile(UserProfile profile);

    void failedGetProfile();

    void successLogout();

    void failedLogout();

    void successGetBookmarks(ArrayList<Resto> data);

    void failedGetBookmarks();

    void successGetBeenHere(ArrayList<Resto> data);

    void failedGetBeenHere();
}
