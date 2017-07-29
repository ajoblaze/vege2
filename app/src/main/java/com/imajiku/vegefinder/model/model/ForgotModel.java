package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.ForgotPresenter;
import com.imajiku.vegefinder.model.request.ForgotRequest;
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
public class ForgotModel {

    private static final String TAG = "exc";
    private ForgotPresenter presenter;
    private Retrofit retrofit;

    public ForgotModel(ForgotPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void forgotPassword(String email) {
        ForgotRequest request = new ForgotRequest(email);
        ApiService svc = retrofit.create(ApiService.class);
        Call<StatusResponse> call = svc.forgot(request);
//        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().getStatus().equals("0")) {
                            presenter.successForgot(response.body().getData().getMessage());
                        }else{
                            presenter.failedForgot(response.body().getData().getMessage());
                        }
                    }else{
                        presenter.failedForgot("Forgot password failed");
                    }
                } else {
                    presenter.failedForgot("Forgot password failed");
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                presenter.failedForgot("Forgot password failed");
            }
        });
    }
}
