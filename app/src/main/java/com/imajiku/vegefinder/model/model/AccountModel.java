package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.AccountPresenter;
import com.imajiku.vegefinder.model.response.AccountResponse;
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
public class AccountModel {

    private AccountPresenter presenter;
    private Retrofit retrofit;

    public AccountModel(AccountPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void getProfile(int userId) {
        ApiService svc = retrofit.create(ApiService.class);
        Call<AccountResponse> call = svc.getProfile(userId);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getStatus() != null) {
                        if (response.body().getData().getStatus().toLowerCase().equals("success")) {
                            presenter.successGetProfile(response.body().getData().getProfile());
                        }else{
                            presenter.failedGetProfile();
                        }
                    }
                } else {
                    presenter.failedGetProfile();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                presenter.failedGetProfile();
            }
        });
    }

    public void logout() {
        ApiService svc = retrofit.create(ApiService.class);
        Call<StatusResponse> call = svc.logout();
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getStatus() != null) {
                        if (response.body().getData().getStatus().toLowerCase().equals("success")) {
                            presenter.successLogout();
                        }else{
                            presenter.failedLogout();
                        }
                    }else{
                        presenter.failedLogout();
                    }
                } else {
                    presenter.failedLogout();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                presenter.failedGetProfile();
            }
        });
    }
}
