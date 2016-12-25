package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.PhotoListModel;
import com.imajiku.vegefinder.model.view.PhotoListView;
import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.pojo.RestoImage;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class PhotoListPresenter {
    private PhotoListView view;
    private PhotoListModel model;

    public PhotoListPresenter(PhotoListView v) {
        this.view =v;
    }

    public PhotoListView getView() {
        return view;
    }

    public void setModel(PhotoListModel model) {
        this.model = model;
    }

    public void getRestoImages(int placeId, int userId) {
        model.getRestoDetail(placeId, userId);
    }

    public void successGetRestoDetail(RestoDetail data) {
//        if(data.getRestoImg().size()==0){
//            data.setRestoImg(dummyImages());
//        }
        ArrayList<RestoImage> restoImage = data.getRestoImg();
        ArrayList<String> list = new ArrayList<>();
        for(RestoImage r : restoImage){
            list.add(r.getImage());
        }
        view.successGetRestoImages(list, data.getTitle());
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
//        for(int i=0;i<5;i++){
//            dummy.add(new RestoImage(i, images[i]));
//        }
//        return dummy;
//    }

    public void failedGetRestoDetail() {
        view.failedGetRestoImages();
    }

    public void addPhoto(int userId, int restoId, String image, String imageCode){
        model.addPhoto(userId, restoId, image, imageCode);
    }

    public void successAddPhoto(String message) {
        view.successAddPhoto(message);
    }

    public void failedAddPhoto(String message) {
        if(message.isEmpty()){
            message = "Connection failed";
        }
        view.failedAddPhoto(message);
    }
}

