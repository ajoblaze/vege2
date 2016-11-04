package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.NewsFragment;
import com.imajiku.vegefinder.fragment.PlacesFragment;
import com.imajiku.vegefinder.fragment.RecommendFragment;
import com.imajiku.vegefinder.model.model.MainModel;
import com.imajiku.vegefinder.model.presenter.MainPresenter;
import com.imajiku.vegefinder.model.view.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements RecommendFragment.RecommendListener,
        PlacesFragment.PlacesListener,
        NewsFragment.NewsListener, View.OnClickListener, MainView {

    private String TAG = "exc";
    private boolean isLogin;
    private RecommendFragment recommendFragment;
    private PlacesFragment placesFragment;
    private NewsFragment newsFragment;
    private Button find, browse, myAccount;
    private String loginMethod;
    private MainPresenter presenter;
    private ArrayList<String> recommendList, placesList, newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        presenter = new MainPresenter(this);
        MainModel model = new MainModel(presenter);
        presenter.setModel(model);

        presenter.getNews();

        isLogin = getIntent().getBooleanExtra("isLogin", false);
        if(isLogin){
            loginMethod = getIntent().getStringExtra("loginMethod");
        }
        recommendFragment = (RecommendFragment) getSupportFragmentManager().findFragmentById(R.id.recommend_fragment);
        placesFragment = (PlacesFragment) getSupportFragmentManager().findFragmentById(R.id.places_fragment);
        newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.articles_fragment);

        find = (Button) findViewById(R.id.find_specific);
        browse = (Button) findViewById(R.id.browse_nearby);
        myAccount = (Button) findViewById(R.id.my_account);
        find.setOnClickListener(this);
        browse.setOnClickListener(this);
        myAccount.setOnClickListener(this);

        if(!isLogin){ // hide places & account
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(placesFragment);
            ft.commit();
            myAccount.setVisibility(View.GONE);
        }
    }

    public void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
        }
        ImageView iv = (ImageView) mToolbar.findViewById(R.id.toolbar_image);
    }

    @Override
    public void onRecommend(Uri uri) {

    }

    @Override
    public void onPlaces(Uri uri) {

    }

    @Override
    public void onNews(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.find_specific:
                startActivity(new Intent(MainActivity.this, FindPlaceActivity.class));
                break;
            case R.id.browse_nearby:
                startActivity(new Intent(MainActivity.this, BrowseActivity.class));
                break;
        }
    }

    @Override
    public void successGetNews() {

    }

    @Override
    public void failedGetNews() {

    }

    class LoadMainTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            presenter.getRecommendation();
            presenter.getPlaces();
            presenter.getNews();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recommendFragment.setData(recommendList);
            placesFragment.setData(placesList);
            newsFragment.setData(newsList);
        }
    }
}
