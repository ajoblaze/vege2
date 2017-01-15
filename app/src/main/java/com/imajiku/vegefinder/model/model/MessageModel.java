package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.MessagePresenter;
import com.imajiku.vegefinder.model.request.ContactUsRequest;
import com.imajiku.vegefinder.model.request.FeedbackRequest;
import com.imajiku.vegefinder.model.request.ReportRequest;
import com.imajiku.vegefinder.model.request.ReviewRequest;
import com.imajiku.vegefinder.model.response.StatusMessageResponse;
import com.imajiku.vegefinder.model.response.FeedbackResponse;
import com.imajiku.vegefinder.model.response.ReportResponse;
import com.imajiku.vegefinder.model.response.ReviewResponse;
import com.imajiku.vegefinder.service.ApiService;
import com.imajiku.vegefinder.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alvin on 2016-10-08.
 */
public class MessageModel {

    private static final String TAG = "exc";
    private MessagePresenter presenter;
    private Retrofit retrofit;

    public MessageModel(MessagePresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void sendContactUs(String name, String email, String phone, String subject, String message) {
        ContactUsRequest request = new ContactUsRequest(name, email, phone, subject, message);
        ApiService svc = retrofit.create(ApiService.class);
        Call<StatusMessageResponse> call = svc.sendContactUs(request);
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<StatusMessageResponse>() {
            @Override
            public void onResponse(Call<StatusMessageResponse> call, Response<StatusMessageResponse> response) {
                if (response.isSuccessful()) {
                    presenter.successSendContactUs(response.body().getData().getMessage());
                } else {
                    presenter.failedSendContactUs("Failed sending contact us");
                }
            }

            @Override
            public void onFailure(Call<StatusMessageResponse> call, Throwable t) {
                presenter.failedSendContactUs("Failed sending contact us");
            }
        });
    }

    public void sendReview(int userId, int restoId, String rate, String title, String comment) {
        Log.e("exc", "userId "+userId+" restoId"+restoId+" rate"+rate+" title"+title+" comment"+comment);
        ReviewRequest request = new ReviewRequest(userId, restoId, rate, title, comment);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ReviewResponse> call = svc.sendReview(request);
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    ReviewResponse.ReviewResponseBody data = response.body().getData();
                    if(data.getStatus().equals("1")) {
                        presenter.successSendReview(data.getMessage());
                    }else{
                        presenter.failedSendReview("Failed adding review");
                    }
                } else {
                    presenter.failedSendReview("Failed adding review");
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                presenter.failedSendReview("Failed adding review");
            }
        });
    }

    public void sendFeedback(int userId, String subject, String comment) {
        FeedbackRequest request = new FeedbackRequest(userId, subject, comment);
        ApiService svc = retrofit.create(ApiService.class);
        Call<FeedbackResponse> call = svc.sendFeedback(request);
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                if (response.isSuccessful()) {
                    FeedbackResponse.FeedbackResponseBody data = response.body().getData();
                    if (data.getStatus().equals("1")) {
                        presenter.successSendReport(data.getMessage());
                    }else{
                        presenter.failedSendReport("Failed sending feedback");
                    }
                } else {
                    presenter.failedSendReport("Failed sending feedback");
                }
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                presenter.failedSendReport("Failed sending feedback");
            }
        });
    }

    public void sendReport(int userId, int restoId, String subject, String comment) {
        ReportRequest request = new ReportRequest(userId, restoId, subject, comment);
        ApiService svc = retrofit.create(ApiService.class);
        Call<ReportResponse> call = svc.sendReport(request);
        Log.e(TAG, String.valueOf(call.request().url()));
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    ReportResponse.ReportResponseBody data = response.body().getData();
                    if(data.getStatus().equals("1")) {
                        presenter.successSendReport(data.getMessage());
                    }else{
                        presenter.failedSendReport("Failed sending report");
                    }
                } else {
                    presenter.failedSendReport("Failed sending report");
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                presenter.failedSendReport("Failed sending report");
            }
        });
    }
}
