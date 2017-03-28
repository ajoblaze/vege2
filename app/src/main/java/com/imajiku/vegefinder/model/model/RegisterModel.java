package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.RegisterPresenter;
import com.imajiku.vegefinder.model.request.RegisterRequest;
import com.imajiku.vegefinder.model.response.RegisterResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterModel {

    private RegisterPresenter presenter;
    private Retrofit retrofit;
    public String TAG = "exc";

    public RegisterModel(RegisterPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void register(String name, String email, String password) {
        ApiService svc = retrofit.create(ApiService.class);
        RegisterRequest request = new RegisterRequest(name, email, password);
        Call<RegisterResponse> call = svc.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse.RegisterResponseBody data = response.body().getData();
                    if(data.getStatus().equals("success")) {
                        presenter.successRegister(data.getId());
                    }else{
                        presenter.failedRegister();
                    }
                } else {
                    presenter.failedRegister();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                presenter.failedRegister();
            }
        });
    }
}
