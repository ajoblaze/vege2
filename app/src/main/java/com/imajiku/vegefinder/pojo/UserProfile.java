package com.imajiku.vegefinder.pojo;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alvin on 2016-11-20.
 */
public class UserProfile implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("gender")
    private String sex;
    @SerializedName("address")
    private String address;
    @SerializedName("country")
    private String country;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("preference")
    private String preference;
    @SerializedName("image")
    private String image;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName+" "+lastName;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getPreference() {
        return preference;
    }

    public String getImage() {
        return image;
    }
}
