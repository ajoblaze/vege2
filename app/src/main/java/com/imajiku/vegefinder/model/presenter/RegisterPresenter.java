package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.RegisterModel;
import com.imajiku.vegefinder.model.request.RegisterRequest;
import com.imajiku.vegefinder.model.view.RegisterView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterPresenter {
    private RegisterView view;
    private RegisterModel model;

    public RegisterPresenter(RegisterView v) {
        this.view =v;
    }

    public RegisterView getView() {
        return view;
    }

    public void setModel(RegisterModel model) {
        this.model = model;
    }

    public void register(String name, String email, String password) {
        model.register(name, email, password);
    }

    public void successRegister() {
        view.successRegister();
    }

    public void failedRegister() {
        view.failedRegister();
    }
}
