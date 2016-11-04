package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

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

    public class News {
        @SerializedName("id")
        private int id;
        @SerializedName("slug")
        private String slug;
        @SerializedName("meta_title")
        private String metaTitle;
        @SerializedName("meta_keywords")
        private String metaKeywords;
        @SerializedName("meta_description")
        private String metaDescription;
        @SerializedName("image")
        private String image;
        @SerializedName("date_post")
        private String datePost;
        @SerializedName("status")
        private String status;
        @SerializedName("sorting")
        private int sorting;
        @SerializedName("news_id")
        private int newsId;
        @SerializedName("title")
        private String title;
        @SerializedName("content")
        private String content;

        public int getId() {
            return id;
        }

        public String getSlug() {
            return slug;
        }

        public String getMetaTitle() {
            return metaTitle;
        }

        public String getMetaKeywords() {
            return metaKeywords;
        }

        public String getMetaDescription() {
            return metaDescription;
        }

        public String getImage() {
            return image;
        }

        public String getDatePost() {
            return datePost;
        }

        public String getStatus() {
            return status;
        }

        public int getSorting() {
            return sorting;
        }

        public int getNewsId() {
            return newsId;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }
}
