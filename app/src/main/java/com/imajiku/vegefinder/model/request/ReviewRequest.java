package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class ReviewRequest {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("place_id")
    private int placeId;
    @SerializedName("rate")
    private int rate;
    @SerializedName("title")
    private String title;
    @SerializedName("comment")
    private String comment;

    public ReviewRequest(int userId, int placeId, int rate, String title, String comment) {
        this.userId = userId;
        this.placeId = placeId;
        this.rate = rate;
        this.title = title;
        this.comment = comment;
    }
}
