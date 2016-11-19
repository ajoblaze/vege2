package com.imajiku.vegefinder.model.presenter;

import android.util.Log;

import com.imajiku.vegefinder.model.model.RegionModel;
import com.imajiku.vegefinder.model.view.RegionView;
import com.imajiku.vegefinder.model.response.CityResponse;
import com.imajiku.vegefinder.model.response.CountryResponse;
import com.imajiku.vegefinder.model.response.ProvinceResponse;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegionPresenter {
    public static final int PROVINCE = 10;
    public static final int CITY = 11;
    public static final int COUNTRY = 9;
    private RegionView view;
    private RegionModel model;
    private CityResponse cityResponse;
    private ProvinceResponse provinceResponse;
    private CountryResponse countryResponse;

    public RegionPresenter(RegionView v) {
        this.view =v;
    }

    public RegionView getView() {
        return view;
    }

    public void setModel(RegionModel model) {
        this.model = model;
    }

    public void getCountry() {
        model.getCountry();
    }

    public void getProvince(String country) {
        if(country.equals("")){
            return;
        }
        int countryId = countryResponse.getData().getCountryId(country);
        Log.e("exc", "getProvince: "+countryId);
        model.getProvince(countryId);
    }

    public void getCity(String province) {
        if(province.equals("")){
            return;
        }
        int provinceId = provinceResponse.getData().getProvinceId(province);
        Log.e("exc", "getCity: "+provinceId);
        model.getCity(provinceId);
    }

    public void successGetProvince(ProvinceResponse response){
        this.provinceResponse = response;
        view.updateDropdown(PROVINCE, response.getData().getProvinceNames());
    }

    public void failedGetProvince(){
        this.cityResponse = null;
        view.updateDropdown(PROVINCE, new ArrayList<String>());
    }

    public void successGetCity(CityResponse response){
        this.cityResponse = response;
        view.updateDropdown(CITY, response.getData().getCityNames());
    }

    public void failedGetCity(){
        this.cityResponse = null;
        view.updateDropdown(CITY, new ArrayList<String>());
    }

    public void successGetCountry(CountryResponse response) {
        this.countryResponse = response;
        view.updateDropdown(COUNTRY, response.getData().getCountryNames());
    }

    public void failedGetCountry(){
        this.cityResponse = null;
        view.updateDropdown(COUNTRY, new ArrayList<String>());
    }

    public int getCountryId(String country) {
        if(countryResponse == null){
            return -1;
        }
        return countryResponse.getData().getCountryId(country);
    }

    public int getProvinceId(String province) {
        if(provinceResponse == null){
            return -1;
        }
        return provinceResponse.getData().getProvinceId(province);
    }

    public int getCityId(String city) {
        if(cityResponse == null){
            return -1;
        }
        return cityResponse.getData().getCityId(city);
    }
}

