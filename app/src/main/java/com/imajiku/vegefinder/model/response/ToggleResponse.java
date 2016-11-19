package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class ToggleResponse {
    @SerializedName("data")
    private ToggleResponseBody data;

    public ToggleResponseBody getData() {
        return data;
    }

    public class ToggleResponseBody {
        @SerializedName("message")
        private String message;
        @SerializedName("status")
        private String status;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("place_id")
        private int placeID;
        @SerializedName("date_added")
        private String date;

        public String getMessage() {
            return message;
        }

        public String getStatus() {
            return status;
        }

        public int getUserId() {
            return userId;
        }

        public int getPlaceID() {
            return placeID;
        }

        public String getDate() {
            return date;
        }
    }
}
