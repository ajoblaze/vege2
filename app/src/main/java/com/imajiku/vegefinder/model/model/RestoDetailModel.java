package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.RestoDetailPresenter;
import com.imajiku.vegefinder.model.request.ToggleRequest;
import com.imajiku.vegefinder.model.response.ToggleResponse;
import com.imajiku.vegefinder.model.response.RestoDetailResponse;
import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RestoDetailModel {

    private static final String TAG = "exc";
    private RestoDetailPresenter presenter;
    private Retrofit retrofit;

    public RestoDetailModel(RestoDetailPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void getRestoDetail(int placeId, int userId) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoDetailResponse> call = svc.getDetailPlace(placeId, userId);
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoDetailResponse>() {
            @Override
            public void onResponse(Call<RestoDetailResponse> call, Response<RestoDetailResponse> response) {
                if (response.isSuccessful()) {
                    RestoDetail data = response.body().getData();
                    presenter.successGetRestoDetail(data);
                } else {
                    presenter.failedGetRestoDetail();
                }
            }

            @Override
            public void onFailure(Call<RestoDetailResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
                presenter.failedGetRestoDetail();
            }
        });
    }

    public void addBookmark(int userId, int placeId){
        ToggleRequest request = new ToggleRequest(userId, placeId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ToggleResponse> call = svc.addBookmark(request);
        call.enqueue(new Callback<ToggleResponse>() {
            @Override
            public void onResponse(Call<ToggleResponse> call, Response<ToggleResponse> response) {
                if (response.isSuccessful()) {
                    ToggleResponse.ToggleResponseBody data = response.body().getData();
                    if(data.getStatus().equals("1")) {
                        presenter.successAddBookmark();
                    } else if(data.getStatus().equals("2")) {
                        presenter.failedAddBookmark("Place already bookmarked");
                    } else {
                        presenter.failedAddBookmark("Failed to bookmark");
                    }
                } else {
                    presenter.failedAddBookmark("Failed to bookmark");
                }
            }

            @Override
            public void onFailure(Call<ToggleResponse> call, Throwable t) {
                presenter.failedAddBookmark("Failed to bookmark");
            }
        });
    }

    public void removeBookmark(int userId, int placeId){
        ToggleRequest request = new ToggleRequest(userId, placeId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ToggleResponse> call = svc.removeBookmark(request);
        call.enqueue(new Callback<ToggleResponse>() {
            @Override
            public void onResponse(Call<ToggleResponse> call, Response<ToggleResponse> response) {
                if (response.isSuccessful()) {
                    ToggleResponse.ToggleResponseBody data = response.body().getData();
                    if(data.getStatus().equals("1")) {
                        presenter.successRemoveBookmark();
                    } else {
                        presenter.failedRemoveBookmark("Failed to remove bookmark");
                    }
                } else {
                    presenter.failedRemoveBookmark("Failed to remove bookmark");
                }
            }

            @Override
            public void onFailure(Call<ToggleResponse> call, Throwable t) {
                presenter.failedRemoveBookmark("Failed to remove bookmark");
            }
        });
    }

    public void addBeenHere(int userId, int placeId){
        ToggleRequest request = new ToggleRequest(userId, placeId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ToggleResponse> call = svc.addBeenHere(request);
        call.enqueue(new Callback<ToggleResponse>() {
            @Override
            public void onResponse(Call<ToggleResponse> call, Response<ToggleResponse> response) {
                if (response.isSuccessful()) {
                    ToggleResponse.ToggleResponseBody data = response.body().getData();
                    if(data.getStatus().equals("1")) {
                        presenter.successAddBeenHere();
                    } else if(data.getStatus().equals("2")) {
                        presenter.failedAddBeenHere("Already been here");
                    } else {
                        presenter.failedAddBeenHere("Failed to set been here");
                    }
                } else {
                    presenter.failedAddBeenHere("Failed to set been here");
                }
            }

            @Override
            public void onFailure(Call<ToggleResponse> call, Throwable t) {
                presenter.failedAddBookmark("Failed to set been here");
            }
        });
    }

    public void removeBeenHere(int userId, int placeId){
        ToggleRequest request = new ToggleRequest(userId, placeId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ToggleResponse> call = svc.removeBeenHere(request);
        call.enqueue(new Callback<ToggleResponse>() {
            @Override
            public void onResponse(Call<ToggleResponse> call, Response<ToggleResponse> response) {
                if (response.isSuccessful()) {
                    ToggleResponse.ToggleResponseBody data = response.body().getData();
                    if(data.getStatus().equals("1")) {
                        presenter.successRemoveBeenHere();
                    } else {
                        presenter.failedRemoveBeenHere("Failed to undo been here");
                    }
                } else {
                    presenter.failedRemoveBeenHere("Failed to undo been here");
                }
            }

            @Override
            public void onFailure(Call<ToggleResponse> call, Throwable t) {
                presenter.failedRemoveBeenHere("Failed to undo been here");
            }
        });
    }

//    public void browseNearby(final String longitude, final String latitude, final String sortType, final String order, String filter) {
//        String location = longitude + "," + latitude;
//        String sort;
//        if (sortType.equals("distance")) {
//            sort = "title-" + order; // any sortType will do
//        } else {
//            sort = sortType + "-" + order;
//        }
//        ApiService svc = retrofit.create(ApiService.class);
//        Call<RestoDetailResponse> call = svc.browseNearby(location, sort, filter);
//        Log.e(TAG, String.valueOf(call.request().url()));
//        call.enqueue(new Callback<RestoDetailResponse>() {
//            @Override
//            public void onResponse(Call<RestoDetailResponse> call, Response<RestoDetailResponse> response) {
//                if (response.isSuccessful()) {
//                    ArrayList<Resto> data = response.body().getData();
//                    if (sortType.equals("distance")) {
//                        presenter.sortData(data, longitude, latitude, order);
//                    } else {
//                        presenter.successBrowseNearby(data);
//                    }
//                } else {
//                    presenter.failedBrowseNearby();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RestoDetailResponse> call, Throwable t) {
//                presenter.failedBrowseNearby();
//            }
//        });
//    }
}
