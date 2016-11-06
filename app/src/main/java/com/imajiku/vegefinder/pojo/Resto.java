package com.imajiku.vegefinder.pojo;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alvin on 2016-10-01.
 */
public class Resto implements Serializable{
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("country_id")
    private int countryId;
    @SerializedName("province_id")
    private int provinceId;
    @SerializedName("city_id")
    private int cityId;
    @SerializedName("slug")
    private String slug;
    @SerializedName("meta_title")
    private String metaTitle;
    @SerializedName("meta_keywords")
    private String metaKeywords;
    @SerializedName("meta_description")
    private String metaDescription;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("address")
    private String address;
    @SerializedName("price_start")
    private int priceStart;
    @SerializedName("price_rate")
    private int priceRate;
    @SerializedName("image")
    private String image;
    @SerializedName("youtube_url")
    private String youtubeUrl;
    @SerializedName("open")
    private String open;
    @SerializedName("date_post")
    private String datePost;
    @SerializedName("status")
    private int status;
    @SerializedName("hits")
    private int hits;
    @SerializedName("wifi")
    private int wifi;
    @SerializedName("buffet")
    private int buffet;
    @SerializedName("delivery")
    private int delivery;
    @SerializedName("type")
    private int type;
    @SerializedName("sorting")
    private int sorting;
    @SerializedName("place_id")
    private int placeId;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("average_rate")
    private int averageRate;

    String imgPath;
    String name;
    private float distance;
    float rating;
    int price;
    boolean bookmarked;

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Resto(){

    }

    public Resto(String imgPath, String name, float rating, int price) {
        this.imgPath = imgPath;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public void setDistance(Location location) {
        Location store = new Location("");
        store.setLongitude(Double.parseDouble(getLongitude()));
        store.setLatitude(Double.parseDouble(getLatitude()));
        distance = location.distanceTo(store);
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }

    public float getDistance() {
        return distance/1000;
    }

    public float getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public int getCityId() {
        return cityId;
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

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public int getPriceStart() {
        return priceStart;
    }

    public int getPriceRate() {
        return priceRate;
    }

    public String getImage() {
        return image;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getOpen() {
        return open;
    }

    public String getDatePost() {
        return datePost;
    }

    public int getStatus() {
        return status;
    }

    public int getHits() {
        return hits;
    }

    public int getWifi() {
        return wifi;
    }

    public int getBuffet() {
        return buffet;
    }

    public int getDelivery() {
        return delivery;
    }

    public int getType() {
        return type;
    }

    public int getSorting() {
        return sorting;
    }

    public int getPlaceId() {
        return placeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getAverageRate() {
        return averageRate;
    }
}
