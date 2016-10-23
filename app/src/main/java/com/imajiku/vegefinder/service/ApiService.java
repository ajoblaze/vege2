package com.imajiku.vegefinder.service;

import com.imajiku.vegefinder.model.request.CityRequest;
import com.imajiku.vegefinder.model.request.ContactUsRequest;
import com.imajiku.vegefinder.model.request.LoginRequest;
import com.imajiku.vegefinder.model.request.RegisterRequest;
import com.imajiku.vegefinder.model.response.CityResponse;
import com.imajiku.vegefinder.model.response.ContactUsResponse;
import com.imajiku.vegefinder.model.response.LoginResponse;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.model.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.GET;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface ApiService {

    //    REGIONAL
    @GET("place/province")
    Call<ProvinceResponse> getProvince();

    @POST("place/city")
    Call<CityResponse> getCity(@Body CityRequest request);

    //    MEMBER
    @POST("users/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("users/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    //    CONTACT
    @POST("contact/sendMessage")
    Call<ContactUsResponse> sendMessage(ContactUsRequest request);
//    Call<ProvinceResponse> getCity(@Query("session_id") String sessionId);
}
