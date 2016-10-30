package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.FindPlacePresenter;
import com.imajiku.vegefinder.model.request.FindPlaceRequest;
import com.imajiku.vegefinder.model.response.FindPlaceResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class FindPlaceModel {

    private FindPlacePresenter presenter;
    private Retrofit retrofit;

    public FindPlaceModel(FindPlacePresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void findPlace(int provinceId, int cityId) {
        FindPlaceRequest request = new FindPlaceRequest(provinceId, cityId);
        ApiService svc = retrofit.create(ApiService.class);
        Call<FindPlaceResponse> call = svc.findPlace(request);
        call.enqueue(new Callback<FindPlaceResponse>() {
            @Override
            public void onResponse(Call<FindPlaceResponse> call, Response<FindPlaceResponse> response) {
                if (response.isSuccessful()) {
                    FindPlaceResponse.FindPlaceResponseBody data = response.body().getData();
//                    presenter.successFindPlace(data.getCode(), data.getEmail());
//                    FindPlaceView.successFindPlace(response.body().getSessionId());
                } else {
//                    try {
//                        if(response.code()==500){
//                            mIAccountView.showToast("Internal server error. Please try again later.");
//                        }else {
//                            String error = response.errorBody().string();
//                            mIAccountView.showToast(getResponseErrorStatus(error));
//                        }
//                        mIAccountView.failedFindPlace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<FindPlaceResponse> call, Throwable t) {
//                mIAccountView.showToast("FindPlace failed. Please check your connection.");
//                mIAccountView.failedFindPlace();

            }
        });
    }
}
