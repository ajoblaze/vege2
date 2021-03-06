package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class ReportResponse {
    @SerializedName("data")
    private ReportResponseBody data;

    public ReportResponseBody getData() {
        return data;
    }

    public class ReportResponseBody{
        @SerializedName("user_id")
        private int userId;
        @SerializedName("place_id")
        private int placeId;
        @SerializedName("subject")
        private String subject;
        @SerializedName("comment")
        private String comment;
        @SerializedName("date_added")
        private String date;
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

        public int getUserId() {
            return userId;
        }

        public String getSubject() {
            return subject;
        }

        public String getComment() {
            return comment;
        }

        public String getDate() {
            return date;
        }

        public int getPlaceId() {
            return placeId;
        }
    }
}
