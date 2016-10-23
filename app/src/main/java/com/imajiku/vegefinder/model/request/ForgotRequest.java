package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ForgotRequest {
    @SerializedName("email")
    String email;

    public ForgotRequest(String email) {
        this.email = email;
    }
}
