package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.VerifyPresenter;
import com.imajiku.vegefinder.model.request.VerifyRequest;
import com.imajiku.vegefinder.model.response.VerifyResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class VerifyModel {

    private VerifyPresenter presenter;
    private Retrofit retrofit;

    public VerifyModel(VerifyPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void submitCode(String code) {
        VerifyRequest request = new VerifyRequest(code);
        ApiService svc = retrofit.create(ApiService.class);
        Call<VerifyResponse> call = svc.confirmCode(request);
        call.enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getStatus() != null) {
                        if (response.body().getData().getStatus().toLowerCase().equals("success")) {
                            presenter.successVerify();
                        }
                    } else {
                        presenter.failedVerify();
                    }
//                    LoginView.successLogin(response.body().getSessionId());
                } else {
                    presenter.failedVerify();
//                    try {
//                        if(response.code()==500){
//                            mIAccountView.showToast("Internal server error. Please try again later.");
//                        }else {
//                            String error = response.errorBody().string();
//                            mIAccountView.showToast(getResponseErrorStatus(error));
//                        }
//                        mIAccountView.failedLogin();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
//                mIAccountView.showToast("Login failed. Please check your connection.");
//                mIAccountView.failedLogin();
            }
        });
    }
}
