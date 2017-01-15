package com.imajiku.vegefinder.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-23.
 */
public class FindKeywordRequest {
    @SerializedName("keywords")
    private String keyword;
    @SerializedName("user_id")
    private String userId;

    public FindKeywordRequest(String keyword, String userId) {
        this.keyword = keyword;
        this.userId = userId;
    }
}
