package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class StatusResponse {
    @SerializedName("data")
    private StatusResponseBody data;

    public StatusResponseBody getData() {
        return data;
    }

    public class StatusResponseBody {
        @SerializedName("status")
        private String status;
        @SerializedName("message")
        private String message;

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
