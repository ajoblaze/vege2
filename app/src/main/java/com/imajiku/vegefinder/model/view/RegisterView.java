package com.imajiku.vegefinder.model.view;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface RegisterView {
    void successRegister(int id, String message);

    void failedRegister(String message);
}
