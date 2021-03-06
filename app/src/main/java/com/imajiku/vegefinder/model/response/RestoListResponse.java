package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-23.
 */
public class RestoListResponse {
    @SerializedName("data")
    private ArrayList<Resto> data;
    @SerializedName("error")
    private ErrorMessage error;

    public ArrayList<Resto> getData() {
        return data;
    }

    public ErrorMessage getError() {
        return error;
    }

    public class ErrorMessage{
        @SerializedName("message")
        private String message;

        public String getMessage() {
            return message;
        }
    }
}
