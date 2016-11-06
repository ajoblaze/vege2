package com.imajiku.vegefinder.model.presenter;


import android.location.Location;

import com.imajiku.vegefinder.model.model.RestoListModel;
import com.imajiku.vegefinder.model.view.RestoListView;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RestoListPresenter {
    private RestoListView view;
    private RestoListModel model;

    public RestoListPresenter(RestoListView v) {
        this.view =v;
    }

    public RestoListView getView() {
        return view;
    }

    public void setModel(RestoListModel model) {
        this.model = model;
    }

    public void findByRegion(int provinceId, int cityId){
        model.findByRegion(provinceId, cityId);
    }

    public void findByKeyword(String keyword){
        model.findByKeyword(keyword);
    }

    public void browseNearby(String longitude, String latitude) {
        browseNearby(longitude, latitude, "distance", "asc", "");
    }

    public void browseNearby(String longitude, String latitude, String sortType, String order, String filter) {
        if(sortType.equals("")){
            sortType = "distance";
        }
        if(order.equals("")){
            order = "asc";
        }
        model.browseNearby(longitude, latitude, sortType, order, filter);
    }

    public void successFind(ArrayList<Resto> data) {
        view.successFind(data);
    }

    public void failedFind() {
        view.failedFind();
    }

    public void successBrowseNearby(ArrayList<Resto> data) {
        view.successBrowseNearby(data);
    }
    public void browseSortDistance(ArrayList<Resto> data, String longitude, String latitude, String order){
        Location location = new Location("");
        location.setLatitude(Double.parseDouble(latitude));
        location.setLongitude(Double.parseDouble(longitude));
        for(Resto r : data){
            r.setDistance(location);
        }
        view.sortData(data, order);
    }

    public void failedBrowseNearby() {
        view.failedBrowseNearby();
    }
}

