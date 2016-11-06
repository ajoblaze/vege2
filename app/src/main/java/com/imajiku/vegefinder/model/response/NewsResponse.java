package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.pojo.News;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class NewsResponse {
    @SerializedName("data")
    private ArrayList<News> data;

    public ArrayList<News> getData() {
        return data;
    }
}
