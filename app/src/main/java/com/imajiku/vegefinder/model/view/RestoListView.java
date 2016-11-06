package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-11-05.
 */
public interface RestoListView {
    void successFind(ArrayList<Resto> data);

    void failedFind();

    void successBrowseNearby(ArrayList<Resto> data);

    void failedBrowseNearby();

    void sortData(ArrayList<Resto> data, String order);
}
