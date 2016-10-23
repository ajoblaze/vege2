package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.pojo.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ProvinceResponse {
    @SerializedName("data")
    private ArrayList<Province> data;

    public ArrayList<Province> getData() {
        return data;
    }

    public ArrayList<String> getProvinces(){
        ArrayList<String> provinces = new ArrayList<>();
        for(Province p : data){
            provinces.add(p.getProvince());
        }
        return provinces;
    }

    public int getProvinceId(String province) {
        for(Province p : getData()){
            if(p.getProvince().equals(province)){
                return p.getId();
            }
        }
        return -1;
    }
}
