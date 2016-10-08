package com.imajiku.vegefinder.pojo;

/**
 * Created by Alvin on 2016-10-01.
 */
public class Resto {
    String imgPath;
    String name;
    float distance;
    float rating;
    int price;

    public Resto(){

    }

    public Resto(String imgPath, String name, float rating, int price) {
        this.imgPath = imgPath;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }

    public float getDistance() {
        return distance;
    }

    public float getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
