package com.imajiku.vegefinder.pojo;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by Alvin on 2016-09-27.
 */
public class Review {
    @SerializedName("first_name")
    String firstName;
    @SerializedName("last_name")
    String lastName;
    @SerializedName("id")
    int reviewId;
    @SerializedName("place_id")
    int placeId;
    @SerializedName("user_id")
    int userId;
    @SerializedName("title")
    String title;
    @SerializedName("date_post")
    String datePost;
    @SerializedName("rate")
    double rate;
    @SerializedName("comment")
    String comment;
    @SerializedName("status")
    int status;

    public Review(String firstName, String lastName, int reviewId, int placeId, int userId, String title, String datePost, double rate, String comment, int status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.userId = userId;
        this.title = title;
        this.datePost = datePost;
        this.rate = rate;
        this.comment = comment;
        this.status = status;
    }

    public String getDateDiff(String now) {
//        datePost = "2016-09-28 21:55:55";
//        String pattern = "yyyy-MM-dd HH:mm:ss";
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        DateTime dt0 = dtf.parseDateTime(datePost);
        DateTime dt1 = dtf.parseDateTime(now);
        LocalDateTime ddate = new LocalDateTime(dt0);
        LocalDateTime dnow = new LocalDateTime(dt1);
        int days = Days.daysBetween(dnow, ddate).getDays();
        if(days > 0){
            if(days > 7) {
                return days + (days == 1 ? " day" : " days") + " ago";
            }else{
                return datePost;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDatePost() {
        return datePost;
    }

    public double getRate() {
        return rate;
    }

    public String getRateString() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(rate)+" / 10";
    }

    public String getComment() {
        return comment;
    }

    public int getStatus() {
        return status;
    }
}
