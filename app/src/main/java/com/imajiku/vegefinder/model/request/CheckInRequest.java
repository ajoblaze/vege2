package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class CheckInRequest {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("place_id")
    private int placeID;
    @SerializedName("comment")
    private String comment;

    public CheckInRequest(int userId, int placeID, String comment) {
        this.userId = userId;
        this.placeID = placeID;
        this.comment = comment;
    }
}
