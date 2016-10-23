package com.imajiku.vegefinder.model;

import com.imajiku.vegefinder.model.presenter.ForgotPresenter;
import com.imajiku.vegefinder.utility.Utility;

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

    //TODO: finish this
    public void forgotPassword(String email) {
//        ForgotRequest request = new ForgotRequest(email);
//        ApiService svc = retrofit.create(ApiService.class);
//        Call<ForgotResponse> call = svc.forgot(request);
//        call.enqueue(new Callback<ForgotResponse>() {
//            @Override
//            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
//                if (response.isSuccessful()) {
//                    presenter.successGetCity(response.body().getCityNames());
//                    ForgotView.successForgot(response.body().getSessionId());
//                } else {
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
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ForgotResponse> call, Throwable t) {
////                mIAccountView.showToast("Forgot failed. Please check your connection.");
////                mIAccountView.failedForgot();
//            }
//        });
    }
}
