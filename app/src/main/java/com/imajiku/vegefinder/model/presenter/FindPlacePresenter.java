package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.FindPlaceModel;
import com.imajiku.vegefinder.model.view.FindPlaceView;
import com.imajiku.vegefinder.model.response.CountryResponse;
import com.imajiku.vegefinder.model.response.ProvinceResponse;

/**
 * Created by Alvin on 2016-10-08.
 */
public class FindPlacePresenter {
    private FindPlaceView view;
    private FindPlaceModel model;
    private ProvinceResponse provinceResponse;
    private CountryResponse countryResponse;

    public FindPlacePresenter(FindPlaceView v) {
        this.view =v;
    }

    public FindPlaceView getView() {
        return view;
    }

    public void setModel(FindPlaceModel model) {
        this.model = model;
    }

    public void findPlace(int provinceId, int cityId){
        model.findPlace(provinceId, cityId);
    }
}

