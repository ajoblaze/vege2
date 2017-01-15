package com.imajiku.vegefinder.utility;

import android.content.Context;
import android.util.Log;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alvin on 2016-10-18.
 */
public class Utility {

    private static final String TAG = "exc";
    public static String BASE_URL = "http://dev.imajiku.com/vegefinder/";
    private static Retrofit retrofit;
    private static final int TIMEOUT = 120;

    public static Retrofit buildRetrofit(){
        if(retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utility.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static String changeImageUrl(String image){
        String falseUrl = "http://localhost/imajiku_vegefinder/";
        if (image.startsWith(falseUrl)) {
            return Utility.BASE_URL + image.substring(falseUrl.length());
        }
        return image;
    }

    public static String attachImageUrl(String image){
        if(image == null){
            return "";
        }
        String newImage = changeImageUrl(image);
        if (!newImage.startsWith(BASE_URL)) {
            return Utility.BASE_URL + newImage;
        }
        return newImage;
    }

    public static String attachImageUrlNews(String image){
        return attachImageUrl(image).replace("users", "news");
    }

    public static void sendEmail(Context context, String email, String subject, String message) {
        BackgroundMail.newBuilder(context)
                .withUsername("email")
                .withPassword("pass")
                .withMailto(email)
                .withSubject(subject)
                .withBody(message)
                .send();
    }

    public static String generateCode(){
        String num = "1234567890";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            builder.append(num.charAt(random.nextInt(num.length())));
        }
        return builder.toString();
    }
}
