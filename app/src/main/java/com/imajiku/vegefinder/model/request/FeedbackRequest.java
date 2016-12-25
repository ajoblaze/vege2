package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class FeedbackRequest {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("subject")
    private String subject;
    @SerializedName("comment")
    private String message;

    public FeedbackRequest(int userId, String subject, String message) {
        this.userId = userId;
        this.subject = subject;
        this.message = message;
    }
}
