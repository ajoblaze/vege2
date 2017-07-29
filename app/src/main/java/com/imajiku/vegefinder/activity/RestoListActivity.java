package com.imajiku.vegefinder.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.RestoListFragment;
import com.imajiku.vegefinder.model.model.RestoListModel;
import com.imajiku.vegefinder.model.presenter.RestoListPresenter;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
import com.imajiku.vegefinder.model.view.RestoListView;
import com.imajiku.vegefinder.pojo.Resto;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.imajiku.vegefinder.utility.Utility;

import java.util.ArrayList;

public class RestoListActivity extends AppCompatActivity implements
        RestoListFragment.RestoListListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener, RestoListView {

    public static final int PAGE_BROWSE = 20;
    public static final int PAGE_RECOMMEND = 21;
    public static final int PAGE_BOOKMARK = 22;
    public static final int PAGE_BEENHERE = 23;
    public static final int PAGE_SEARCH_R = 24;
    public static final int PAGE_SEARCH_K = 25;
    private static final int REQUEST_CHECK_SETTINGS = 8008;
    private static final int FILTER_BOX_QTY = 7;
    private static final int SORT_BUTTON_QTY = 4;
    private RestoListFragment restoListFragment;
    private TextView filter;
    private TextView sort;
    private String TAG = "exc";
    private GoogleApiClient googleApiClient;
    private boolean isRequestingLocationUpdates;
    private LocationRequest mLocationRequest;
    private ExpandableRelativeLayout filterLayout, sortLayout;
    private TextView selectAll;
    private TextView clear;
    private TextView submitFilter;
    private TextView submitSort;
    private CheckBox[] filterBox;
    private RadioGroup orderGroup;
    private boolean[] sortSelected;
    private LinearLayout[] sortButtonLayout;
//    private String[] sortText = {"title", "distance", "date_post", "average_cost"};
    private String[] sortText = {"alphabet", "distance", "date", "price"};
    private int currSelectedSort = -1;
    private RestoListPresenter presenter;
    private int pageType, filterCode;
    private String mLatitudeText, mLongitudeText;
    private boolean isFilterToggled, isSortToggled;
    private ArrayList<Resto> restoList;
    private Location mLastLocation;
    private Typeface tf;
    private boolean hasBrowsedNearby;
    private LinearLayout filterLinearLayout, sortLinearLayout;
    private ImageView filterArrow, sortArrow;
    private int userId;
    private int apiCallCounter = 0;
    private ProgressBar progressBar;
    private SortFilterRequest currRequest;
    private String consumeType="";
    private Intent intent;

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
        setContentView(R.layout.activity_resto_list);

        presenter = new RestoListPresenter(this);
        RestoListModel model = new RestoListModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), Utility.regFont);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        intent = getIntent();
        userId = CurrentUser.getId(this);
        pageType = intent.getIntExtra("page", PAGE_BROWSE);

        currRequest = new SortFilterRequest(userId);
        switch (pageType) {
            case PAGE_BOOKMARK:
                initToolbar("Saved Places");
                hasBrowsedNearby = false;
                break;
            case PAGE_BEENHERE:
                initToolbar("Visited Places");
                hasBrowsedNearby = false;
                break;
            case PAGE_RECOMMEND:
                initToolbar("You Might Like");
                hasBrowsedNearby = false;
//                presenter.getRecommendation(new SortFilterRequest(userId));
                break;
            case PAGE_BROWSE:
                initToolbar("Nearby Places");
                hasBrowsedNearby = false;
                break;
            case PAGE_SEARCH_R: {
                initToolbar("Find a Specific Place");
                hasBrowsedNearby = false;
//                presenter.findByRegion(countryId, provinceId, cityId, userId);
                }
                break;
            case PAGE_SEARCH_K: {
                initToolbar("Find a Specific Place");
                hasBrowsedNearby = false;
//                presenter.findByKeyword(keyword, userId);
                }
                break;
        }

        restoListFragment = (RestoListFragment) getSupportFragmentManager().findFragmentById(R.id.resto_list_fragment);
        filterLinearLayout = (LinearLayout) findViewById(R.id.filter_layout);
        sortLinearLayout = (LinearLayout) findViewById(R.id.sort_layout);
        filter = (TextView) findViewById(R.id.filter_btn);
        sort = (TextView) findViewById(R.id.sort_btn);

        isFilterToggled = false;
        isSortToggled = false;

        filter.setTypeface(tf);
        sort.setTypeface(tf);

        filterLayout = (ExpandableRelativeLayout) findViewById(R.id.layout_filter);
        sortLayout = (ExpandableRelativeLayout) findViewById(R.id.layout_sort);
        filterLinearLayout.setOnClickListener(this);
        sortLinearLayout.setOnClickListener(this);
        filterBox = new CheckBox[FILTER_BOX_QTY];
        sortButtonLayout = new LinearLayout[SORT_BUTTON_QTY];
        sortSelected = new boolean[]{false, false, false, false};

        // filter
        selectAll = (TextView) findViewById(R.id.select_all);
        clear = (TextView) findViewById(R.id.clear);
        selectAll.setTypeface(tf);
        clear.setTypeface(tf);

        filterBox[0] = (CheckBox) findViewById(R.id.open_now);
        filterBox[1] = (CheckBox) findViewById(R.id.rate_8);
        filterBox[2] = (CheckBox) findViewById(R.id.bookmarked);
        filterBox[3] = (CheckBox) findViewById(R.id.been_here);
        filterBox[4] = (CheckBox) findViewById(R.id.vegan);
        filterBox[5] = (CheckBox) findViewById(R.id.vege);
        filterBox[6] = (CheckBox) findViewById(R.id.vegeready);
        submitFilter = (TextView) findViewById(R.id.submit_filter);
        if (pageType == PAGE_BROWSE || pageType == PAGE_SEARCH_R || pageType == PAGE_SEARCH_K) {
            for (int i = 0; i < 4; i++) {
                filterBox[i].setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < FILTER_BOX_QTY; i++) {
            filterBox[i].setTypeface(tf);
            CompoundButtonCompat.setButtonTintList(filterBox[i], setColorStateList(
                    ContextCompat.getColor(this, R.color.accentGreenBtnDark)
            ));
        }
        submitFilter.setTypeface(tf);

        //sort
        sortButtonLayout[0] = (LinearLayout) findViewById(R.id.sort_alpha_ll);
        sortButtonLayout[1] = (LinearLayout) findViewById(R.id.sort_distance_ll);
        sortButtonLayout[2] = (LinearLayout) findViewById(R.id.sort_date_ll);
        sortButtonLayout[3] = (LinearLayout) findViewById(R.id.sort_price_ll);
        orderGroup = (RadioGroup) findViewById(R.id.sort_order);
        submitSort = (TextView) findViewById(R.id.submit_sort);
        for(int i=0;i<orderGroup.getChildCount();i++){
            RadioButton radio = (RadioButton) orderGroup.getChildAt(i);
            radio.setTypeface(tf);
            CompoundButtonCompat.setButtonTintList(radio, setColorStateList(
                    ContextCompat.getColor(this, R.color.accentGreenBtnDark)
            ));
        }
        //if Browse Nearby, hide sort function
        if(pageType == PAGE_BROWSE){
            sortLinearLayout.setVisibility(View.GONE);
            sortLayout.setVisibility(View.GONE);
        }

        // arrow
        filterArrow = (ImageView) findViewById(R.id.filter_arrow);
        sortArrow = (ImageView) findViewById(R.id.sort_arrow);

        // filter
        selectAll.setOnClickListener(this);
        clear.setOnClickListener(this);
        submitFilter.setOnClickListener(this);

        //sort
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            sortButtonLayout[i].setOnClickListener(this);
            ((TextView) sortButtonLayout[i].getChildAt(1)).setTypeface(tf);
        }
        submitSort.setOnClickListener(this);
        submitSort.setTypeface(tf);
    }

    private void loadPageData(){
        currRequest.setLoc(mLongitudeText + "," + mLatitudeText);
        switch(pageType) {
            case PAGE_BOOKMARK:
                addApiCounter(true);
                presenter.getSortFilterList(RestoListModel.BOOKMARK, currRequest);
                consumeType = RestoListModel.BOOKMARK;
                break;
            case PAGE_BEENHERE:
                addApiCounter(true);
                presenter.getSortFilterList(RestoListModel.BEENHERE, currRequest);
                consumeType = RestoListModel.BEENHERE;
                break;
            case PAGE_BROWSE:
                addApiCounter(true);
                presenter.browseNearby(new SortFilterRequest(userId, mLongitudeText+","+mLatitudeText, "no", "no", "no"));
                break;
            case PAGE_RECOMMEND:
                addApiCounter(true);
                presenter.getSortFilterList(RestoListModel.RECOMMEND, currRequest);
                consumeType = RestoListModel.RECOMMEND;
                break;
            case PAGE_SEARCH_R:
                String countryId = intent.getStringExtra("country");
                String provinceId = intent.getStringExtra("province");
                String cityId = intent.getStringExtra("city");
                consumeType = RestoListModel.SEARCH_R;
                currRequest.setRegionId(countryId, provinceId, cityId);
                addApiCounter(true);
                presenter.getSortFilterList(RestoListModel.SEARCH_R, currRequest);
                break;
            case PAGE_SEARCH_K:
                String keyword = intent.getStringExtra("keyword");
                consumeType = RestoListModel.SEARCH_K;
                currRequest.setKeyword(keyword);
                addApiCounter(true);
                presenter.getSortFilterList(RestoListModel.SEARCH_K, currRequest);
                break;
        }
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    private ColorStateList setColorStateList(int color) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled} //enabled
                }, new int[]{color}
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_layout:
                sortLayout.collapse();
                filterLayout.toggle();
                isFilterToggled = !isFilterToggled;
                isSortToggled = false;
                changeMenuButton();
                break;
            case R.id.sort_layout:
                filterLayout.collapse();
                sortLayout.toggle();
                isFilterToggled = false;
                isSortToggled = !isSortToggled;
                changeMenuButton();
                break;
            case R.id.select_all:
                for (int i = 0; i < FILTER_BOX_QTY; i++) {
                    filterBox[i].setChecked(true);
                }
                break;
            case R.id.clear:
                for (int i = 0; i < FILTER_BOX_QTY; i++) {
                    filterBox[i].setChecked(false);
                }
                break;
            case R.id.sort_alpha_ll:
                changeSortButton(0);
                break;
            case R.id.sort_distance_ll:
                changeSortButton(1);
                break;
            case R.id.sort_date_ll:
                changeSortButton(2);
                break;
            case R.id.sort_price_ll:
                changeSortButton(3);
                break;
            case R.id.submit_filter:
                filterLayout.collapse();
                if (pageType == PAGE_BOOKMARK || pageType == PAGE_BEENHERE || pageType == PAGE_RECOMMEND || pageType == PAGE_SEARCH_R || pageType == PAGE_SEARCH_K) {
                    sortFilter();
                } else {
                    if (pageType == PAGE_BROWSE) {
                        addApiCounter(true);
                        presenter.browseNearby(new SortFilterRequest(
                                userId,
                                mLongitudeText + "," + mLatitudeText,
                                boolToYesNo(filterBox[4].isChecked()),
                                boolToYesNo(filterBox[5].isChecked()),
                                boolToYesNo(filterBox[6].isChecked())
                        ));
                    }
                }
                break;
            case R.id.submit_sort:
                if(currSelectedSort == -1){
                    Toast.makeText(RestoListActivity.this, "Please choose sort type", Toast.LENGTH_SHORT).show();
                }else {
                    sortLayout.collapse();
                    if (pageType == PAGE_BOOKMARK || pageType == PAGE_BEENHERE || pageType == PAGE_RECOMMEND || pageType == PAGE_SEARCH_R || pageType == PAGE_SEARCH_K) {
                        sortFilter();
                    }
//                    else {
//                        if (pageType == PAGE_BROWSE) {
//                            addApiCounter(true);
//                            presenter.browseNearby(
//                                    userId,
//                                    mLongitudeText,
//                                    mLatitudeText,
//                                    (currSelectedSort == -1) ? "" : sortText[currSelectedSort],
//                                    (orderGroup.getCheckedRadioButtonId() == R.id.asc) ? "asc" : "desc",
//                                    getFilter()
//                            );
//                        }
//                    }
                }
                break;
        }
    }

    // unify all pages that use SortFilterRequest
    private void sortFilter() {
        String sortType = (currSelectedSort == -1) ? "alphabet" : sortText[currSelectedSort];
        String sortOrder = (orderGroup.getCheckedRadioButtonId() == R.id.asc) ? "asc" : "desc";
        addApiCounter(true);

        // assigns values according to selected sortfilter menu
        currRequest.assign(
                userId,
                sortType,
                mLongitudeText + "," + mLatitudeText,
                sortOrder,
                boolToYesNo(filterBox[0].isChecked()),
                boolToYesNo(filterBox[2].isChecked()),
                boolToYesNo(filterBox[3].isChecked()),
                boolToYesNo(filterBox[1].isChecked()),
                boolToYesNo(filterBox[4].isChecked()),
                boolToYesNo(filterBox[5].isChecked()),
                boolToYesNo(filterBox[6].isChecked())
        );
        presenter.getSortFilterList(consumeType, currRequest);
    }

    private String boolToYesNo(boolean b) {
        if (b) {
            return "yes";
        }
        return "no";
    }

    private void changeMenuButton() {
        boolean b;
        ImageView iv;
        LinearLayout ll;
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                b = isFilterToggled;
                iv = filterArrow;
                ll = filterLinearLayout;
            } else {
                b = isSortToggled;
                iv = sortArrow;
                ll = sortLinearLayout;
            }

            if (b) {
                iv.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp_m);
                ll.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            } else {
                iv.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp_m);
                ll.setBackgroundColor(ContextCompat.getColor(this, R.color.selectedGray));
            }
            iv.setColorFilter(ContextCompat.getColor(this, R.color.accentGreenBtn));
        }
    }

    private String getFilter() {
        if (filterBox[4].isChecked() && !filterBox[5].isChecked() && !filterBox[6].isChecked()) {
            return "vegan";
        }
        if (!filterBox[4].isChecked() && filterBox[5].isChecked() && !filterBox[6].isChecked()) {
            return "vegetarian";
        }
        if (!filterBox[4].isChecked() && !filterBox[5].isChecked() && filterBox[6].isChecked()) {
            return "vegeready";
        }
        return "";
    }

    private void changeSortButton(int index) {
        int color;
        // change selection
        if (currSelectedSort != index) {
            if (currSelectedSort != -1) {
                sortSelected[currSelectedSort] = false;
            }
            sortSelected[index] = true;
            currSelectedSort = index;
        } else {
            sortSelected[index] = false;
            currSelectedSort = -1;
        }
        // change color
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            if (sortSelected[i]) {
                color = ContextCompat.getColor(this, R.color.accentGreenBtn);
            } else {
                color = ContextCompat.getColor(this, R.color.black);
            }
            ((ImageView) sortButtonLayout[i].getChildAt(0)).setColorFilter(color);
        }
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
    public void onRestoList(Uri uri) {

    }

    @Override
    public void changeBookmark(int restoId, boolean isBookmarked) {
        addApiCounter(true);
        presenter.changeBookmark(userId, restoId, isBookmarked);
    }

    @Override
    public void removeBeenHere(Resto r) {
        addApiCounter(true);
        presenter.changeBeenHere(userId, r.getId(), false);
    }

    @Override
    public void toggleSpinner(boolean b) {
        addApiCounter(b);
    }

    @Override
    public void onLoadMore() {
        addApiCounter(true);
        if(currRequest == null){
            currRequest = new SortFilterRequest(userId);
        }
        currRequest.setPage(currRequest.getPage()+1);
//        String json = new Gson().toJson(currRequest);
//        Log.e(TAG, "LOAD "+json);
        presenter.getSortFilterList(consumeType, currRequest);
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

    /**
     * params
     * 0 = isHideFlag
     * 1 = isLoadMore
     * 2 = isBookmark
     * 3 = isBeenHere
     * */
    @Override
    public void successBrowseNearby(ArrayList<Resto> data) {
        addApiCounter(false);
        hasBrowsedNearby = true;
        filterLayout.setVisibility(View.VISIBLE);
        sortLayout.setVisibility(View.VISIBLE);
        restoList = data;
        boolean isHideFlag = userId == -1; // if is not logged in, hide flag
        boolean[] params = new boolean[]{isHideFlag, false, false, false};
        restoListFragment.setData(restoList, params, 1);
    }

    @Override
    public void failedBrowseNearby(String error) {
        addApiCounter(false);
        Toast.makeText(RestoListActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successChangeBookmark(int placeId, boolean isBookmarked) {
        addApiCounter(false);
        restoListFragment.updateBookmark(placeId, isBookmarked);
        String change = isBookmarked ? "added" : "removed";
        Toast.makeText(RestoListActivity.this, "Bookmark "+change+" successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedChangeBookmark(String message) {
        addApiCounter(false);
        Toast.makeText(RestoListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successChangeBeenHere(int placeId) {
        addApiCounter(false);
        restoListFragment.removeData(placeId);
        Toast.makeText(RestoListActivity.this, "Been here removed successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedChangeBeenHere(String message) {
        addApiCounter(false);
        Toast.makeText(RestoListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * params
     * 0 = isHideFlag
     * 1 = isLoadMore
     * 2 = isBookmark
     * 3 = isBeenHere
     * */
    @Override
    public void successGetSortFilterList(ArrayList<Resto> list) {
        addApiCounter(false);
        hasBrowsedNearby = true;
        filterLayout.setVisibility(View.VISIBLE);
        sortLayout.setVisibility(View.VISIBLE);
        restoList = list;
        boolean isHideFlag = (userId == -1 || pageType == PAGE_BOOKMARK); // if not logged in or bookmark, hide flag
        boolean[] params = new boolean[]{
                isHideFlag,
                true,
                pageType == PAGE_BOOKMARK,
                pageType == PAGE_BEENHERE
        };
        restoListFragment.setData(restoList, params, 2);
    }

    @Override
    public void failedGetSortFilterList(String type) {
        addApiCounter(false);
        Toast.makeText(RestoListActivity.this, "No " + type + " data", Toast.LENGTH_SHORT).show();
        successGetSortFilterList(new ArrayList<Resto>());
    }

    @Override
    public void onBackPressed() {
        if (isFilterToggled) {
            filterLayout.collapse();
            isFilterToggled = false;
        } else if (isSortToggled) {
            sortLayout.collapse();
            isSortToggled = false;
        } else {
            super.onBackPressed();
        }
        changeMenuButton();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createLocationRequest();
        updateLocation();
        if (mLastLocation != null) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
            hasBrowsedNearby = true;
            loadPageData();
        }

        if (isRequestingLocationUpdates) {
            startLocationUpdates();
        }
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
        if (mLastLocation != null) {
//            Location testLocation = new Location("test");
//            testLocation.setLatitude(-6.1379595);
//            testLocation.setLongitude(106.9175735);
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
//            Log.e(TAG, String.valueOf(mLastLocation.distanceTo(testLocation)) + " meters");
        }
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
                    googleApiClient, mLocationRequest, (LocationListener) this);
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
        if (mLocationRequest != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, (LocationListener) this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        addApiCounter(false);
        updateLocation();
        if (mLastLocation != null) {
            //so that it doesnt rehit API every time ur phone move
            if(!hasBrowsedNearby) {
                hasBrowsedNearby = true;
                loadPageData();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        addApiCounter(false);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        addApiCounter(false);
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void checkIfLocationIsOff() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());

        // this code is to get user to turn on their location settings if not already
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
//                        Log.e(TAG, status.getStatusMessage());
//                        Log.e(TAG, locationSettingsStates.toString());
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    RestoListActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }
}
