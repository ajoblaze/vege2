package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.RegisterProfilePresenter;
import com.imajiku.vegefinder.model.request.RegisterProfileRequest;
import com.imajiku.vegefinder.model.response.RegisterProfileResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterProfileModel {

    private RegisterProfilePresenter presenter;
    private Retrofit retrofit;

    public RegisterProfileModel(RegisterProfilePresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void registerProfile(RegisterProfileRequest request) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RegisterProfileResponse> call = svc.registerProfile(request);
        call.enqueue(new Callback<RegisterProfileResponse>() {
            @Override
            public void onResponse(Call<RegisterProfileResponse> call, Response<RegisterProfileResponse> response) {
                if (response.isSuccessful()) {
                    RegisterProfileResponse.RegisterProfileResponseBody data = response.body().getData();
                    if(data.getStatus().equals("success")) {
                        presenter.successRegisterProfile();
                    }else{
                        presenter.failedRegisterProfile();
                    }
//                    RegisterProfileView.successRegisterProfile(response.body().getSessionId());
                } else {
                    presenter.failedRegisterProfile();
//                    try {
//                        if(response.code()==500){
//                            mIAccountView.showToast("Internal server error. Please try again later.");
//                        }else {
//                            String error = response.errorBody().string();
//                            mIAccountView.showToast(getResponseErrorStatus(error));
//                        }
//                        mIAccountView.failedRegisterProfile();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterProfileResponse> call, Throwable t) {
                presenter.failedRegisterProfile();
//                mIAccountView.showToast("RegisterProfile failed. Please check your connection.");
//                mIAccountView.failedRegisterProfile();

            }
        });
    }
}
