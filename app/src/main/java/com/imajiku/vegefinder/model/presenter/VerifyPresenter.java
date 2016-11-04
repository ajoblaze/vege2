package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.VerifyModel;
import com.imajiku.vegefinder.model.view.VerifyView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyPresenter {
    private VerifyView view;
    private VerifyModel model;

    public VerifyPresenter(VerifyView v) {
        this.view =v;
    }

    public VerifyView getView() {
        return view;
    }

    public void setModel(VerifyModel model) {
        this.model = model;
    }

    public void submit(String s) {
        model.submitCode(s);
    }

    public void successVerify() {
        view.successVerify();
    }

    public void failedVerify() {
        view.showError("Code is incorrect. Please try again.");
    }
}

