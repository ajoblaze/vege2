package com.imajiku.vegefinder.service;

import com.imajiku.vegefinder.model.request.BookRequest;
import com.imajiku.vegefinder.model.request.CheckInRequest;
import com.imajiku.vegefinder.model.request.ContactUsRequest;
import com.imajiku.vegefinder.model.request.FeedbackRequest;
import com.imajiku.vegefinder.model.request.FindAllRequest;
import com.imajiku.vegefinder.model.request.FindKeywordRequest;
import com.imajiku.vegefinder.model.request.FindRegionRequest;
import com.imajiku.vegefinder.model.request.ForgotRequest;
import com.imajiku.vegefinder.model.request.LoginRequest;
import com.imajiku.vegefinder.model.request.EditProfileRequest;
import com.imajiku.vegefinder.model.request.PhotoRequest;
import com.imajiku.vegefinder.model.request.RegisterRequest;
import com.imajiku.vegefinder.model.request.ReportRequest;
import com.imajiku.vegefinder.model.request.ReviewRequest;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
import com.imajiku.vegefinder.model.request.ToggleRequest;
import com.imajiku.vegefinder.model.request.VerifyForgotRequest;
import com.imajiku.vegefinder.model.request.VerifyRequest;
import com.imajiku.vegefinder.model.response.AccountResponse;
import com.imajiku.vegefinder.model.response.CheckInResponse;
import com.imajiku.vegefinder.model.response.CityResponse;
import com.imajiku.vegefinder.model.response.StatusMessageResponse;
import com.imajiku.vegefinder.model.response.CountryResponse;
import com.imajiku.vegefinder.model.response.FeedbackResponse;
import com.imajiku.vegefinder.model.response.ReportResponse;
import com.imajiku.vegefinder.model.response.StatusResponse;
import com.imajiku.vegefinder.model.response.LoginResponse;
import com.imajiku.vegefinder.model.response.NewsResponse;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.model.response.RegisterResponse;
import com.imajiku.vegefinder.model.response.RestoDetailResponse;
import com.imajiku.vegefinder.model.response.RestoListResponse;
import com.imajiku.vegefinder.model.response.ReviewResponse;
import com.imajiku.vegefinder.model.response.ToggleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface ApiService {

    //    REGIONAL
    @GET("province/getCountry")
    Call<CountryResponse> getCountry();

    @GET("province/getProvince/{countryId}")
    Call<ProvinceResponse> getProvince(@Path ("countryId") int countryId);

    @GET("province/getCity/{provinceId}")
    Call<CityResponse> getCity(@Path ("provinceId") int provinceId);

    //    MEMBER
    @POST("users/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("users/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("users/updateProfileRegister")
    Call<StatusResponse> registerProfile(@Body EditProfileRequest request);

    @POST("users/activationCode")
    Call<StatusResponse> confirmCode(@Body VerifyRequest request);

    @POST("users/forgot")
    Call<StatusResponse> forgot(@Body ForgotRequest request);

    @POST("users/reset_password")
    Call<StatusResponse> resetPassword(@Body VerifyForgotRequest request);

    @POST("users/change_password")
    Call<StatusResponse> changePassword(@Body VerifyForgotRequest request);

    @POST("users/UpdateProfile")
    Call<StatusResponse> updateProfile(@Body EditProfileRequest request);

    @POST("users/UpdatePhotoProfile")
    Call<StatusResponse> updatePhotoProfile(@Body EditProfileRequest request);

    @POST("users/feedback")
    Call<FeedbackResponse> sendFeedback(@Body FeedbackRequest request);

    @GET("users/logout")
    Call<StatusResponse> logout();

    //    CONTACT
    @POST("contact/sendContactUs")
    Call<StatusMessageResponse> sendContactUs(@Body ContactUsRequest request);

    //    PLACES
    @GET("place/generate_place/{location}/{sort}/{filter}")
    Call<RestoListResponse> browseNearby(
            @Path ("location") String location,
            @Path ("sort") String sort,
            @Path ("filter") String filter
    );

    @GET("place/generate_place/{location}/{sort}")
    Call<RestoListResponse> browseNearby(
            @Path ("location") String location,
            @Path ("sort") String sort
    );

    @POST("place/checkin")
    Call<CheckInResponse> checkIn(@Body CheckInRequest request);

    @POST("place/search_region")
    Call<RestoListResponse> findRegion(@Body FindRegionRequest request);

    @POST("place/search_keyword")
    Call<RestoListResponse> findKeyword(@Body FindKeywordRequest request);

    @POST("place/search_all")
    Call<RestoListResponse> findAll(@Body FindAllRequest request);

    @POST("place/bookmark")
    Call<ToggleResponse> addBookmark(@Body ToggleRequest request);

    @POST("place/remove_bookmark")
    Call<ToggleResponse> removeBookmark(@Body ToggleRequest request);

    @POST("place/bookmark_user")
    Call<RestoListResponse> getBookmarks(@Body SortFilterRequest request);

    @POST("place/beenhere")
    Call<ToggleResponse> addBeenHere(@Body ToggleRequest request);

    @POST("place/remove_beenhere")
    Call<ToggleResponse> removeBeenHere(@Body ToggleRequest request);

    @POST("place/book")
    Call<StatusResponse> book(@Body BookRequest request);

    @POST("place/report")
    Call<ReportResponse> sendReport(@Body ReportRequest request);

    @POST("place/addPhoto")
    Call<StatusMessageResponse> addPhoto(@Body PhotoRequest request);

    @POST("place/beenhere_user")
    Call<RestoListResponse> getBeenHere(@Body SortFilterRequest request);

    @POST("place/submit_review")
    Call<ReviewResponse> sendReview(@Body ReviewRequest request);

    @GET("place/get_detail_place/{placeId}/{userId}")
    Call<RestoDetailResponse> getDetailPlace(
            @Path ("placeId") int placeId,
            @Path ("userId") int userId
    );

    //    NEWS
    @GET("news/generate_news")
    Call<NewsResponse> generateNews();

    //    MY ACCOUNT
    @POST("users/profile/{userId}")
    Call<AccountResponse> getProfile(@Path ("userId") int userId);
}
