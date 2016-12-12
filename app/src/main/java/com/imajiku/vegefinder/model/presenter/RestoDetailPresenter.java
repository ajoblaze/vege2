package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.RestoDetailModel;
import com.imajiku.vegefinder.model.view.RestoDetailView;
import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.pojo.RestoImage;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RestoDetailPresenter {
    private RestoDetailView view;
    private RestoDetailModel model;

    public RestoDetailPresenter(RestoDetailView v) {
        this.view = v;
    }

    public RestoDetailView getView() {
        return view;
    }

    public void setModel(RestoDetailModel model) {
        this.model = model;
    }

    public void getRestoDetail(int placeId) {
        getRestoDetail(placeId, 1);
    }

    public void getRestoDetail(int placeId, int userId) {
        model.getRestoDetail(placeId, userId);
    }

    public void successGetRestoDetail(RestoDetail data) {
//        if (data.getRestoImg().size() == 0) {
//            data.setRestoImg(dummyImages());
//        }
        view.successGetRestoDetail(data);
    }

//    private ArrayList<RestoImage> dummyImages() {
//        ArrayList<RestoImage> dummy = new ArrayList<>();
//        String[] images = {
//                "http://globalgamejam.org/sites/default/files/styles/game_sidebar__normal/public/game/featured_image/promo_5.png?itok=9dymM8JD",
//                "https://fishofgold.files.wordpress.com/2016/04/jetbear-mnrart.gif?w=376",
//                "http://jokesforlosers.weebly.com/uploads/5/3/8/8/53885375/4968195_orig.png",
//                "http://www.awesomelyluvvie.com/wp-content/uploads/2015/11/Candy-Corn-Sadness-594x375.jpg",
//                "http://venture.mcmaster.ca/Camper_Websites/Week_4/Basketball/Hypnotoad-1.gif"
//        };
//        for (int i = 0; i < 5; i++) {
//            dummy.add(new RestoImage(i, images[i]));
//        }
//        return dummy;
//    }

    public void failedGetRestoDetail() {
        view.failedGetRestoDetail();
    }

    public void changeBookmark(int userId, int placeId, boolean isBookmarked) {
        model.changeBookmark(userId, placeId, isBookmarked);
    }

    public void changeBeenHere(int userId, int placeId, boolean hasBeenHere) {
        model.changeBeenHere(userId, placeId, hasBeenHere);
    }

    public void successChangeBookmark() {
        view.successChangeBookmark();
    }

    public void failedChangeBookmark(String message) {
        view.failedChangeBookmark(message);
    }

    public void successChangeBeenHere() {
        view.successChangeBeenHere();
    }

    public void failedChangeBeenHere(String message) {
        view.failedChangeBeenHere(message);
    }
}

