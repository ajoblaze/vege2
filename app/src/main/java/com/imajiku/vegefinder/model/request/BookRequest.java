package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class BookRequest {
    @SerializedName("user_id")
    int userId;
    @SerializedName("place_id")
    int placeId;
    @SerializedName("date")
    String date;
    @SerializedName("time")
    String time;
    @SerializedName("comment")
    String comment;

    public BookRequest(int userId, int placeId, String date, String time, String comment) {
        this.userId = userId;
        this.placeId = placeId;
        this.date = date;
        this.time = time;
        this.comment = comment;
    }
}
