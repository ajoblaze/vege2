package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.ForgotModel;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.model.view.ForgotView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ForgotPresenter {
    private ForgotView view;
    private ForgotModel model;
    private ProvinceResponse provinceResponse;

    public ForgotPresenter(ForgotView v) {
        this.view =v;
    }

    public ForgotView getView() {
        return view;
    }

    public void setModel(ForgotModel model) {
        this.model = model;
    }

    public void successForgot() {
        view.successForgot();
    }

    public void forget(String emailContent) {
        model.forgotPassword(emailContent);
    }

    public void failedForgot() {
        view.failedForgot();
    }
}

