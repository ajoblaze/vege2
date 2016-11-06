package com.imajiku.vegefinder.model.presenter;


import android.location.Location;

import com.imajiku.vegefinder.model.model.NewsListModel;
import com.imajiku.vegefinder.model.view.NewsListView;
import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class NewsListPresenter {
    private NewsListView view;
    private NewsListModel model;

    public NewsListPresenter(NewsListView v) {
        this.view =v;
    }

    public NewsListView getView() {
        return view;
    }

    public void setModel(NewsListModel model) {
        this.model = model;
    }

    public void loadNews() {
        model.loadNews();
    }

    public void successLoadNews(ArrayList<News> data) {
        view.successLoadNews(data);
    }

    public void failedLoadNews() {
        view.failedLoadNews();
    }
}

