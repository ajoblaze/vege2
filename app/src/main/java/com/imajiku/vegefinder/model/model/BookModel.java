package com.imajiku.vegefinder.model.model;

import android.util.Log;

import com.imajiku.vegefinder.model.presenter.BookPresenter;
import com.imajiku.vegefinder.model.request.BookRequest;
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
public class BookModel {

    private BookPresenter presenter;
    private Retrofit retrofit;

    public BookModel(BookPresenter presenter) {
        this.presenter = presenter;
        retrofit = Utility.buildRetrofit();
    }

    public void book(int userId, int placeId, String date, String time, String comment) {
        BookRequest request = new BookRequest(userId, placeId, date, time, comment);
        ApiService svc = retrofit.create(ApiService.class);
        Call<StatusResponse> call = svc.book(request);
        Log.e("exc", String.valueOf(call.request().url()));
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        if (!response.body().getData().getStatus().toLowerCase().equals("failed")) {
                            presenter.successBook();
                        }
                    } else {
                        presenter.failedBook("Failed booking");
                    }
                } else {
                    presenter.failedBook("Failed booking");
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                presenter.failedBook("Failed booking");
            }
        });
    }
}
