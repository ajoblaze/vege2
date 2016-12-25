package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class FindRegionRequest {
    @SerializedName("country_idd")
    private int countryId;
    @SerializedName("province_id")
    private int provinceId;
    @SerializedName("city_id")
    private int cityId;

    public FindRegionRequest(int countryId, int provinceId, int cityId) {
        this.countryId = countryId;
        this.provinceId = provinceId;
        this.cityId = cityId;
    }
}
