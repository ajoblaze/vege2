package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class ReportRequest {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("place_id")
    private int placeId;
    @SerializedName("subject")
    private String subject;
    @SerializedName("comment")
    private String message;

    public ReportRequest(int userId, int placeId, String subject, String message) {
        this.userId = userId;
        this.placeId = placeId;
        this.subject = subject;
        this.message = message;
    }
}
