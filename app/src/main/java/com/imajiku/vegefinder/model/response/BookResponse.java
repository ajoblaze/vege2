package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class BookResponse {
    @SerializedName("data")
    private BookResponseBody data;

    public BookResponseBody getData() {
        return data;
    }

    public class BookResponseBody {
        @SerializedName("status")
        private String status;

        public String getStatus() {
            return status;
        }
    }
}
