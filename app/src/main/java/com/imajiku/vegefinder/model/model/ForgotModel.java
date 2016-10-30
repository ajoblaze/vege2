package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.ForgotPresenter;
import com.imajiku.vegefinder.model.presenter.view.ForgotView;
import com.imajiku.vegefinder.model.request.ForgotRequest;
import com.imajiku.vegefinder.model.response.ForgotResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ForgotModel {

    private ForgotPresenter presenter;
    private Retrofit retrofit;

    public ForgotModel(ForgotPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void forgotPassword(String email) {
        ForgotRequest request = new ForgotRequest(email);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ForgotResponse> call = svc.forgot(request);
        call.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getStatus() != null) {
                        if (response.body().getData().getStatus().toLowerCase().equals("success")) {
                            presenter.successForgot();
                        }else{
                            presenter.failedForgot();
                        }
                    }
                } else {
//                    try {
//                        if(response.code()==500){
//                            mIAccountView.showToast("Internal server error. Please try again later.");
//                        }else {
//                            String error = response.errorBody().string();
//                            mIAccountView.showToast(getResponseErrorStatus(error));
//                        }
//                        mIAccountView.failedForgot();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
//                mIAccountView.showToast("Forgot failed. Please check your connection.");
//                mIAccountView.failedForgot();
            }
        });
    }
}
