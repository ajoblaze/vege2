package com.imajiku.vegefinder.model;

import com.imajiku.vegefinder.model.request.CityRequest;
import com.imajiku.vegefinder.model.request.RegisterRequest;
import com.imajiku.vegefinder.model.response.CityResponse;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.model.response.RegisterResponse;
import com.imajiku.vegefinder.model.presenter.RegisterProfilePresenter;
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

    public void getProvince() {
        ApiService svc = retrofit.create(ApiService.class);
        Call<ProvinceResponse> call = svc.getProvince();
        call.enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(Call<ProvinceResponse> call, Response<ProvinceResponse> response) {
                if (response.isSuccessful()) {
                    presenter.successGetProvince(response.body());
//                    RegisterProfileView.successRegisterProfile(response.body().getSessionId());
                } else {
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
            public void onFailure(Call<ProvinceResponse> call, Throwable t) {
//                mIAccountView.showToast("RegisterProfile failed. Please check your connection.");
//                mIAccountView.failedRegisterProfile();
            }
        });
    }

    public void getCity(int provinceId) {
        CityRequest request = new CityRequest(provinceId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<CityResponse> call = svc.getCity(request);
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()) {
                    presenter.successGetCity(response.body().getCities());
//                    RegisterProfileView.successRegisterProfile(response.body().getSessionId());
                } else {
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
            public void onFailure(Call<CityResponse> call, Throwable t) {
//                mIAccountView.showToast("RegisterProfile failed. Please check your connection.");
//                mIAccountView.failedRegisterProfile();
            }
        });
    }

    public void register(String username, String email, String password) {
        RegisterRequest request = new RegisterRequest(username, email, password);
        ApiService svc = retrofit.create(ApiService.class);
        Call<RegisterResponse> call = svc.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse.RegisterResponseBody data = response.body().getData();
                    presenter.successRegister(data.getCode(), data.getEmail());
//                    RegisterProfileView.successRegisterProfile(response.body().getSessionId());
                } else {
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
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                mIAccountView.showToast("RegisterProfile failed. Please check your connection.");
//                mIAccountView.failedRegisterProfile();

            }
        });
    }
}
