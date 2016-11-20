package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class ReviewResponse {
    @SerializedName("data")
    private ReviewResponseBody data;

    public ReviewResponseBody getData() {
        return data;
    }

    public class ReviewResponseBody{
        @SerializedName("message")
        private String message;
        @SerializedName("status")
        private String status;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("place_id")
        private int placeId;
        @SerializedName("rate")
        private int rate;
        @SerializedName("title")
        private String title;
        @SerializedName("comment")
        private String comment;

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

        public int getRate() {
            return rate;
        }

        public String getTitle() {
            return title;
        }

        public String getComment() {
            return comment;
        }
    }
}
