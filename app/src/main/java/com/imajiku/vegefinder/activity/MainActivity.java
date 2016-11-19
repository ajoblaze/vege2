package com.imajiku.vegefinder.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.NewsFragment;
import com.imajiku.vegefinder.fragment.PlacesFragment;
import com.imajiku.vegefinder.fragment.RecommendFragment;
import com.imajiku.vegefinder.model.model.MainModel;
import com.imajiku.vegefinder.model.presenter.MainPresenter;
import com.imajiku.vegefinder.model.view.MainView;
import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.pojo.Resto;
import com.imajiku.vegefinder.pojo.RestoPreview;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements RecommendFragment.RecommendListener,
        PlacesFragment.PlacesListener,
        NewsFragment.NewsListener, View.OnClickListener, MainView {

    private static final int NEWS_MAX_QTY = 4;
    private static final int PREVIEW_MAX_QTY = 5;
    private String TAG = "exc";
    private boolean isLogin;
    private RecommendFragment recommendFragment;
    private PlacesFragment placesFragment;
    private NewsFragment newsFragment;
    private Button find, browse, myAccount;
    private String loginMethod;
    private MainPresenter presenter;
    private ArrayList<Resto> recommendList;
    private ArrayList<Resto> placesList;
    private ArrayList<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        presenter = new MainPresenter(this);
        MainModel model = new MainModel(presenter);
        presenter.setModel(model);


        isLogin = getIntent().getBooleanExtra("isLogin", false);
        isLogin = true; // TODO: remove this
        if(isLogin){
            loginMethod = getIntent().getStringExtra("loginMethod");
            presenter.getPlaces();
        }
        presenter.getRecommendation();
        presenter.getNews();
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
    public void onRecommend(int id) {
        Intent i = new Intent(MainActivity.this, RestoDetailActivity.class);
        i.putExtra("resto", id);
        startActivity(i);
    }

    @Override
    public void onPlaces(int id) {
        Intent i = new Intent(MainActivity.this, RestoDetailActivity.class);
        i.putExtra("resto", id);
        startActivity(i);
    }

    @Override
    public void onNews(int id) {
        Intent i = new Intent(MainActivity.this, NewsDetailActivity.class);
        i.putExtra("news", findNewsById(newsList, id));
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.find_specific:
                startActivity(new Intent(MainActivity.this, FindPlaceActivity.class));
                break;
            case R.id.browse_nearby: {
                if(isLocationEnabled(this)) {
                    Intent i = new Intent(MainActivity.this, RestoListActivity.class);
                    i.putExtra("page", RestoListActivity.PAGE_BROWSE);
                    startActivity(i);
                }else{
                    //show dialog
                    Toast.makeText(MainActivity.this, "Please turn on location to use this", Toast.LENGTH_SHORT).show();
                }
            }
                break;
        }
    }

    /**
     * Gets the state of Airplane Mode.
     * from http://stackoverflow.com/questions/4319212/how-can-one-detect-airplane-mode-on-android
     * Answer by Alex Volovoy
     *
     * @param context
     * @return true if enabled.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

    }

    /**
     * from http://stackoverflow.com/questions/10311834/how-to-check-if-location-services-are-enabled
     * Answer by Slava Fir
     */
    public static boolean isLocationEnabled(Context context) {
        if(isAirplaneModeOn(context)){
            return false;
        }

        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private Resto findRestoById(ArrayList<Resto> list, int id) {
        for(Resto r : list){
            if(r.getId() == id){
                return r;
            }
        }
        return null;
    }

    private News findNewsById(ArrayList<News> list, int id) {
        for(News n : list){
            if(n.getId() == id){
                return n;
            }
        }
        return null;
    }

    @Override
    public void successGetNews(ArrayList<News> news) {
        newsList = news;
        ArrayList<RestoPreview> newsPreviewList = new ArrayList<>();
        int size = min(news.size(), NEWS_MAX_QTY);
        for(int i=0;i<size;i++){
            newsPreviewList.add(new RestoPreview(news.get(i).getId(), news.get(i).getImage(), news.get(i).getTitle()));
        }
        newsFragment.setData(newsPreviewList);
    }

    private int min(int a, int b) {
        if(a < b){
            return a;
        }
        return b;
    }

    @Override
    public void failedGetNews() {
        Log.e(TAG, "failedGetNews");
    }

    @Override
    public void successGetRecommendation(ArrayList<Resto> list) {
        recommendList = list;
        ArrayList<RestoPreview> recommendPreviewList = new ArrayList<>();
        int size = min(list.size(), PREVIEW_MAX_QTY);
        for(int i=0;i<size;i++){
            Resto r = list.get(i);
            recommendPreviewList.add(new RestoPreview(r.getId(), r.getImage(), r.getTitle(), r.getCityId()+""));
        }
        recommendFragment.setData(recommendPreviewList);
    }

    @Override
    public void failedGetRecommendation() {
        Log.e(TAG, "failedGetRecommendation");
    }

    @Override
    public void successGetPlaces(ArrayList<Resto> list) {
        placesList = list;
        ArrayList<RestoPreview> placesPreviewList = new ArrayList<>();
        int size = min(list.size(), PREVIEW_MAX_QTY);
        for(int i=0;i<size;i++){
            Resto r = list.get(i);
            placesPreviewList.add(new RestoPreview(r.getId(), r.getImage(), r.getTitle(), r.getCityId()+""));
        }
        placesFragment.setData(placesPreviewList);
    }

    @Override
    public void failedGetPlaces() {
        Log.e(TAG, "failedGetPlaces");
    }
}
