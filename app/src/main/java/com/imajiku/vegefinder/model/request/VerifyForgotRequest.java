package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyForgotRequest {
    @SerializedName("code")
    String code;

    @SerializedName("password")
    String password;

    @SerializedName("password_confirm")
    String password2;

    public VerifyForgotRequest(String code, String password) {
        this.code = code;
        this.password = password;
        this.password2 = password;
    }
}
