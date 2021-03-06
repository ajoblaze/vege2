package com.imajiku.vegefinder.pojo;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.utility.Utility;

/**
 * Created by Alvin on 2016-11-13.
 */
public class RestoImage {
    @SerializedName("id")
    private int imgId;
    @SerializedName("image")
    private String image;

    public RestoImage(int imgId, String image) {
        this.imgId = imgId;
        this.image = image;
    }

    public int getImgId() {
        return imgId;
    }

    public String getImage() {
        return Utility.attachImageUrl(image);
    }

    public void setImage(String image) {
        this.image = image;
    }
}
