package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class FeedbackResponse {
    @SerializedName("data")
    private FeedbackResponseBody data;

    public FeedbackResponseBody getData() {
        return data;
    }

    public class FeedbackResponseBody{
        @SerializedName("user_id")
        private int userId;
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
    }
}
