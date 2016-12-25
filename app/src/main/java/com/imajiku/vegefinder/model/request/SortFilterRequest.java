package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class SortFilterRequest {
    @SerializedName("id")
    int userId;
    @SerializedName("type")
    String type;
    @SerializedName("loc")
    String loc;
    @SerializedName("sort")
    String sort;
    @SerializedName("open_now")
    String openNow;
    @SerializedName("bookmarked")
    String bookmarked;
    @SerializedName("beenhere")
    String beenhere;
    @SerializedName("rated")
    String rated;
    @SerializedName("vegan")
    String vegan;
    @SerializedName("vegetarian")
    String vege;
    @SerializedName("vegeready")
    String vegeready;

    public SortFilterRequest(int userId, String type, String loc, String sort, String openNow, String bookmarked, String beenhere, String rated, String vegan, String vege, String vegeready) {
        this.userId = userId;
        this.type = type;
        this.loc = loc;
        this.sort = sort;
        this.openNow = openNow;
        this.bookmarked = bookmarked;
        this.beenhere = beenhere;
        this.rated = rated;
        this.vegan = vegan;
        this.vege = vege;
        this.vegeready = vegeready;
    }

    public SortFilterRequest(int userId) {
        this.userId = userId;
        this.type = "name";
        this.loc = "";
        this.sort = "asc";
        this.openNow = "no";
        this.bookmarked = "no";
        this.beenhere = "no";
        this.rated = "no";
        this.vegan = "no";
        this.vege = "no";
        this.vegeready = "no";
    }
}
