package com.imajiku.vegefinder.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-19.
 */
public class Province {
    @SerializedName("id")
    private int id;
    @SerializedName("country_id")
    private int countryId;
    @SerializedName("province")
    private String province;

    public int getId() {
        return id;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getProvince() {
        return province;
    }
}
