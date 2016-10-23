package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.pojo.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alvin on 2016-10-08.
 */
public class CityResponse {
    @SerializedName("data")
    private ArrayList<City> data;

    public ArrayList<City> getData() {
        return data;
    }

    public ArrayList<String> getCities(){
        ArrayList<String> cities = new ArrayList<>();
        for(City c : data){
            cities.add(c.getCity());
        }
        return cities;
    }
}
