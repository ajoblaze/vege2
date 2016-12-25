package com.imajiku.vegefinder.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alvin on 2016-10-01.
 */
public class RestoPreview {
    private int id;
    private String image;
    private String title;
    private String city;

    public RestoPreview(int id, String image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public RestoPreview(int id, String image, String title, String city) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }
}
