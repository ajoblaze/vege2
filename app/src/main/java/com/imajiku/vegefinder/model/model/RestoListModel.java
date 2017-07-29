package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.google.gson.Gson;
import com.imajiku.vegefinder.model.presenter.RestoListPresenter;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
import com.imajiku.vegefinder.model.request.ToggleRequest;
import com.imajiku.vegefinder.model.response.RestoListResponse;
import com.imajiku.vegefinder.model.response.ToggleResponse;
import com.imajiku.vegefinder.pojo.Resto;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RestoListModel {

    public static final String BOOKMARK = "bookmark";
    public static final String BEENHERE = "been here";
    public static final String RECOMMEND = "recommendation";
    public static final String SEARCH_R = "search region result";
    public static final String SEARCH_K = "search keyword result";
    private static final String TAG = "exc";
    private RestoListPresenter presenter;
    private Retrofit retrofit;

    public RestoListModel(RestoListPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void browseNearby(SortFilterRequest request) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.browseNearby(request);
//        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    if(data!=null) {
                        presenter.successBrowseNearby(data);
                    }else{
                        String error = response.body().getError().getMessage();
                        if(error != null){
                            presenter.failedBrowseNearby(error);
                        }
                    }
                } else {
                    presenter.failedBrowseNearby("Failed getting data");
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedBrowseNearby("Failed getting data");
            }
        });
    }

    public void changeBookmark(int userId, int placeId, final boolean isBookmarked) {
        ToggleRequest request = new ToggleRequest(userId, placeId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ToggleResponse> call;
        if(isBookmarked) {
            call = svc.addBookmark(request);
        }else{
            call = svc.removeBookmark(request);
        }
//        Log.e(TAG, String.valueOf(call.request().url())+" UID:"+userId+" PID:"+placeId);
        call.enqueue(new Callback<ToggleResponse>() {
            @Override
            public void onResponse(Call<ToggleResponse> call, Response<ToggleResponse> response) {
                if (response.isSuccessful()) {
                    ToggleResponse.ToggleResponseBody data = response.body().getData();
                    if(!data.getStatus().equals("failed")) {
                        if(data.getStatus().equals("1")) {
                            presenter.successChangeBookmark(data.getPlaceId(), isBookmarked);
                        }else{
                            presenter.failedChangeBookmark(data.getMessage());
                        }
                    } else {
                        presenter.failedChangeBookmark("Please check your connection");
                    }
                } else {
                    presenter.failedChangeBookmark("Please check your connection");
                }
            }

            @Override
            public void onFailure(Call<ToggleResponse> call, Throwable t) {
                presenter.failedChangeBookmark("Please check your connection");
            }
        });
    }

    public void changeBeenHere(int userId, int placeId, boolean hasBeenHere) {
        ToggleRequest request = new ToggleRequest(userId, placeId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ToggleResponse> call;
        if(hasBeenHere) {
            call = svc.addBeenHere(request);
        }else{
            call = svc.removeBeenHere(request);
        }
//        Log.e(TAG, String.valueOf(call.request().url())+" UID:"+userId+" PID:"+placeId);
        call.enqueue(new Callback<ToggleResponse>() {
            @Override
            public void onResponse(Call<ToggleResponse> call, Response<ToggleResponse> response) {
                if (response.isSuccessful()) {
                    ToggleResponse.ToggleResponseBody data = response.body().getData();
                    if(!data.getStatus().equals("failed")) {
                        if(data.getStatus().equals("1")) {
                            presenter.successChangeBeenHere(data.getPlaceId());
                        }else{
                            presenter.failedChangeBeenHere(data.getMessage());
                        }
                    } else {
                        presenter.failedChangeBeenHere("Please check your connection");
                    }
                } else {
                    presenter.failedChangeBeenHere("Please check your connection");
                }
            }

            @Override
            public void onFailure(Call<ToggleResponse> call, Throwable t) {
                presenter.failedChangeBeenHere("Please check your connection");
            }
        });
    }

    public void getSortFilterList(final String type, SortFilterRequest request) {
        request.setDefaultLoc("-8.687115,115.213868");
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call;
        if(type.equals(BOOKMARK)) {
            call = svc.getBookmarks(request);
        }else if(type.equals(BEENHERE)) {
            call = svc.getBeenHere(request);
        }else if(type.equals(RECOMMEND)) {
            call = svc.mightLike(request);
        }else if(type.equals(SEARCH_R)) {
            call = svc.findRegion(request);
        }else if(type.equals(SEARCH_K)) {
            call = svc.findKeyword(request);
        }else{
            presenter.failedGetSortFilterList(type);
            return;
        }
//        Log.e(TAG, String.valueOf(call.request().url()));

//        String json = new Gson().toJson(request);
//        Log.e(TAG, "LOAD "+json);
//        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    if(data!=null){
                        presenter.successGetSortFilterList(data);
                    } else {
                        presenter.failedGetSortFilterList(type);
                    }
                } else {
                    presenter.failedGetSortFilterList(type);
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
//                Log.e(TAG, t.getMessage());
                presenter.failedGetSortFilterList(type);
            }
        });
    }
}
