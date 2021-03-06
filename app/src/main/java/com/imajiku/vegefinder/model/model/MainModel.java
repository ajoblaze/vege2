package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.MainPresenter;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
import com.imajiku.vegefinder.model.response.RestoListResponse;
import com.imajiku.vegefinder.model.response.NewsResponse;
import com.imajiku.vegefinder.pojo.News;
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
public class MainModel {

    private static final String TAG = "exc";
    private MainPresenter presenter;
    private Retrofit retrofit;

    public MainModel(MainPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void getNews() {
        ApiService svc = retrofit.create(ApiService.class);
        Call<NewsResponse> call = svc.generateNews();
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        ArrayList<News> news = response.body().getData();
                        if(news != null) {
                            presenter.successGetNews(news);
                        }else{
                            presenter.failedGetNews();
                        }
                    } else {
                        presenter.failedGetNews();
                    }
                } else {
                    presenter.failedGetNews();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                presenter.failedGetNews();
            }
        });
    }

    public void getRecommendation(int userId) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoListResponse> call = svc.mightLike(userId+"");
//        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    if(data !=null) {
                        presenter.successGetRecommendation(data);
                    }else{
                        String error = response.body().getError().getMessage();
                        if(error != null){
                            presenter.failedGetRecommendation();
                        }
                    }
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

    public void getBookmarks(SortFilterRequest request) {
        ApiService svc = retrofit.create(ApiService.class);
        request.setDefaultLoc("-8.687115,115.213868");
        Call<RestoListResponse> call = svc.getBookmarks(request);
//        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    if(data !=null) {
                        presenter.successGetBookmarks(data);
                    }else{
                        String error = response.body().getError().getMessage();
                        if(error != null){
                            presenter.failedGetBookmarks();
                        }
                    }
                } else {
                    presenter.failedGetBookmarks();
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedGetBookmarks();
            }
        });
    }

    public void getBeenHere(SortFilterRequest request) {
        ApiService svc = retrofit.create(ApiService.class);
        request.setDefaultLoc("-8.687115,115.213868");
        Call<RestoListResponse> call = svc.getBeenHere(request);
//        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<RestoListResponse>() {
            @Override
            public void onResponse(Call<RestoListResponse> call, Response<RestoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Resto> data = response.body().getData();
                    if(data !=null) {
                        presenter.successGetBeenHere(data);
                    }else{
                        String error = response.body().getError().getMessage();
                        if(error != null){
                            presenter.failedGetBeenHere();
                        }
                    }
                } else {
                    presenter.failedGetBeenHere();
                }
            }

            @Override
            public void onFailure(Call<RestoListResponse> call, Throwable t) {
                presenter.failedGetBeenHere();
            }
        });
    }
}
