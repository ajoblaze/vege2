package com.imajiku.vegefinder.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.RegionPresenter;
import com.imajiku.vegefinder.model.response.CityResponse;
import com.imajiku.vegefinder.model.response.CountryResponse;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegionModel {

    private RegionPresenter presenter;
    private Retrofit retrofit;

    public RegionModel(RegionPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void getCountry() {
        ApiService svc = retrofit.create(ApiService.class);
        Call<CountryResponse> call = svc.getCountry();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getData().getStatus().equals("success")) {
                        presenter.successGetCountry(response.body());
                    }else{
                        presenter.failedGetCountry();
                    }
//                    RegionView.successRegion(response.body().getSessionId());
                } else {
//                    try {
//                        if(response.code()==500){
//                            mIAccountView.showToast("Internal server error. Please try again later.");
//                        }else {
//                            String error = response.errorBody().string();
//                            mIAccountView.showToast(getResponseErrorStatus(error));
//                        }
//                        mIAccountView.failedRegion();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
//                mIAccountView.showToast("Region failed. Please check your connection.");
//                mIAccountView.failedRegion();
            }
        });
    }

    public void getProvince(int countryId) {
        if(countryId == -1){
            presenter.failedGetProvince();
            return;
        }
        ApiService svc = retrofit.create(ApiService.class);
        Call<ProvinceResponse> call = svc.getProvince(countryId);
        call.enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(Call<ProvinceResponse> call, Response<ProvinceResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getData().getStatus().equals("success")) {
                        presenter.successGetProvince(response.body());
                    }else{
                        presenter.failedGetProvince();
                    }
//                    RegionView.successRegion(response.body().getSessionId());
                } else {
//                    try {
//                        if(response.code()==500){
//                            mIAccountView.showToast("Internal server error. Please try again later.");
//                        }else {
//                            String error = response.errorBody().string();
//                            mIAccountView.showToast(getResponseErrorStatus(error));
//                        }
//                        mIAccountView.failedRegion();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<ProvinceResponse> call, Throwable t) {
//                mIAccountView.showToast("Region failed. Please check your connection.");
//                mIAccountView.failedRegion();
            }
        });
    }

    public void getCity(int provinceId) {
        if(provinceId == -1){
            presenter.failedGetCity();
            return;
        }
        ApiService svc = retrofit.create(ApiService.class);
        Call<CityResponse> call = svc.getCity(provinceId);
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getData().getStatus().equals("success")) {
                        presenter.successGetCity(response.body());
                    }else{
                        presenter.failedGetCity();
                    }
//                    RegionView.successRegion(response.body().getSessionId());
                }
//                    try {
//                        if(response.code()==500){
//                            mIAccountView.showToast("Internal server error. Please try again later.");
//                        }else {
//                            String error = response.errorBody().string();
//                            mIAccountView.showToast(getResponseErrorStatus(error));
//                        }
//                        mIAccountView.failedRegion();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
//                mIAccountView.showToast("Region failed. Please check your connection.");
//                mIAccountView.failedRegion();
            }
        });
    }
}
