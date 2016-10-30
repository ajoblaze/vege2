package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ForgotResponse {
    @SerializedName("data")
    private ForgotResponseBody data;

    public ForgotResponseBody getData() {
        return data;
    }

    public class ForgotResponseBody {
        @SerializedName("status")
        private String status;

        public String getStatus() {
            return status;
        }
    }
}
