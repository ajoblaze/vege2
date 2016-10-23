package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.ArticlesFragment;
import com.imajiku.vegefinder.fragment.PlacesFragment;
import com.imajiku.vegefinder.fragment.RecommendFragment;

public class MainActivity extends AppCompatActivity
        implements RecommendFragment.RecommendListener,
        PlacesFragment.PlacesListener,
        ArticlesFragment.ArticlesListener, View.OnClickListener{

    private String TAG = "exc";
    private boolean isLogin;
    private RecommendFragment recommendFragment;
    private PlacesFragment placesFragment;
    private ArticlesFragment articlesFragment;
    private Button browse;
    private String loginMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLogin = getIntent().getBooleanExtra("isLogin", false);
        if(isLogin){
            loginMethod = getIntent().getStringExtra("loginMethod");
        }
        recommendFragment = (RecommendFragment) getSupportFragmentManager().findFragmentById(R.id.recommend_fragment);
        placesFragment = (PlacesFragment) getSupportFragmentManager().findFragmentById(R.id.places_fragment);
        articlesFragment = (ArticlesFragment) getSupportFragmentManager().findFragmentById(R.id.articles_fragment);

        if(!isLogin){ // hide places
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(placesFragment);
            ft.commit();
        }

        browse = (Button) findViewById(R.id.browse_nearby);
        browse.setOnClickListener(this);
    }

    @Override
    public void onRecommend(Uri uri) {

    }

    @Override
    public void onPlaces(Uri uri) {

    }

    @Override
    public void onArticles(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browse_nearby:
                startActivity(new Intent(MainActivity.this, BrowseActivity.class));
                break;
        }
    }
}
