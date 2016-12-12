package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.EditProfilePresenter;
import com.imajiku.vegefinder.model.request.EditProfileRequest;
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
public class EditProfileModel {

    private static final String TAG = "exc";
    private EditProfilePresenter presenter;
    private Retrofit retrofit;

    public EditProfileModel(EditProfilePresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void registerProfile(EditProfileRequest request) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RegisterProfileResponse> call = svc.registerProfile(request);
        Log.e(TAG, String.valueOf(call.request().url()));
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
                } else {
                    presenter.failedRegisterProfile();
                }
            }

            @Override
            public void onFailure(Call<RegisterProfileResponse> call, Throwable t) {
                presenter.failedRegisterProfile();
            }
        });
    }

    public void updateProfile(EditProfileRequest request) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RegisterProfileResponse> call = svc.updateProfile(request);
        call.enqueue(new Callback<RegisterProfileResponse>() {
            @Override
            public void onResponse(Call<RegisterProfileResponse> call, Response<RegisterProfileResponse> response) {
                if (response.isSuccessful()) {
                    RegisterProfileResponse.RegisterProfileResponseBody data = response.body().getData();
                    if(data.getStatus().equals("success")) {
                        presenter.successUpdateProfile();
                    }else{
                        presenter.failedUpdateProfile();
                    }
                } else {
                    presenter.failedUpdateProfile();
                }
            }

            @Override
            public void onFailure(Call<RegisterProfileResponse> call, Throwable t) {
                presenter.failedUpdateProfile();
            }
        });
    }

    public void updatePhotoProfile(EditProfileRequest request) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<RegisterProfileResponse> call = svc.updatePhotoProfile(request);
        call.enqueue(new Callback<RegisterProfileResponse>() {
            @Override
            public void onResponse(Call<RegisterProfileResponse> call, Response<RegisterProfileResponse> response) {
                if (response.isSuccessful()) {
                    RegisterProfileResponse.RegisterProfileResponseBody data = response.body().getData();
                    if(data.getStatus().equals("success")) {
                        presenter.successUpdatePhotoProfile();
                    }else{
                        presenter.failedUpdatePhotoProfile();
                    }
                } else {
                    presenter.failedUpdatePhotoProfile();
                }
            }

            @Override
            public void onFailure(Call<RegisterProfileResponse> call, Throwable t) {
                presenter.failedUpdatePhotoProfile();
            }
        });
    }
}
