package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.RestoDetail;

/**
 * Created by Alvin on 2016-11-05.
 */
public interface RestoDetailView {
    void successGetRestoDetail(RestoDetail data);

    void failedGetRestoDetail();

    void successChangeBookmark();

    void failedChangeBookmark(String message);

    void successChangeBeenHere();

    void failedChangeBeenHere(String message);
}
