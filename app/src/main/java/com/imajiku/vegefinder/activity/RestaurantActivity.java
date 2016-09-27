package com.imajiku.vegefinder.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.PhotoFragment;
import com.imajiku.vegefinder.fragment.ReviewFragment;

public class RestaurantActivity extends AppCompatActivity
        implements PhotoFragment.PhotoListener, ReviewFragment.ReviewListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
    }

    @Override
    public void onPhoto(Uri uri) {

    }

    @Override
    public void onReview(Uri uri) {

    }
}
