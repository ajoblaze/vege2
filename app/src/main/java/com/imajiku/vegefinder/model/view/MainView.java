package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface MainView {
    void successGetNews(ArrayList<News> news);

    void failedGetNews();

    void successGetRecommendation(ArrayList<Resto> list);

    void failedGetRecommendation();

    void successGetBookmarks(ArrayList<Resto> shortList);

    void failedGetBookmarks();

    void successGetBeenHere(ArrayList<Resto> data);

    void failedGetBeenHere();
}
