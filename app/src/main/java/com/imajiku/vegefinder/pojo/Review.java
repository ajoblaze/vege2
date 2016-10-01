package com.imajiku.vegefinder.pojo;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by Alvin on 2016-09-27.
 */
public class Review {
    String profileImg;
    String name;
    String title;
    String date;
    String rating;
    String desc;

    public String getDateDiff(String now) {
        date = "2016-09-28 21:55:55";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt0 = dtf.parseDateTime(date);
        DateTime dt1 = dtf.parseDateTime(now);
        LocalDateTime ddate = new LocalDateTime(dt0);
        LocalDateTime dnow = new LocalDateTime(dt1);
        int days = Days.daysBetween(dnow, ddate).getDays();
        if(days > 0){
            if(days > 7) {
                return days + (days == 1 ? " day" : " days") + " ago";
            }else{
                return date;
            }
        }else{
            int hours = Hours.hoursBetween(dnow, ddate).getHours() % 24;
            if(hours > 0){
                return hours + (hours == 1 ? " hour" : " hours") + " ago";
            } else {
                int minutes = Minutes.minutesBetween(dnow, ddate).getMinutes() % 60;
                if(minutes > 0){
                    return minutes + (minutes == 1 ? " minute" : " minutes") + " ago";
                }else{
                    int seconds = Seconds.secondsBetween(dnow, ddate).getSeconds() % 60;
                    return seconds + (seconds == 1 ? " second" : " seconds") + " ago";
                }
            }
        }
    }

    public Review(String profileImg, String name, String title, String date, String rating, String desc) {
        this.profileImg = profileImg;
        this.name = name;
        this.title = title;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }
}
