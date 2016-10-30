package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyForgotResponse {
    @SerializedName("data")
    private VerifyForgotResponseBody data;

    public VerifyForgotResponseBody getData() {
        return data;
    }

    public class VerifyForgotResponseBody {
        @SerializedName("status")
        private String status;

        public String getStatus() {
            return status;
        }
    }
}
