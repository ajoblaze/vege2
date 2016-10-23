package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class ContactUsResponse {
    @SerializedName("data")
    private ContactUsResponseBody data;

    public ContactUsResponseBody getData() {
        return data;
    }

    public class ContactUsResponseBody{
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
