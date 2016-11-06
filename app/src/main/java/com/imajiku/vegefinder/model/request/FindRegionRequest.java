package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class FindRegionRequest {
    @SerializedName("province_id")
    private int provinceId;
    @SerializedName("city_id")
    private int cityId;

    public FindRegionRequest(int provinceId, int cityId) {
        this.provinceId = provinceId;
        this.cityId = cityId;
    }
}
