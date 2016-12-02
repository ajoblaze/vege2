package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class FindAllRequest {
    @SerializedName("province_id")
    private int provinceId;
    @SerializedName("city_id")
    private int cityId;
    @SerializedName("keywords")
    private String keyword;

    public FindAllRequest(int provinceId, int cityId, String keyword) {
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.keyword = keyword;
    }
}
