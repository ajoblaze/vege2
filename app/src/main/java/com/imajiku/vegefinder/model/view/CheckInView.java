package com.imajiku.vegefinder.model.view;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface CheckInView {
    void successCheckIn();

    void failedCheckIn(String message);
}
