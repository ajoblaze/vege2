package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.ContactUsModel;
import com.imajiku.vegefinder.model.view.ContactUsView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ContactUsPresenter {
    private ContactUsView view;
    private ContactUsModel model;

    public ContactUsPresenter(ContactUsView v) {
        this.view =v;
    }

    public ContactUsView getView() {
        return view;
    }

    public void setModel(ContactUsModel model) {
        this.model = model;
    }

    public void sendMessage(String name, String email, String phone, String subject, String message){
        model.sendContactUs(name, email, phone, subject, message);
    }

    public void successSendMessage(String s) {
        view.successSendMessage();
    }
}

