package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterResponse {
    @SerializedName("data")
    private RegisterResponseBody data;

    public RegisterResponseBody getData() {
        return data;
    }

    public class RegisterResponseBody {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("activation_code")
        private String code;
        @SerializedName("status")
        private String status;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getCode() {
            return code;
        }

        public String getStatus() {
            return status;
        }
    }
}
