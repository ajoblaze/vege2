package com.imajiku.vegefinder.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-11-13.
 */
public class RestoTag {
    @SerializedName("id")
    private int tagId;
    @SerializedName("place_id")
    private int placeId;
    @SerializedName("tag")
    private String tag;

    public int getTagId() {
        return tagId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public String getTag() {
        return tag;
    }
}
