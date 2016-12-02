package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.RestoListPresenter;
import com.imajiku.vegefinder.model.request.FindAllRequest;
import com.imajiku.vegefinder.model.request.FindKeywordRequest;
import com.imajiku.vegefinder.model.request.FindRegionRequest;
import com.imajiku.vegefinder.model.response.RestoListResponse;
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

    private static final String TAG = "exc";
    private RestoListPresenter presenter;
    private Retrofit retrofit;

    public RestoListModel(RestoListPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void findByRegion(int provinceId, int cityId) {
        FindRegionRequest request = new FindRegionRequest(provinceId, cityId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.findRegion(request);
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    presenter.successFind(data);
                } else {
                    presenter.failedFind();
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedFind();
            }
        });
    }

    public void findByKeyword(String keyword) {
        FindKeywordRequest request = new FindKeywordRequest(keyword);
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.findKeyword(request);
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    presenter.successFind(data);
                } else {
                    presenter.failedFind();
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedFind();
            }
        });
    }

    public void findAll(int countryId, int provinceId, int cityId, String keyword) {
        FindAllRequest request = new FindAllRequest(provinceId, cityId, keyword);
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.findAll(request);
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    presenter.successFind(data);
                } else {
                    presenter.failedFind();
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedFind();
            }
        });
    }

    public void browseNearby(final String longitude, final String latitude, final String sortType, final String order, String filter) {
        String location = longitude + "," + latitude;
        String sort;
        if (sortType.equals("distance")) {
            sort = "title-" + order; // any sortType will do
        } else {
            sort = sortType + "-" + order;
        }
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.browseNearby(location, sort, filter);
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    presenter.setDistance(data, longitude, latitude);
                    if (sortType.equals("distance")) {
                        presenter.sortData(data, order);
                    } else {
                        presenter.successBrowseNearby(data);
                    }
                } else {
                    presenter.failedBrowseNearby();
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedBrowseNearby();
            }
        });
    }

    public void getRecommendation(String latitude, String longitude) {
        String location = longitude + "," + latitude;
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.browseNearby(location, "average_rate-desc", "");
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    presenter.successGetRecommendation(data);
                } else {
                    presenter.failedGetRecommendation();
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedGetRecommendation();
            }
        });
    }
}
