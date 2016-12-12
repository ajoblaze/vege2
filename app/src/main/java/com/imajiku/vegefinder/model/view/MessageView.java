package com.imajiku.vegefinder.model.view;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface MessageView {
    void successSendContactUs();
    void successSendReview(String s);
    void failedSendReview(String s);
    void successSendReport();

    void failedSendContactUs(String s);
}
