package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.VerifyForgotPresenter;
import com.imajiku.vegefinder.model.request.VerifyForgotRequest;
import com.imajiku.vegefinder.model.response.VerifyForgotResponse;
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

    private VerifyForgotPresenter presenter;
    private Retrofit retrofit;

    public VerifyForgotModel(VerifyForgotPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void resetPassword(String code, String password) {
        VerifyForgotRequest request = new VerifyForgotRequest(code, password);
        ApiService svc = retrofit.create(ApiService.class);
        Call<VerifyForgotResponse> call = svc.resetPassword(request);
        call.enqueue(new Callback<VerifyForgotResponse>() {
            @Override
            public void onResponse(Call<VerifyForgotResponse> call, Response<VerifyForgotResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getStatus() != null) {
                        if (response.body().getData().getStatus().toLowerCase().equals("success")) {
                            presenter.successVerifyCode();
                        }
                    } else {
                        presenter.failedVerifyCode();
                    }
                } else {
                    presenter.failedVerifyCode();
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
            public void onFailure(Call<VerifyForgotResponse> call, Throwable t) {
//                mIAccountView.showToast("Forgot failed. Please check your connection.");
//                mIAccountView.failedForgot();
            }
        });
    }
}
