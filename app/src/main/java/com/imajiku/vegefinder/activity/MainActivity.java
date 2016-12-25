package com.imajiku.vegefinder.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.NewsFragment;
import com.imajiku.vegefinder.fragment.PlacesFragment;
import com.imajiku.vegefinder.fragment.RecommendFragment;
import com.imajiku.vegefinder.model.model.MainModel;
import com.imajiku.vegefinder.model.presenter.MainPresenter;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
import com.imajiku.vegefinder.model.view.MainView;
import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.pojo.Resto;
import com.imajiku.vegefinder.pojo.RestoPreview;
import com.imajiku.vegefinder.utility.CurrentUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements RecommendFragment.RecommendListener,
        PlacesFragment.PlacesListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, NewsFragment.NewsListener, View.OnClickListener, MainView {

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
    private GoogleApiClient googleApiClient;
    private boolean isRequestingLocationUpdates;
    private boolean hasRecommendation;
    private Location mLastLocation;
    private String mLatitudeText, mLongitudeText;
    private LocationRequest mLocationRequest;
    private LinearLayout recommendLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        setContentView(R.layout.activity_main);
        initToolbar();
        presenter = new MainPresenter(this);
        MainModel model = new MainModel(presenter);
        presenter.setModel(model);

        isLogin = getIntent().getBooleanExtra("isLogin", false);
        Log.e(TAG, ""+isLogin);
        if(isLogin){
            loginMethod = getIntent().getStringExtra("loginMethod");
            int userId = CurrentUser.getId(this);
            presenter.getBookmarks(new SortFilterRequest(userId));
//            presenter.getSortFilterList(userId);
//            presenter.getBeenHere(userId, "asc");
        }else{
            CurrentUser.setId(this, -1);
        }
        presenter.getNews();

        recommendLayout = (LinearLayout) findViewById(R.id.recommend_layout);
        if(isLocationEnabled(this)){
            mLastLocation = null;
            recommendLayout.setVisibility(View.VISIBLE);
        }

        hasRecommendation = false; // has called getRecommendation or not

        recommendFragment = (RecommendFragment) getSupportFragmentManager().findFragmentById(R.id.recommend_fragment);
        placesFragment = (PlacesFragment) getSupportFragmentManager().findFragmentById(R.id.places_fragment);
        newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.articles_fragment);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        find = (Button) findViewById(R.id.find_specific);
        browse = (Button) findViewById(R.id.browse_nearby);
        myAccount = (Button) findViewById(R.id.my_account);
        find.setOnClickListener(this);
        browse.setOnClickListener(this);
        myAccount.setOnClickListener(this);

        //Tint Drawable
        Drawable userIcon = ContextCompat.getDrawable(this, R.drawable.ic_account_circle_black_24dp_m);
        userIcon = DrawableCompat.wrap(userIcon);
        int btnGreen = ContextCompat.getColor(this, R.color.accentGreenBtn);
        DrawableCompat.setTint(userIcon, btnGreen);
        myAccount.setCompoundDrawablesWithIntrinsicBounds(userIcon, null, null, null);

        find.setTypeface(tf);
        browse.setTypeface(tf);
        myAccount.setTypeface(tf);

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
    protected void onStart() {
        googleApiClient.connect();
        isRequestingLocationUpdates = true;
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient.isConnected() && !isRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        isRequestingLocationUpdates = false;
        stopLocationUpdates();
        super.onStop();
    }

    @Override
    public void onRecommend(int id) {
        Intent i = new Intent(MainActivity.this, RestoDetailActivity.class);
        i.putExtra("placeId", id);
        startActivity(i);
    }

    @Override
    public void onPlaces(int id) {
        Intent i = new Intent(MainActivity.this, RestoDetailActivity.class);
        i.putExtra("placeId", id);
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
        Intent i;
        switch (v.getId()){
            case R.id.find_specific:
                i = new Intent(MainActivity.this, FindPlaceActivity.class);
                startActivity(i);
                break;
            case R.id.browse_nearby:
                if(isLocationEnabled(this)) {
                    i = new Intent(MainActivity.this, RestoListActivity.class);
                    i.putExtra("page", RestoListActivity.PAGE_BROWSE);
                    startActivity(i);
                }else{
                    //show dialog
                    Toast.makeText(MainActivity.this, "Please turn on location to use this", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.my_account:
                i = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(i);
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
        hasRecommendation = true;
        recommendList = list;
        ArrayList<RestoPreview> recommendPreviewList = new ArrayList<>();
        int size = min(list.size(), PREVIEW_MAX_QTY);
        for(int i=0;i<size;i++){
            Resto r = list.get(i);
            recommendPreviewList.add(new RestoPreview(r.getId(), r.getImage(), r.getMetaTitle(), r.getCityId()+""));
        }
        recommendFragment.setData(recommendPreviewList);
        if(isLocationEnabled(this)){
            recommendLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void failedGetRecommendation() {
        Log.e(TAG, "failedGetRecommendation");
    }

    @Override
    public void successGetBookmarks(ArrayList<Resto> list) {
        placesList = list;
        ArrayList<RestoPreview> placesPreviewList = new ArrayList<>();
        int size = min(list.size(), PREVIEW_MAX_QTY);
        for(int i=0;i<size;i++){
            Resto r = list.get(i);
            placesPreviewList.add(new RestoPreview(r.getId(), r.getImage(), r.getMetaTitle(), r.getCityId()+""));
        }
        placesFragment.setData(placesPreviewList);
    }

    @Override
    public void failedGetBookmarks() {
        Log.e(TAG, "failedGetBookmarks");
    }

    @Override
    public void successGetBeenHere(ArrayList<Resto> data) {

    }

    @Override
    public void failedGetBeenHere() {

    }

    private void updateLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
            }
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
            }
            return;
        }
        if (mLocationRequest != null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createLocationRequest();
        updateLocation();
        if (mLastLocation != null) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
            presenter.getRecommendation(mLongitudeText, mLatitudeText);
        }

        if (isRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // can start listening to location changes
                }
                break;
        }
    }

    protected void stopLocationUpdates() {
        if (googleApiClient.isConnected() && mLocationRequest != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, (LocationListener) this);
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocation();
        if (mLastLocation != null && !hasRecommendation) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
            presenter.getRecommendation(mLongitudeText, mLatitudeText);
            hasRecommendation = true;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
