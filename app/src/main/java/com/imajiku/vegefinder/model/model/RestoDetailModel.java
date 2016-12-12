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

    public void changeBookmark(int userId, int placeId, final boolean isBookmarked) {
        ToggleRequest request = new ToggleRequest(userId, placeId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ToggleResponse> call;
        if(isBookmarked) {
            call = svc.addBookmark(request);
        }else{
            call = svc.removeBookmark(request);
        }
        Log.e(TAG, String.valueOf(call.request().url())+" UID:"+userId+" PID:"+placeId);
        call.enqueue(new Callback<ToggleResponse>() {
            @Override
            public void onResponse(Call<ToggleResponse> call, Response<ToggleResponse> response) {
                if (response.isSuccessful()) {
                    ToggleResponse.ToggleResponseBody data = response.body().getData();
                    if(!data.getStatus().equals("failed")) {
                        if(data.getStatus().equals("1")) {
                            presenter.successChangeBookmark();
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
        Log.e(TAG, String.valueOf(call.request().url())+" UID:"+userId+" PID:"+placeId);
        call.enqueue(new Callback<ToggleResponse>() {
            @Override
            public void onResponse(Call<ToggleResponse> call, Response<ToggleResponse> response) {
                if (response.isSuccessful()) {
                    ToggleResponse.ToggleResponseBody data = response.body().getData();
                    if(!data.getStatus().equals("failed")) {
                        if(data.getStatus().equals("1")) {
                            presenter.successChangeBeenHere();
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
