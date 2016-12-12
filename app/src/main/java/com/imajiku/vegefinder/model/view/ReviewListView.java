package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.pojo.Review;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface ReviewListView {
    void successGetRestoReviews(RestoDetail restoDetail);

    void failedGetRestoReviews();
}
