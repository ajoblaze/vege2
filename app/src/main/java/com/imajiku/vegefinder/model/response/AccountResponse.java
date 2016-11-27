package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.pojo.UserProfile;

/**
 * Created by Alvin on 2016-10-08.
 */
public class AccountResponse {
    @SerializedName("data")
    private AccountResponseBody data;

    public AccountResponseBody getData() {
        return data;
    }

    public class AccountResponseBody {
        @SerializedName("status")
        private String status;
        @SerializedName("data")
        private UserProfile profile;

        public String getStatus() {
            return status;
        }

        public UserProfile getProfile() {
            return profile;
        }
    }
}
