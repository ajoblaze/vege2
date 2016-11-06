package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-11-05.
 */
public interface NewsListView {
    void successLoadNews(ArrayList<News> data);

    void failedLoadNews();
}
