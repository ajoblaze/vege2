package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.PhotoFragment;
import com.imajiku.vegefinder.fragment.ReviewFragment;
import com.imajiku.vegefinder.model.model.RestoDetailModel;
import com.imajiku.vegefinder.model.presenter.RestoDetailPresenter;
import com.imajiku.vegefinder.model.view.RestoDetailView;
import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.pojo.RestoFacility;
import com.imajiku.vegefinder.pojo.RestoImage;
import com.imajiku.vegefinder.pojo.RestoMenu;
import com.imajiku.vegefinder.pojo.Review;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestoDetailActivity extends AppCompatActivity
        implements PhotoFragment.PhotoListener, ReviewFragment.ReviewListener, View.OnClickListener, RestoDetailView {

    private int restoId, userId;
    private ImageView banner;
    private TextView rate, guest;
    private TextView[] restoHead = new TextView[5];
    private TextView[] restoContentTitle = new TextView[6];
    private TextView[] restoContent = new TextView[6];
    private Button map, addPhoto, addReview, reportProblem;
    private Button[] buttonHead = new Button[5];
    private PhotoFragment photoFragment;
    private TextView openDays, openDaysTitle;
    private ReviewFragment reviewFragment;
    private RestoDetailPresenter presenter;
    private String TAG = "exc";
    private RestoDetail restoDetail;
    private boolean[] buttonStatus = new boolean[2];
    private LinearLayout photoLayout, reviewLayout;
    private Typeface tf, tfBold;
    private ProgressBar progressBar;
    private int apiCallCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_detail);

        presenter = new RestoDetailPresenter(this);
        RestoDetailModel model = new RestoDetailModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");
        tfBold = Typeface.createFromAsset(getAssets(), "fonts/VDS_Bold_New.ttf");
        initToolbar();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        restoDetail = null;
        restoId = getIntent().getIntExtra("restoId", -1);
        userId = CurrentUser.getId(this);
        banner = (ImageView) findViewById(R.id.resto_img);
        rate = (TextView) findViewById(R.id.rating);
        guest = (TextView) findViewById(R.id.guest);
        restoHead[0] = (TextView) findViewById(R.id.resto_title);
        restoHead[1] = (TextView) findViewById(R.id.resto_address);
        restoHead[2] = (TextView) findViewById(R.id.resto_is_open);
        restoHead[3] = (TextView) findViewById(R.id.resto_open_hours);
        restoHead[4] = (TextView) findViewById(R.id.resto_statistic);
        buttonHead[0] = (Button) findViewById(R.id.btn_bookmark);
        buttonHead[1] = (Button) findViewById(R.id.btn_been_here);
        buttonHead[2] = (Button) findViewById(R.id.btn_book);
        buttonHead[3] = (Button) findViewById(R.id.btn_check_in);
        buttonHead[4] = (Button) findViewById(R.id.btn_call);
        restoContentTitle[0] = (TextView) findViewById(R.id.title_1);
        restoContentTitle[1] = (TextView) findViewById(R.id.title_2);
        restoContentTitle[2] = (TextView) findViewById(R.id.title_3);
        restoContentTitle[3] = (TextView) findViewById(R.id.title_4);
        restoContentTitle[4] = (TextView) findViewById(R.id.title_5);
        restoContentTitle[5] = (TextView) findViewById(R.id.title_6);
        restoContent[0] = (TextView) findViewById(R.id.content_1);
        restoContent[1] = (TextView) findViewById(R.id.content_2);
        restoContent[2] = (TextView) findViewById(R.id.content_3);
        restoContent[3] = (TextView) findViewById(R.id.content_4);
        restoContent[4] = (TextView) findViewById(R.id.content_5);
        restoContent[5] = (TextView) findViewById(R.id.content_6);
        map = (Button) findViewById(R.id.btn_map);
        photoLayout = (LinearLayout) findViewById(R.id.photo_layout);
        photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.photo_fragment);
        reviewLayout = (LinearLayout) findViewById(R.id.review_layout);
        reviewFragment = (ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.review_fragment);
        addPhoto = (Button) findViewById(R.id.btn_add_photo);
        addReview = (Button) findViewById(R.id.btn_add_review);
        openDaysTitle = (TextView) findViewById(R.id.open_days_title);
        openDays = (TextView) findViewById(R.id.open_days);
        reportProblem = (Button) findViewById(R.id.btn_report);

        if(isLoggedIn(false)){
            for(int i=0;i<4;i++) {
                buttonHead[i].setVisibility(View.VISIBLE);
            }
            addPhoto.setVisibility(View.VISIBLE);
            addReview.setVisibility(View.VISIBLE);
        }

        addApiCounter(true);
        presenter.getRestoDetail(restoId, userId);

        //tf
        TextView of10 = (TextView) findViewById(R.id.of_10);
        of10.setTypeface(tf);
        rate.setTypeface(tf);
        guest.setTypeface(tf);

        for (TextView t : restoHead) {
            t.setTypeface(tf);
        }
        for (Button b : buttonHead) {
            b.setTypeface(tf);
        }
        for (TextView t : restoContentTitle) {
            t.setTypeface(tfBold);
        }
        for (TextView t : restoContent) {
            t.setTypeface(tf);
        }
        map.setTypeface(tf);
        addPhoto.setTypeface(tf);
        addReview.setTypeface(tf);
        openDaysTitle.setTypeface(tfBold);
        openDays.setTypeface(tf);
        reportProblem.setTypeface(tf);
    }

    public void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
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
        Intent i;
        switch (view.getId()) {
            case R.id.btn_bookmark: {
                int idx = 0;
                Log.e(TAG, String.valueOf(buttonStatus[idx]));
                addApiCounter(true);
                presenter.changeBookmark(userId, restoId, buttonStatus[idx]);
            }
            break;
            case R.id.btn_been_here: {
                int idx = 1;
                Log.e(TAG, String.valueOf(buttonStatus[idx]));
                addApiCounter(true);
                presenter.changeBeenHere(userId, restoId, buttonStatus[idx]);
            }
            break;
            case R.id.btn_book:
                i = new Intent(RestoDetailActivity.this, BookActivity.class);
                i.putExtra("restoId", restoDetail.getId());
                i.putExtra("title", restoDetail.getTitle());
                i.putExtra("image", restoDetail.getImageUrl());
                startActivity(i);
                break;
            case R.id.btn_call:
                i = new Intent(RestoDetailActivity.this, CallActivity.class);
                i.putExtra("restoId", restoDetail.getId());
                i.putExtra("phone", restoDetail.getPhone());
                startActivity(i);
                break;
            case R.id.btn_check_in:
                i = new Intent(RestoDetailActivity.this, CheckInActivity.class);
                i.putExtra("restoId", restoDetail.getId());
                i.putExtra("title", restoDetail.getTitle());
                i.putExtra("image", restoDetail.getImageUrl());
                startActivity(i);
                break;
            case R.id.btn_map:
                i = new Intent(RestoDetailActivity.this, MapActivity.class);
                i.putExtra("restoId", restoDetail.getId());
                i.putExtra("title", restoDetail.getTitle());
                i.putExtra("address", restoDetail.getAddress());
                i.putExtra("longitude", restoDetail.getLongitude());
                i.putExtra("latitude", restoDetail.getLatitude());
                startActivity(i);
                break;
            case R.id.btn_add_photo:
                i = new Intent(RestoDetailActivity.this, AddPhotoActivity.class);
                i.putExtra("restoId", restoId);
                startActivityForResult(i, 1);
                break;
            case R.id.btn_add_review:
                i = new Intent(RestoDetailActivity.this, AddReviewActivity.class);
                i.putExtra("restoId", restoId);
                startActivityForResult(i, 1);
                break;
            case R.id.btn_report:
                i = new Intent(RestoDetailActivity.this, SendMessageActivity.class);
                i.putExtra("restoId", restoId);
                i.putExtra("type", SendMessageActivity.REPORT);
                startActivity(i);
                break;
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
        Log.e(TAG, "apiCallCounter: "+apiCallCounter);
    }

    private boolean isLoggedIn(boolean toast){
        if(CurrentUser.getId(this) <= -1){
            if(toast) {
                Toast.makeText(RestoDetailActivity.this, "You have to be logged in to use", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.getRestoDetail(restoId, userId);
        addApiCounter(true);
    }

    @Override
    public void onPhoto(int position) {
        Intent i = new Intent(this, PhotoDetailActivity.class);
        i.putExtra("restoId", restoId);
        i.putExtra("position", position);
        startActivity(i);
    }

    @Override
    public void onReview(Uri uri) {

    }

    private void changeButtonColor(int index) {
        int bgId, textColorId;
        if (buttonStatus[index]) {
            bgId = R.drawable.rounded_green_selected;
            textColorId = R.color.accentGreenBtn;
        } else {
            bgId = R.drawable.rounded_green;
            textColorId = R.color.white;
        }
        buttonHead[index].setBackground(ContextCompat.getDrawable(this, bgId));
        buttonHead[index].setTextColor(ContextCompat.getColor(this, textColorId));
    }

    @Override
    public void successGetRestoDetail(RestoDetail data) {
        // assign listener to buttons
        for (int i = 0; i < 5; i++) {
            buttonHead[i].setOnClickListener(this);
        }
        map.setOnClickListener(this);
        addPhoto.setOnClickListener(this);
        addReview.setOnClickListener(this);
        reportProblem.setOnClickListener(this);

        restoDetail = data;

        // load data
        Picasso.with(this)
                .load(restoDetail.getImageUrl())
                .noFade()
                .fit()
                .centerCrop()
                .placeholder(R.drawable.empty_image)
                .into(banner);
        if (restoDetail.getAvgRate() != null) {
            rate.setText(restoDetail.getAvgRate());
            guest.setText(restoDetail.getCountReview() + " guest" + (restoDetail.getCountReview() < 2 ? "" : "s"));
        }
        findViewById(R.id.rate_guest).setVisibility(View.VISIBLE);

        restoHead[0].setText(restoDetail.getTitle());
        restoHead[1].setText(restoDetail.getAddress());
        if (restoDetail.isOpenNow()) {
            restoHead[2].setTextColor(ContextCompat.getColor(this, R.color.translucentGreen75));
            restoHead[2].setText("OPEN");
        } else {
            restoHead[2].setTextColor(ContextCompat.getColor(this, R.color.translucentRed75));
            restoHead[2].setText("CLOSED");
        }
        restoHead[3].setText(restoDetail.getOpenTime());
        restoHead[4].setText(restoDetail.getStatistics());
        fillRestoContent(data);

        buttonStatus[0] = restoDetail.getStatusBookmark().equals("active");
        buttonStatus[1] = restoDetail.getStatusBeenHere().equals("active");
        for (int i = 0; i < 2; i++) {
            changeButtonColor(i);
        }

        ArrayList<String> imageList = new ArrayList<>();
        for (RestoImage img : restoDetail.getRestoImg()) {
            imageList.add(img.getImage());
        }
        photoFragment.setData(imageList, restoId, 1);

        ArrayList<Review> miniList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            if(i == restoDetail.getRatesReview().size()){
                break;
            }
            miniList.add(restoDetail.getRatesReview().get(i));
        }
        reviewFragment.setData(miniList, restoId);
        openDaysTitle.setVisibility(View.VISIBLE);
        openDays.setText(restoDetail.getWeekOpenTime());
        addApiCounter(false);
    }

    private void fillRestoContent(RestoDetail data) {
        StringBuilder builder;
        restoContentTitle[0].setText("TYPE");
        restoContent[0].setText(Integer.toString(data.getType()));
        restoContentTitle[1].setText("AVERAGE COST");
        restoContent[1].setText(data.getAverageCost());

        boolean menu = false;
        boolean facility = false;

        for (int i = 2; i < 6; i++) {
            if (!facility && data.getRestoFacility() != null) {
                facility = true;
                restoContentTitle[i].setText("FACILITIES");
                builder = new StringBuilder();
                int idx = 0;
                for (RestoFacility f : data.getRestoFacility()) {
                    builder.append(f.getTitle());
                    if (idx < data.getRestoFacility().size() - 1) {
                        builder.append("\n");
                    }
                    idx++;
                }
                restoContent[i].setText(builder.toString());
                continue;
            }
            if (!menu && data.getRestoMenu() != null) {
                menu = true;
                restoContentTitle[i].setText("POPULAR MENU");
                builder = new StringBuilder();
                int idx = 0;
                for (RestoMenu m : data.getRestoMenu()) {
                    builder.append(m.getTitle());
                    if (idx < data.getRestoMenu().size() - 1) {
                        builder.append("\n");
                    }
                    idx++;
                }
                restoContent[i].setText(builder.toString());
                continue;
            }
//            if (!facility && data.getResto().getWifi() != 0 && data.getResto().getDelivery() != 0){
//                facility = true;
//                boolean wifi = false;
//                restoContentTitle[i].setText("FACILITIES");
//                builder = new StringBuilder();
//                if(data.getResto().getWifi() == 1){
//                    builder.append("Free Wifi");
//                    wifi = true;
//                }
//                if(data.getResto().getDelivery() == 1){
//                    if(wifi){
//                        builder.append("\n");
//                    }
//                    builder.append("Delivery");
//                }
//                restoContent[i].setText(builder.toString());
//            }
            if (menu && facility) {
                break;
            }
        }
    }

    private void toast(String message) {
        Toast.makeText(RestoDetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedGetRestoDetail() {
        addApiCounter(false);
        toast("Failed getting place detail data");
        Log.e("exc", "failedGetRestoImages");
    }

    @Override
    public void successChangeBookmark() {
        addApiCounter(false);
        int idx = 0;
        buttonStatus[idx] = !buttonStatus[idx];
        changeButtonColor(idx);
        toast("Success "+(buttonStatus[idx]?"add":"remove")+" bookmark");
    }

    @Override
    public void failedChangeBookmark(String message) {
        addApiCounter(false);
        toast(message);
    }

    @Override
    public void successChangeBeenHere() {
        addApiCounter(false);
        int idx = 1;
        buttonStatus[idx] = !buttonStatus[idx];
        changeButtonColor(idx);
        toast("Success "+(buttonStatus[idx]?"add":"remove")+" been here");
    }

    @Override
    public void failedChangeBeenHere(String message) {
        addApiCounter(false);
        toast(message);
    }
}
