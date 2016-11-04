package com.imajiku.vegefinder.activity;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class BrowseActivity extends AppCompatActivity implements
        RestoListFragment.RestoListListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    private static final int REQUEST_CHECK_SETTINGS = 8008;
    private static final int FILTER_BOX_QTY = 6;
    private static final int SORT_BUTTON_QTY = 4;
    private RestoListFragment restoListFragment;
    private Button filter, sort;
    private String TAG = "exc";
    private GoogleApiClient googleApiClient;
    private boolean isRequestingLocationUpdates;
    private LocationRequest mLocationRequest;
    private ExpandableRelativeLayout filterLayout, sortLayout;
    private Button selectAll, clear, submitFilter, submitSort;
    private CheckBox[] filterBox;
    private RadioGroup orderGroup;
    private RadioButton desc;
    private boolean[] sortSelected;
    private Button[] sortButton;
    private int currSelectedSort = -1;

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
        setContentView(R.layout.activity_browse);
        restoListFragment = (RestoListFragment) getSupportFragmentManager().findFragmentById(R.id.resto_list_fragment);
        filter = (Button) findViewById(R.id.filter_btn);
        sort = (Button) findViewById(R.id.sort_btn);
        filterLayout = (ExpandableRelativeLayout) findViewById(R.id.layout_filter);
        sortLayout = (ExpandableRelativeLayout) findViewById(R.id.layout_sort);
        filter.setOnClickListener(this);
        sort.setOnClickListener(this);
        filterBox = new CheckBox[FILTER_BOX_QTY];
        sortButton = new Button[SORT_BUTTON_QTY];
        sortSelected = new boolean[]{false, false, false, false};

        // filter
        selectAll = (Button) findViewById(R.id.select_all);
        clear = (Button) findViewById(R.id.clear);
        filterBox[0] = (CheckBox) findViewById(R.id.open_now);
        filterBox[1] = (CheckBox) findViewById(R.id.rate_8);
        filterBox[2] = (CheckBox) findViewById(R.id.bookmarked);
        filterBox[3] = (CheckBox) findViewById(R.id.vegan);
        filterBox[4] = (CheckBox) findViewById(R.id.been_here);
        filterBox[5] = (CheckBox) findViewById(R.id.vege);
        submitFilter = (Button) findViewById(R.id.submit_filter);

        //sort
        sortButton[0] = (Button) findViewById(R.id.alpha);
        sortButton[1] = (Button) findViewById(R.id.distance);
        sortButton[2] = (Button) findViewById(R.id.date);
        sortButton[3] = (Button) findViewById(R.id.price);
        orderGroup = (RadioGroup) findViewById(R.id.sort_order);
        submitSort = (Button) findViewById(R.id.submit_sort);

        // filter
        selectAll.setOnClickListener(this);
        clear.setOnClickListener(this);
        submitFilter.setOnClickListener(this);

        //sort
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            sortButton[i].setOnClickListener(this);
        }
        submitSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_btn:
                sortLayout.collapse();
                filterLayout.toggle();
                break;
            case R.id.sort_btn:
                filterLayout.collapse();
                sortLayout.toggle();
                break;
            case R.id.select_all:
                for (int i = 0; i < FILTER_BOX_QTY; i++) {
                    filterBox[i].setSelected(true);
                }
                break;
            case R.id.clear:
                for (int i = 0; i < FILTER_BOX_QTY; i++) {
                    filterBox[i].setSelected(false);
                }
                break;
            case R.id.alpha:
                if (currSelectedSort != 0) {
                    if (currSelectedSort == -1) {
                        sortSelected[currSelectedSort] = false;
                    }
                    sortSelected[0] = true;
                } else {
                    sortSelected[0] = false;
                }
                currSelectedSort = 0;
                changeSortButton();
                break;
            case R.id.distance:
                if (currSelectedSort != 1) {
                    if (currSelectedSort == -1) {
                        sortSelected[currSelectedSort] = false;
                    }
                    sortSelected[1] = true;
                } else {
                    sortSelected[1] = false;
                }
                currSelectedSort = 1;
                changeSortButton();
                break;
            case R.id.date:
                if (currSelectedSort != 2) {
                    if (currSelectedSort == -1) {
                        sortSelected[currSelectedSort] = false;
                    }
                    sortSelected[2] = true;
                } else {
                    sortSelected[2] = false;
                }
                currSelectedSort = 2;
                changeSortButton();
                break;
            case R.id.price:
                if (currSelectedSort != 3) {
                    if (currSelectedSort == -1) {
                        sortSelected[currSelectedSort] = false;
                    }
                    sortSelected[3] = true;
                } else {
                    sortSelected[3] = false;
                }
                currSelectedSort = 3;
                changeSortButton();
                break;
            case R.id.submit_filter:
                filterLayout.collapse();
//                restoListFragment.filter(getFilterResult());
                break;
            case R.id.submit_sort:
                sortLayout.collapse();
//                restoListFragment.sort(getSortResult());
                break;
        }
    }

    private int[] getSortResult() {
        int[] sortResult = new int[2];
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            if(filterBox[i].isSelected()) {
                sortResult[0] = i;
                break;
            }
        }
        sortResult[1] =
                (orderGroup.getCheckedRadioButtonId() == R.id.asc) ? 0 : 1;
        return sortResult;
    }

    private boolean[] getFilterResult() {
        boolean[] filterResult = new boolean[FILTER_BOX_QTY];
        for (int i = 0; i < FILTER_BOX_QTY; i++) {
            filterResult[i] = filterBox[i].isSelected();
        }
        return filterResult;
    }

    private void changeSortButton() {
        int color;
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            if (sortSelected[i]) {
                color = ContextCompat.getColor(this, R.color.translucentGreen75);
            } else {
                color = ContextCompat.getColor(this, R.color.translucentRed75);
            }
            sortButton[i].setBackgroundColor(color);
        }
    }

    @Override
    public void onRestoList(Uri uri) {

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
    public void onConnected(@Nullable Bundle bundle) {
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
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (mLastLocation != null) {
            Location testLocation = new Location("test");
            testLocation.setLatitude(-6.1379595);
            testLocation.setLongitude(106.9175735);
            String mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            String mLongitudeText = String.valueOf(mLastLocation.getLongitude());
            Log.e(TAG, mLatitudeText + " " + mLongitudeText);
            Log.e(TAG, String.valueOf(mLastLocation.distanceTo(testLocation)) + " meters");
        }

        if (isRequestingLocationUpdates) {
            startLocationUpdates();
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
        if (mLocationRequest != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, (LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "u2");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "u3");
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
                        Log.e(TAG, status.getStatusMessage());
                        Log.e(TAG, locationSettingsStates.toString());
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    BrowseActivity.this,
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

    @Override
    public void onLocationChanged(Location location) {

    }
}
