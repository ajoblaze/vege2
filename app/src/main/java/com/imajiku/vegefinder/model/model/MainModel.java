package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.MainPresenter;
import com.imajiku.vegefinder.model.response.NewsResponse;
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
                        ArrayList<NewsResponse.News> news = response.body().getData();
                        presenter.successGetNews();
                    } else {
                        presenter.failedGetNews();
                    }
                } else {
//                    try {
//                        if(response.code()==500){
//                            mIAccountView.showToast("Internal server error. Please try again later.");
//                        }else {
//                            String error = response.errorBody().string();
//                            mIAccountView.showToast(getResponseErrorStatus(error));
//                        }
//                        mIAccountView.failedMain();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                mIAccountView.showToast("Main failed. Please check your connection.");
//                mIAccountView.failedMain();
            }
        });
    }

    public void getRecommendation() {

    }

    public void getPlaces() {

    }
}
