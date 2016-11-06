package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class FindKeywordRequest {
    @SerializedName("keywords")
    private String keyword;

    public FindKeywordRequest(String keyword) {
        this.keyword = keyword;
    }
}
