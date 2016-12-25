package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class StatusMessageResponse {
    @SerializedName("data")
    private StatusMessageResponseBody data;

    public StatusMessageResponseBody getData() {
        return data;
    }

    public class StatusMessageResponseBody{
        @SerializedName("message")
        private String message;
        @SerializedName("status")
        private String status;

        public String getMessage() {
            return message;
        }

        public String getStatus() {
            return status;
        }
    }
}
