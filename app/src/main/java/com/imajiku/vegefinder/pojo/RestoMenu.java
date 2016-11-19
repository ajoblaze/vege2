package com.imajiku.vegefinder.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-11-13.
 */
public class RestoMenu {
    @SerializedName("place_id")
    private int placeId;
    @SerializedName("id")
    private int menuId;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;

    public int getPlaceId() {
        return placeId;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
