package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class PhotoRequest {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("place_id")
    private int placeId;
    @SerializedName("image")
    private String image;
    @SerializedName("image_code")
    private String imageCode;

    public PhotoRequest(int userId, int placeId, String image, String imageCode) {
        this.userId = userId;
        this.placeId = placeId;
        this.image = image;
        this.imageCode = imageCode;
    }
}
