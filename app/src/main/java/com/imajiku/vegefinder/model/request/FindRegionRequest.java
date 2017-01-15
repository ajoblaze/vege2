package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class FindRegionRequest {
    @SerializedName("country_idd")
    private String countryId;
    @SerializedName("province_id")
    private String provinceId;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("user_id")
    private String userId;

    public FindRegionRequest(String countryId, String provinceId, String cityId, String userId) {
        this.countryId = countryId == null ? "" : countryId;
        this.provinceId = provinceId == null ? "" : provinceId;
        this.cityId = cityId == null ? "" : cityId;
        this.userId = userId;
    }
}
