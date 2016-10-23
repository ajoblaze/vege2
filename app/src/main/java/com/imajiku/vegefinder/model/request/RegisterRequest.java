package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterRequest {
    @SerializedName("name")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
