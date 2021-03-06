package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.request.LoginRequest;
import com.imajiku.vegefinder.model.response.LoginResponse;
import com.imajiku.vegefinder.model.presenter.LoginPresenter;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class LoginModel {

    private LoginPresenter presenter;
    private Retrofit retrofit;

    public LoginModel(LoginPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void login(String email, final String password) {
        LoginRequest request = new LoginRequest(email, password);
        ApiService svc = retrofit.create(ApiService.class);
        Call<LoginResponse> call = svc.login(request);
        Log.e("exc", String.valueOf(call.request().url()));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse.LoginResponseBody data = response.body().getData();
                    if (data != null) {
                        if (data.getStatus().equals("0")) {
                            presenter.successLogin(data.getUserId(), password, data.getMessage());
                        }else{
                            presenter.failedLogin(data.getMessage());
                        }
                    } else {
                        presenter.failedLogin("Login failed");
                    }
                } else {
                    presenter.failedLogin("Login failed");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                presenter.failedLogin("Login failed");
            }
        });
    }
}
