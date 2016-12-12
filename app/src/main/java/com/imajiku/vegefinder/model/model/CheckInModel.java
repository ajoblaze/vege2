package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.CheckInPresenter;
import com.imajiku.vegefinder.model.request.CheckInRequest;
import com.imajiku.vegefinder.model.response.CheckInResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class CheckInModel {

    private CheckInPresenter presenter;
    private Retrofit retrofit;

    public CheckInModel(CheckInPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void checkIn(int userId, int placeId, String comment) {
        CheckInRequest request = new CheckInRequest(userId, placeId, comment);
        ApiService svc = retrofit.create(ApiService.class);
        Call<CheckInResponse> call = svc.checkIn(request);
        Log.e("exc", String.valueOf(call.request().url()));
        call.enqueue(new Callback<CheckInResponse>() {
            @Override
            public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null && response.body().getData().getStatus() != null) {
                        if (!response.body().getData().getStatus().equals("failed")) {
                            if(!response.body().getData().getStatus().equals("2")) {
                                presenter.successCheckIn();
                            }else{
                                presenter.failedCheckIn(response.body().getData().getMessage());
                            }
                        }else{
                            presenter.failedCheckIn("Please check your connection");
                        }
                    } else {
                        presenter.failedCheckIn("Please check your connection");
                    }
                } else {
                    presenter.failedCheckIn("Please check your connection");
                }
            }

            @Override
            public void onFailure(Call<CheckInResponse> call, Throwable t) {
                presenter.failedCheckIn("Please check your connection");
            }
        });
    }
}
