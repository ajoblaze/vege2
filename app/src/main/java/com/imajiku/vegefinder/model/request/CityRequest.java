package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class CityRequest {
    @SerializedName("province")
    int provinceId;

    public CityRequest(int provinceId) {
        this.provinceId = provinceId;
    }
}
