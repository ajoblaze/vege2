package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class SortFilterRequest {
    @SerializedName("id")
    int userId;
    @SerializedName("user")
    int userId2;
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
    @SerializedName("page")
    int page;
    @SerializedName("country_id")
    String countryId;
    @SerializedName("province_id")
    String provinceId;
    @SerializedName("city_id")
    String cityId;
    @SerializedName("keywords")
    String keyword;

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
        this.page = 1;
    }

    public SortFilterRequest(int userId) {
        this.userId = userId;
        this.type = "alphabet";
        this.sort = "asc";
        this.openNow = "no";
        this.bookmarked = "no";
        this.beenhere = "no";
        this.rated = "no";
        this.vegan = "no";
        this.vege = "no";
        this.vegeready = "no";
        this.page = 1;
    }

    public SortFilterRequest(int userId, String type, String loc, String sort, String openNow, String bookmarked, String beenhere, String rated, String vegan, String vege, String vegeready, int page) {
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
        this.page = page;
    }

    public SortFilterRequest(int userId, int page) {
        this.userId = userId;
        this.type = "alphabet";
        this.sort = "asc";
        this.openNow = "no";
        this.bookmarked = "no";
        this.beenhere = "no";
        this.rated = "no";
        this.vegan = "no";
        this.vege = "no";
        this.vegeready = "no";
        this.page = page;
    }

    public SortFilterRequest(int userId, String loc, String vegeready, String vegan, String vege) {
        this.userId2 = userId;
        this.loc = loc;
        this.vegeready = vegeready;
        this.vegan = vegan;
        this.vege = vege;
    }

    public void assign(int userId, String type, String loc, String sort, String openNow, String bookmarked, String beenhere, String rated, String vegan, String vege, String vegeready) {
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
        this.page = 1;
    }

    public void setDefaultLoc(String loc) {
        if(this.loc == null || this.loc.isEmpty()) {
            this.loc = loc;
        }
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setRegionId(String countryId, String provinceId, String cityId) {
        this.countryId = countryId;
        this.provinceId = provinceId;
        this.cityId = cityId;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
