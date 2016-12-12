package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.MainModel;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.model.view.MainView;
import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Alvin on 2016-10-08.
 */
public class MainPresenter {
    private MainView view;
    private MainModel model;
    private ProvinceResponse provinceResponse;

    public MainPresenter(MainView v) {
        this.view =v;
    }

    public MainView getView() {
        return view;
    }

    public void setModel(MainModel model) {
        this.model = model;
    }

    public void getNews() {
        model.getNews();
    }

    public void getRecommendation(String longitude, String latitude) {
        model.getRecommendation(latitude, longitude);
    }

    public void getPlaces() {
        model.getPlaces();
    }

    public void successGetNews(ArrayList<News> news) {
        view.successGetNews(news);
    }

    public void failedGetNews() {
        view.failedGetNews();
    }

    public void successGetRecommendation(ArrayList<Resto> list) {
        ArrayList<Resto> shortList = new ArrayList<>();
        for (int i=0;i<5;i++){
            shortList.add(list.get(i));
        }
        view.successGetRecommendation(shortList);
    }

    public void failedGetRecommendation() {
        view.failedGetRecommendation();
    }

    public void successGetPlaces(ArrayList<Resto> list) {
        view.successGetPlaces(list);
    }

    public void failedGetPlaces() {
        view.failedGetPlaces();
    }

    public void getBookmarks(int userId) {
        model.getBookmarks(userId);
    }

    public void getBeenHere(int userId, String order) {
        model.getBeenHere(userId, order);
    }

    public void successGetBookmarks() {
    }

    public void failedGetBookmarks() {
    }

    public void successGetBeenHere() {
    }

    public void failedGetBeenHere() {
    }
}

