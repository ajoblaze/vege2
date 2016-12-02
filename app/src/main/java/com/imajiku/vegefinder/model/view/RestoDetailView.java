package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.RestoDetail;

/**
 * Created by Alvin on 2016-11-05.
 */
public interface RestoDetailView {
    void successGetRestoDetail(RestoDetail data);

    void failedGetRestoDetail();

    void successAddBookmark();

    void failedAddBookmark(String message);

    void successRemoveBookmark();

    void failedRemoveBookmark(String message);

    void successAddBeenHere();

    void failedAddBeenHere(String message);

    void successRemoveBeenHere();

    void failedRemoveBeenHere(String message);
}
