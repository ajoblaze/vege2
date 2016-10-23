package com.imajiku.vegefinder.model;

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

    public void login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        ApiService svc = retrofit.create(ApiService.class);
        Call<LoginResponse> call = svc.login(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    presenter.successLogin();
//                    LoginView.successLogin(response.body().getSessionId());
                } else {
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                mIAccountView.showToast("Login failed. Please check your connection.");
//                mIAccountView.failedLogin();
            }
        });
    }
}
