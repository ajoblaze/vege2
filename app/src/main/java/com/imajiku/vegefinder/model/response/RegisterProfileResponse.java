package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterProfileResponse {
    @SerializedName("data")
    private RegisterProfileResponseBody data;

    public RegisterProfileResponseBody getData() {
        return data;
    }

    public class RegisterProfileResponseBody {
        @SerializedName("status")
        private String status;

        public String getStatus() {
            return status;
        }
    }
}
