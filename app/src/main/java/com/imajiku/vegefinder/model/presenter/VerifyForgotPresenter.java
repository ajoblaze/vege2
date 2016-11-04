package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.VerifyForgotModel;
import com.imajiku.vegefinder.model.view.VerifyForgotView;
import com.imajiku.vegefinder.model.response.ProvinceResponse;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyForgotPresenter {
    private VerifyForgotView view;
    private VerifyForgotModel model;
    private ProvinceResponse provinceResponse;

    public VerifyForgotPresenter(VerifyForgotView v) {
        this.view =v;
    }

    public VerifyForgotView getView() {
        return view;
    }

    public void setModel(VerifyForgotModel model) {
        this.model = model;
    }

    public void successVerifyCode() {
        view.successResetPassword();
    }

    public void failedVerifyCode() {
        view.failedResetPassword("Code is incorrect. Please try again.");
    }

    public void resetPassword(String code, String password) {
        model.resetPassword(code, password);
    }
}

