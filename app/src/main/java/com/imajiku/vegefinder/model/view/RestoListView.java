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

    void successChangeBookmark(int placeId, boolean isBookmarked);

    void failedChangeBookmark(String message);

    void successChangeBeenHere(int placeId);

    void failedChangeBeenHere(String message);

    void successGetSortFilterList(ArrayList<Resto> list);

    void failedGetSortFilterList(String type);

    void successGetRecommendation(ArrayList<Resto> list);

    void failedGetRecommendation();
}
