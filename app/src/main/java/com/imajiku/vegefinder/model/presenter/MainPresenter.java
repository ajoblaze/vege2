package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.MainModel;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
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

    public void successGetNews(ArrayList<News> news) {
        view.successGetNews(news);
    }

    public void failedGetNews() {
        view.failedGetNews();
    }

    public void successGetRecommendation(ArrayList<Resto> list) {
        if(list.size()>5) {
            ArrayList<Resto> shortList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                shortList.add(list.get(i));
            }
            view.successGetRecommendation(shortList);
        }else{
            view.successGetRecommendation(list);
        }
    }

    public void failedGetRecommendation() {
        view.failedGetRecommendation();
    }

    public void getBookmarks(SortFilterRequest request) {
        model.getBookmarks(request);
    }

    public void successGetBookmarks(ArrayList<Resto> list) {
        if(list.size()>5) {
            ArrayList<Resto> shortList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                shortList.add(list.get(i));
            }
            view.successGetBookmarks(shortList);
        }else{
            view.successGetBookmarks(list);
        }
    }

    public void failedGetBookmarks() {
        view.failedGetBookmarks();
    }

    public void getBeenHere(SortFilterRequest request) {
        model.getBeenHere(request);
    }

    public void successGetBeenHere(ArrayList<Resto> data) {
        view.successGetBeenHere(data);
    }

    public void failedGetBeenHere() {
        view.failedGetBeenHere();
    }
}

