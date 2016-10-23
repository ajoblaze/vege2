package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class LoginRequest {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
