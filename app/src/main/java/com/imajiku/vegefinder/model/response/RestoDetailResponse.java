package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.pojo.RestoDetail;

/**
 * Created by Alvin on 2016-10-23.
 */
public class RestoDetailResponse {
    @SerializedName("data")
    private RestoDetail data;

    public RestoDetail getData() {
        return data;
    }
}
