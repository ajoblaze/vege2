package com.imajiku.vegefinder.pojo;

import android.location.Location;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.utility.Utility;

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
    @SerializedName("country")
    private String country;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
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
    @SerializedName("telephone")
    private String phone;
    @SerializedName("image")
    private String image;
    @SerializedName("image_url")
    private String imageUrl;
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
    private String averageRate;
    @SerializedName("status_bookmark")
    private String statusBookmark;

    String imgPath;
    String name;
    private float distance;
    int rating;
    int price;

    public boolean isBookmarked() {
        return statusBookmark.equals("active");
    }

    public void setBookmarked(boolean bookmarked) {
        if(bookmarked){
            statusBookmark = "active";
        }else{
            statusBookmark = "nonactive";
        }
    }

    public Resto(){

    }

    public Resto(String imgPath, String name, int rating, int price) {
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

    public int getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
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

    public String getImageUrl() {
        return Utility.attachImageUrl(imageUrl);
    }

    public String getPhone() {
        return phone;
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
        if(placeId == 0){
            return id;
        }
        return placeId;
    }

    public String getTitle() {
        return title == null? "" : title;
    }

    public String getContent() {
        return content;
    }

    public String getAverageRate() {
        return averageRate;
    }

    public String getStatusBookmark() {
        return statusBookmark;
    }
}
