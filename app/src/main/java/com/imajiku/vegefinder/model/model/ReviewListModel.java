package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.ReviewListPresenter;
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
public class ReviewListModel {

    private ReviewListPresenter presenter;
    private Retrofit retrofit;
    public String TAG = "exc";

    public ReviewListModel(ReviewListPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void getRestoDetail(int placeId, int userId) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RestoDetailResponse> call = svc.getDetailPlace(
                String.valueOf(placeId),
                userId == -1? "-" : String.valueOf(userId)
        );
//        Log.e(TAG, String.valueOf(call.request().url()));
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
                presenter.failedGetRestoDetail();
            }
        });
    }
}
