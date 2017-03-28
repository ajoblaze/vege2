package com.imajiku.vegefinder.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
        NewsFragment.NewsListener, View.OnClickListener, MainView {

    private static final int NEWS_MAX_QTY = 4;
    private static final int PREVIEW_MAX_QTY = 5;
    private static final int PERMISSION_UPPER = 900;
    private static final int PERMISSION_NETWORK_REQUEST_CODE = 901;
    private static final int PERMISSION_LOWER = 902;
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
    private boolean hasRecommendation;
    private LinearLayout recommendLayout;
    private int userId;
    private ProgressBar progressBar;
    private int apiCallCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        presenter = new MainPresenter(this);
        MainModel model = new MainModel(presenter);
        presenter.setModel(model);

        isLogin = getIntent().getBooleanExtra("isLogin", false);
        if(isLogin){
            loginMethod = getIntent().getStringExtra("loginMethod");
            userId = CurrentUser.getId(this);
            presenter.getBookmarks(new SortFilterRequest(userId));
            addApiCounter(true);
//            presenter.getBeenHere(userId, "asc");
        }else{
            CurrentUser.setId(this, -1);
        }
        presenter.getRecommendation();
        addApiCounter(true);
        presenter.getNews();
        addApiCounter(true);

        recommendLayout = (LinearLayout) findViewById(R.id.recommend_layout);

        hasRecommendation = false; // has called getRecommendation or not

        recommendFragment = (RecommendFragment) getSupportFragmentManager().findFragmentById(R.id.recommend_fragment);
        placesFragment = (PlacesFragment) getSupportFragmentManager().findFragmentById(R.id.places_fragment);
        newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.articles_fragment);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");

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

    public void addApiCounter(boolean isStart){
        if(isStart){
            apiCallCounter++;
        }else{
            if(apiCallCounter > 0) {
                apiCallCounter--;
            }
        }
        if(apiCallCounter == 1){
            progressBar.setVisibility(View.VISIBLE);
        }else if(apiCallCounter == 0){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if(isLogin) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Exit")
                    .setMessage("Do you really want to exit application?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            superBackPressed();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }else{
            super.onBackPressed();
        }
    }

    private void superBackPressed(){
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onRecommend(int id) {
        Intent i = new Intent(MainActivity.this, RestoDetailActivity.class);
        i.putExtra("restoId", id);
        startActivity(i);
    }

    @Override
    public void onPlaces(int id) {
        Intent i = new Intent(MainActivity.this, RestoDetailActivity.class);
        i.putExtra("restoId", id);
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
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET
        };
        switch (v.getId()){
            case R.id.find_specific:
                i = new Intent(MainActivity.this, FindPlaceActivity.class);
                startActivity(i);
                break;
            case R.id.browse_nearby:
                if(isLocationEnabled(this)) {
                    if(hasPermissions(permissions)) {
                        browseNearbyPlaces();
                    }else{
                        requestPerms(permissions);
                    }
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

    private void browseNearbyPlaces(){
        Intent i = new Intent(MainActivity.this, RestoListActivity.class);
        i.putExtra("page", RestoListActivity.PAGE_BROWSE);
        startActivity(i);
    }

    // CHECK PERMISSION FOR MARSHMALLOW
    private boolean hasPermissions(String[] permissions) {
        int res;
        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    private void requestPerms(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_NETWORK_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isAllowed = false;
        if (requestCode > PERMISSION_UPPER && requestCode < PERMISSION_LOWER) {
            for (int res : grantResults) {
                isAllowed = (res == PackageManager.PERMISSION_GRANTED);
                if (isAllowed) {
                    break;
                }
            }
        }
        if (isAllowed) {
            switch (requestCode) {
                case PERMISSION_NETWORK_REQUEST_CODE:
                    browseNearbyPlaces();
                    break;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Toast.makeText(MainActivity.this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
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
        addApiCounter(false);
        newsList = news;
        ArrayList<RestoPreview> newsPreviewList = new ArrayList<>();
        int size = min(news.size(), NEWS_MAX_QTY);
        for(int i=0;i<size;i++){
            newsPreviewList.add(new RestoPreview(news.get(i).getId(), news.get(i).getImageUrl(), news.get(i).getTitle()));
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
        addApiCounter(false);
        Toast.makeText(MainActivity.this, "Failed getting news data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successGetRecommendation(ArrayList<Resto> list) {
        hasRecommendation = true;
        recommendList = list;
        ArrayList<RestoPreview> recommendPreviewList = new ArrayList<>();
        int size = min(list.size(), PREVIEW_MAX_QTY);
        for(int i=0;i<size;i++){
            Resto r = list.get(i);
            recommendPreviewList.add(new RestoPreview(r.getPlaceId(), r.getImageUrl(), r.getMetaTitle(), r.getCity()));
        }
        recommendFragment.setData(recommendPreviewList);
        addApiCounter(false);
    }

    @Override
    public void failedGetRecommendation() {
        Toast.makeText(MainActivity.this, "Failed getting recommendation", Toast.LENGTH_SHORT).show();
        addApiCounter(false);
    }

    @Override
    public void successGetBookmarks(ArrayList<Resto> list) {
        placesList = list;
        ArrayList<RestoPreview> placesPreviewList = new ArrayList<>();
        int size = min(list.size(), PREVIEW_MAX_QTY);
        for(int i=0;i<size;i++){
            Resto r = list.get(i);
            placesPreviewList.add(new RestoPreview(r.getPlaceId(), r.getImageUrl(), r.getMetaTitle(), r.getCity()));
        }
        placesFragment.setData(placesPreviewList);
        addApiCounter(false);
    }

    @Override
    public void failedGetBookmarks() {
        Toast.makeText(MainActivity.this, "Failed getting bookmark data", Toast.LENGTH_SHORT).show();
        addApiCounter(false);
    }

    @Override
    public void successGetBeenHere(ArrayList<Resto> data) {
        addApiCounter(false);
    }

    @Override
    public void failedGetBeenHere() {
        Toast.makeText(MainActivity.this, "Failed getting been here data", Toast.LENGTH_SHORT).show();
        addApiCounter(false);
    }
}
