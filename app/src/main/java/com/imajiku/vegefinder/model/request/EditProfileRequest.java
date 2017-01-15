package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class EditProfileRequest {
    @SerializedName("id")
    int id;
    @SerializedName("nama")
    String name;
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
    String imageCode;

    public EditProfileRequest(int id, int countryId, int provinceId, int cityId, String gender, String preference, String image, String imageCode) {
        this.id = id;
        this.countryId = countryId;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.gender = gender;
        this.preference = preference;
        this.image = image;
        this.imageCode = imageCode;
    }

    public EditProfileRequest(int id, int countryId, int provinceId, int cityId, String gender, String preference) {
        this.id = id;
        this.countryId = countryId;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.gender = gender;
        this.preference = preference;
    }

    public EditProfileRequest(int id, String image, String imageCode) {
        this.id = id;
        this.image = image;
        this.imageCode = imageCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getImageCode() {
        return imageCode;
    }

    public String getImage() {
        return image;
    }
}
