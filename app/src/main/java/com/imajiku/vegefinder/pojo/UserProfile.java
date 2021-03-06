package com.imajiku.vegefinder.pojo;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.utility.Utility;

import java.io.Serializable;

/**
 * Created by Alvin on 2016-11-20.
 */
public class UserProfile implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("ip_address")
    private String ipAddress;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("salt")
    private String salt;
    @SerializedName("email")
    private String email;
    @SerializedName("activation_code")
    private String activationCode;
    @SerializedName("forgotten_password_code")
    private String forgotCode;
    @SerializedName("forgotten_password_time")
    private String forgotTime;
    @SerializedName("remember_code")
    private String rememberCode;
    @SerializedName("created_on")
    private String createdTime;
    @SerializedName("last_login")
    private String lastLogin;
    @SerializedName("active")
    private String isActive;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("company")
    private String company;
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
    @SerializedName("phone")
    private String phone;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("preference")
    private String preference;
    @SerializedName("image")
    private String image;
    @SerializedName("img_url")
    private String imageUrl;
    @SerializedName("how")
    private String how;
    @SerializedName("dob")
    private String dob;

    public String getName() {
        if (lastName == null || lastName.equals("null") || lastName.isEmpty()) {
            return firstName;
        }
        return firstName+" "+lastName;
    }

    public int getId() {
        return parseInt(id);
    }
    
    public int parseInt(String s){
        int i;
        try {
            i = Integer.parseInt(s);
        }catch(NumberFormatException e) {
            i = 0;
        }
        return i;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getEmail() {
        return email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public String getForgotCode() {
        return forgotCode;
    }

    public String getForgotTime() {
        return forgotTime;
    }

    public String getRememberCode() {
        return rememberCode;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public int getIsActive() {
        return parseInt(isActive);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public int getCountryId() {
        return parseInt(country);
    }

    public int getProvinceId() {
        return parseInt(province);
    }

    public int getCityId() {
        return parseInt(city);
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPreference() {
        return preference;
    }

    public String getImage() {
        return Utility.attachImageUrl(image);
    }

    public String getImageUrl() {
        return Utility.attachImageUrl(imageUrl);
    }

    public String getHow() {
        return how;
    }

    public String getDob() {
        return dob;
    }
}
