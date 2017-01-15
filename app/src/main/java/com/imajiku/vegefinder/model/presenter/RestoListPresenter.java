package com.imajiku.vegefinder.model.presenter;


import android.location.Location;

import com.imajiku.vegefinder.model.model.RestoListModel;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
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

    public void findByRegion(String countryId, String provinceId, String cityId, int userId){
        model.findByRegion(countryId, provinceId, cityId, getUserIdString(userId));
    }

    public void findByKeyword(String keyword, int userId){
        model.findByKeyword(keyword, getUserIdString(userId));
    }

    private String getUserIdString(int userId){
        if(userId != -1){
            return String.valueOf(userId);
        }
        return "-";
    }

    public void browseNearby(int userId, String longitude, String latitude) {
        browseNearby(userId, longitude, latitude, "distance", "asc", "");
    }

    public void browseNearby(int userId, String longitude, String latitude, String sortType, String order, String filter) {
        if(sortType.equals("")){
            sortType = "distance";
        }
        if(order.equals("")){
            order = "asc";
        }
        model.browseNearby(getUserIdString(userId), longitude, latitude, sortType, order, filter);
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

    public void getRecommendation() {
        model.getRecommendation();
    }

    public void successGetRecommendation(ArrayList<Resto> list) {
        if(list.size() > 20) {
            ArrayList<Resto> shortList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                shortList.add(list.get(i));
            }
            view.successGetRecommendation(shortList);
        }else {
            view.successGetRecommendation(list);
        }
    }

    public void failedGetRecommendation() {
        view.failedGetRecommendation();
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

    public void getSortFilterList(String type, SortFilterRequest request) {
        model.getSortFilterList(type, request);
    }

    public void successGetSortFilterList(ArrayList<Resto> list) {
        if(list.size() > 20) {
            ArrayList<Resto> shortList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                shortList.add(list.get(i));
            }
            view.successGetSortFilterList(shortList);
        }else {
            view.successGetSortFilterList(list);
        }
    }

    public void failedGetSortFilterList(String type) {
        view.failedGetSortFilterList(type);
    }
}

