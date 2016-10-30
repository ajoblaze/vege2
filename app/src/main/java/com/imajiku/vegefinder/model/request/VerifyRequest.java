package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyRequest {
    @SerializedName("code")
    String code;

    public VerifyRequest(String code) {
        this.code = code;
    }
}
