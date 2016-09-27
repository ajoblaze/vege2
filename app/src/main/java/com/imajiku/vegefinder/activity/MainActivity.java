package com.imajiku.vegefinder.activity;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.ArticlesFragment;
import com.imajiku.vegefinder.fragment.PlacesFragment;
import com.imajiku.vegefinder.fragment.RecommendFragment;

public class MainActivity extends AppCompatActivity
        implements RecommendFragment.RecommendListener, PlacesFragment.PlacesListener
        , ArticlesFragment.ArticlesListener {

    private String TAG = "exc";
    private boolean isLogin;
    private RecommendFragment recommendFragment;
    private PlacesFragment placesFragment;
    private ArticlesFragment articlesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLogin = getIntent().getBooleanExtra("isLogin", false);
        recommendFragment = (RecommendFragment) getSupportFragmentManager().findFragmentById(R.id.recommend_fragment);
        placesFragment = (PlacesFragment) getSupportFragmentManager().findFragmentById(R.id.places_fragment);
        articlesFragment = (ArticlesFragment) getSupportFragmentManager().findFragmentById(R.id.articles_fragment);

        if(!isLogin){ // hide places
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(placesFragment);
            ft.commit();
        }
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
}
