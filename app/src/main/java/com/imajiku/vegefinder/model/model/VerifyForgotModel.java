package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.VerifyForgotPresenter;
import com.imajiku.vegefinder.model.request.VerifyForgotRequest;
import com.imajiku.vegefinder.model.response.StatusResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyForgotModel {

    private static final String TAG = "exc";
    private VerifyForgotPresenter presenter;
    private Retrofit retrofit;

    public VerifyForgotModel(VerifyForgotPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void resetPassword(String code, String password) {
        VerifyForgotRequest request = new VerifyForgotRequest(code, password);
        ApiService svc = retrofit.create(ApiService.class);
        Call<StatusResponse> call = svc.resetPassword(request);
//        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getStatus() != null) {
                        if (response.body().getData().getStatus().toLowerCase().equals("success")) {
                            presenter.successVerifyCode();
                        } else {
                            presenter.failedVerifyCode();
                        }
                    } else {
                        presenter.failedVerifyCode();
                    }
                } else {
                    presenter.failedVerifyCode();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                presenter.failedVerifyCode();
            }
        });
    }
}
