package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class CheckInResponse {
    @SerializedName("data")
    private CheckInResponseBody data;

    public CheckInResponseBody getData() {
        return data;
    }

    public class CheckInResponseBody {
        @SerializedName("message")
        private String message;
        @SerializedName("status")
        private String status;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("place_id")
        private int placeId;
        @SerializedName("date_added")
        private String date;
        @SerializedName("comment")
        private String comment;

        public String getComment() {
            return comment;
        }

        public String getMessage() {
            return message;
        }

        public String getStatus() {
            return status;
        }

        public int getUserId() {
            return userId;
        }

        public int getPlaceId() {
            return placeId;
        }

        public String getDate() {
            return date;
        }
    }
}
