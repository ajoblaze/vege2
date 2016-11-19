package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class ToggleRequest {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("place_id")
    private int placeID;

    public ToggleRequest(int userId, int placeID) {
        this.userId = userId;
        this.placeID = placeID;
    }
}
