package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.MessageModel;
import com.imajiku.vegefinder.model.view.MessageView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class MessagePresenter {
    private MessageView view;
    private MessageModel model;

    public MessagePresenter(MessageView v) {
        this.view =v;
    }

    public MessageView getView() {
        return view;
    }

    public void setModel(MessageModel model) {
        this.model = model;
    }

    public void sendContactUs(String name, String email, String phone, String subject, String message){
        model.sendContactUs(name, email, phone, subject, message);
    }

    public void successSendContactUs(String s) {
        view.successSendContactUs();
    }

    public void failedSendContactUs(String s) {
        view.failedSendContactUs(s);
    }

    public void sendReview(int userId, int restoId, String rate, String title, String comment){
        model.sendReview(userId, restoId,  rate, title, comment);
    }

    public void successSendReview(String s) {
        view.successSendReview(s);
    }

    public void failedSendReview(String s) {
        view.failedSendReview(s);
    }

//    public void sendReport(String name, String email, String phone, String subject, String message){
//        model.sendReport(name, email, phone, subject, message);
//    }

    public void successSendReport(String s) {
        view.successSendReport();
    }

    public void sendFeedback(int userId, String subject, String message) {
        model.sendFeedback(userId, subject, message);
    }

    public void sendReport(int userId, int placeId, String subject, String message) {
        model.sendReport(userId, placeId, subject, message);
    }

    public void failedSendReport(String s) {
        view.failedSendReport(s);
    }
}

