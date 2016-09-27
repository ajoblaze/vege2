package com.imajiku.vegefinder.pojo;

import java.util.Date;

/**
 * Created by Alvin on 2016-09-27.
 */
public class Review {
    String profileImg;
    String name;
    String title;
    int datetime;
    String rating;
    String desc;

    public Review(String profileImg, String name, String title, int datetime, String rating, String desc) {
        this.profileImg = profileImg;
        this.name = name;
        this.title = title;
        this.datetime = datetime;
        this.rating = rating;
        this.desc = desc;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDateDiff(int now) {
        long diff = now - datetime;
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        if(minutes == 0) {
            return seconds+" second"+(seconds==1?"":"s")+" ago";
        }
        long hours = minutes / 60;
        if(hours == 0) {
            return minutes+" second"+(minutes==1?"":"s")+" ago";
        }
        long days = hours / 24;
        if(days == 0) {
            return hours+" second"+(hours==1?"":"s")+" ago";
        }else{
            return new Date(datetime).toString();
        }
    }

    public String getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }
}
