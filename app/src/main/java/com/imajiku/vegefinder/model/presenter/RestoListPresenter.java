package com.imajiku.vegefinder.model.presenter;


import android.location.Location;
import android.util.Log;

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

    public void findAll(int countryId, int provinceId, int cityId, String keyword) {
        model.findAll(countryId, provinceId, cityId, keyword);
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
//        ArrayList<Resto> shortList = new ArrayList<>();
//        for (int i=0;i<data.size();i++){
//            Log.e("exc", "distance: "+data.get(i).getDistance());
//            if(data.get(i).getDistance() <= 25);
//        }
        view.successBrowseNearby(data);
    }

    public void sortData(ArrayList<Resto> data, String order){
        view.sortData(data, order);
    }

    public void setDistance(ArrayList<Resto> data, String longitude, String latitude){
        Location location = new Location("");
        location.setLatitude(Double.parseDouble(latitude));
        location.setLongitude(Double.parseDouble(longitude));
        for(Resto r : data){
            r.setDistance(location);
        }
    }

    public void failedBrowseNearby() {
        view.failedBrowseNearby();
    }

    public void getRecommendation(String longitude, String latitude) {
        model.getRecommendation(latitude, longitude);
    }

    public void successGetRecommendation(ArrayList<Resto> list) {
        ArrayList<Resto> shortList = new ArrayList<>();
        for (int i=0;i<20;i++){
            shortList.add(list.get(i));
        }
        view.successBrowseNearby(shortList);
    }

    public void failedGetRecommendation() {
        view.failedBrowseNearby();
    }

    public void changeBookmark(int userId, int placeId, boolean isBookmarked) {
        model.changeBookmark(userId, placeId, isBookmarked);
    }

    public void changeBeenHere(int userId, int placeId, boolean hasBeenHere) {
        model.changeBeenHere(userId, placeId, hasBeenHere);
    }

    public void successChangeBookmark(int placeId, boolean isBookmarked) {
        view.successChangeBookmark(placeId, isBookmarked);
    }

    public void failedChangeBookmark(String message) {
        view.failedChangeBookmark(message);
    }

    public void successChangeBeenHere(int placeId) {
        view.successChangeBeenHere(placeId);
    }

    public void failedChangeBeenHere(String message) {
        view.failedChangeBeenHere(message);
    }
}

