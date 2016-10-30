package com.imajiku.vegefinder.model.model;

import com.imajiku.vegefinder.model.presenter.ContactUsPresenter;
import com.imajiku.vegefinder.model.request.ContactUsRequest;
import com.imajiku.vegefinder.model.response.ContactUsResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ContactUsModel {

    private ContactUsPresenter presenter;
    private Retrofit retrofit;

    public ContactUsModel(ContactUsPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void sendContactUs(String name, String email, String phone, String subject, String message) {
        ContactUsRequest request = new ContactUsRequest(name, email, phone, subject, message);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ContactUsResponse> call = svc.sendMessage(request);
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                if (response.isSuccessful()) {
                    presenter.successSendMessage(response.body().getData().getMessage());
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
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
//                mIAccountView.showToast("RegisterProfile failed. Please check your connection.");
//                mIAccountView.failedRegisterProfile();
            }
        });
    }
}
