package com.imajiku.vegefinder.model.view;

import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface FindPlaceView {
    void successFindKeyword(ArrayList<Resto> data);

    void successFindPlace(ArrayList<Resto> data);
}
