package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.ReviewListModel;
import com.imajiku.vegefinder.model.view.ReviewListView;
import com.imajiku.vegefinder.pojo.RestoDetail;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ReviewListPresenter {
    private ReviewListView view;
    private ReviewListModel model;

    public ReviewListPresenter(ReviewListView v) {
        this.view =v;
    }

    public ReviewListView getView() {
        return view;
    }

    public void setModel(ReviewListModel model) {
        this.model = model;
    }

    public void getRestoReviews(int placeId, int userId) {
        model.getRestoDetail(placeId, userId);
    }

    public void successGetRestoDetail(RestoDetail data) {
//        view.successGetRestoReviews();
    }

    public void failedGetRestoDetail() {
        view.failedGetRestoReviews();
    }
}

