package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class LoginResponse {
    @SerializedName("data")
    private LoginResponseBody data;

    public LoginResponseBody getData() {
        return data;
    }

    public class LoginResponseBody {
        @SerializedName("status")
        private String status;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("email")
        private String email;

        public String getStatus() {
            return status;
        }

        public int getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }
    }
}
