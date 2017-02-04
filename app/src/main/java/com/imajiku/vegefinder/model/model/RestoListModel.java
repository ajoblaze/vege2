package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.RestoListPresenter;
import com.imajiku.vegefinder.model.request.FindKeywordRequest;
import com.imajiku.vegefinder.model.request.FindRegionRequest;
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
    private static final String TAG = "exc";
    private RestoListPresenter presenter;
    private Retrofit retrofit;

    public RestoListModel(RestoListPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void findByRegion(String countryId, String provinceId, String cityId, String userId) {
        FindRegionRequest request = new FindRegionRequest(countryId, provinceId, cityId, userId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.findRegion(request);
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    presenter.successFind(data);
                } else {
                    Log.e(TAG, "as");
                    presenter.failedFind();
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                presenter.failedFind();
            }
        });
    }

    public void findByKeyword(String keyword, String userId) {
        FindKeywordRequest request = new FindKeywordRequest(keyword, userId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.findKeyword(request);
        Log.e(TAG, String.valueOf(call.request().url()));
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

    public void browseNearby(String userId, final String longitude, final String latitude, final String sortType, final String order, String filter) {
        String location = longitude + "," + latitude;
        String sort;
        if (sortType.equals("distance")) {
            sort = "title-" + order; // any sortType will do
        } else {
            sort = sortType + "-" + order;
        }
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.browseNearby(userId, location, sort, filter);
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

    public void getRecommendation() {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.mightLike();
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
                Log.e(TAG, "onFailure: "+t.getMessage());
                presenter.failedGetRecommendation();
            }
        });
    }

//    public void getRecommendation(String userId, String latitude, String longitude) {
//        String location = longitude + "," + latitude;
//        ApiService svc = retrofit.create(ApiService.class);
//        Call<RestoListResponse> call = svc.browseNearby(userId, location, "average_rate-desc", "");
//        Log.e(TAG, String.valueOf(call.request().url()));
//        call.enqueue(new Callback<RestoListResponse>() {
//            @Override
//            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
//                if (response.isSuccessful()) {
//                    ArrayList<Resto> data = response.body().getData();
//                    presenter.successGetRecommendation(data);
//                } else {
//                    presenter.failedGetRecommendation();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RestoListResponse> call, Throwable t) {
//                presenter.failedGetRecommendation();
//            }
//        });
//    }

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
        Log.e(TAG, String.valueOf(call.request().url())+" UID:"+userId+" PID:"+placeId);
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
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call;
        if(type.equals(BOOKMARK)) {
            call = svc.getBookmarks(request);
        }else if(type.equals(BEENHERE)) {
            call = svc.getBeenHere(request);
        }else{
            presenter.failedGetSortFilterList(type);
            return;
        }
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    presenter.successGetSortFilterList(data);
                } else {
                    presenter.failedGetSortFilterList(type);
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedGetSortFilterList(type);
            }
        });
    }
}
