package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterProfileRequest {
    @SerializedName("id")
    int id;
    @SerializedName("country")
    int countryId;
    @SerializedName("province")
    int provinceId;
    @SerializedName("city")
    int cityId;
    @SerializedName("gender")
    String gender;
    @SerializedName("preference")
    String preference;
    @SerializedName("image")
    String image;
    @SerializedName("image_code")
    String image_code;

    public RegisterProfileRequest(int id, int countryId, int provinceId, int cityId, String gender, String preference, String image, String image_code) {
        this.id = id;
        this.countryId = countryId;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.gender = gender;
        this.preference = preference;
        this.image = image;
        this.image_code = image_code;
    }
}
