package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.MainModel;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.model.view.MainView;

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

    public void successGetNews() {
        view.successGetNews();
    }

    public void failedGetNews() {
        view.failedGetNews();
    }

    public void getRecommendation() {
        model.getRecommendation();
    }

    public void getPlaces() {
        model.getPlaces();
    }
}

