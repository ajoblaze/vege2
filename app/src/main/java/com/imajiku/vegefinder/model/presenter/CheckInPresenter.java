package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.CheckInModel;
import com.imajiku.vegefinder.model.view.CheckInView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class CheckInPresenter {
    private CheckInView view;
    private CheckInModel model;

    public CheckInPresenter(CheckInView v) {
        this.view =v;
    }

    public CheckInView getView() {
        return view;
    }

    public void setModel(CheckInModel model) {
        this.model = model;
    }

    public void checkIn(int userId, int placeId, String comment){
        model.checkIn(userId, placeId, comment);
    }

    public void successCheckIn() {
        view.successCheckIn();
    }

    public void failedCheckIn(String message) {
        view.failedCheckIn(message);
    }
}

