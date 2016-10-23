package com.imajiku.vegefinder.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-19.
 */
public class City {
    @SerializedName("id")
    private int id;
    @SerializedName("province_id")
    private int provinceId;
    @SerializedName("city")
    private String city;

    public int getId() {
        return id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public String getCity() {
        return city;
    }
}
