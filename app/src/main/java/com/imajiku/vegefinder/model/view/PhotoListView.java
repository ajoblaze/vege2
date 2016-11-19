package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.RestoDetail;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface PhotoListView {
    void successGetRestoImages(ArrayList<String> list);

    void failedGetRestoImages();
}
