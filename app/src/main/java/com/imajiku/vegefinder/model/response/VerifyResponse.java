package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyResponse {
    @SerializedName("data")
    private VerifyResponseBody data;

    public VerifyResponseBody getData() {
        return data;
    }

    public class VerifyResponseBody {
        @SerializedName("status")
        private String status;

        public String getStatus() {
            return status;
        }
    }
}
