package com.imajiku.vegefinder.model.view;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface PhotoListView {
    void successGetRestoImages(ArrayList<String> list, String title);

    void failedGetRestoImages();

    void successAddPhoto(String message);

    void failedAddPhoto(String message);
}
