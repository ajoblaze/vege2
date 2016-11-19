package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.PhotoFragment;
import com.imajiku.vegefinder.fragment.ReviewFragment;
import com.imajiku.vegefinder.model.model.RestoDetailModel;
import com.imajiku.vegefinder.model.presenter.RestoDetailPresenter;
import com.imajiku.vegefinder.model.view.RestoDetailView;
import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.pojo.RestoImage;
import com.imajiku.vegefinder.pojo.RestoMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestoDetailActivity extends AppCompatActivity
        implements PhotoFragment.PhotoListener, ReviewFragment.ReviewListener, View.OnClickListener, RestoDetailView {

    private int restoId;
    private ImageView banner;
    private TextView rate, guest;
    private TextView[] restoHead = new TextView[5];
    private TextView[] restoContentTitle = new TextView[6];
    private TextView[] restoContent = new TextView[6];
    private Button map, addPhoto, addReview, reportProblem;
    private Button[] buttonHead = new Button[5];
    private PhotoFragment photoFragment;
    private TextView openDays;
    private ReviewFragment reviewFragment;
    private RestoDetailPresenter presenter;
    private String TAG= "exc";
    private RestoDetail restoDetail;
    private boolean[] buttonStatus = new boolean[2];
    private LinearLayout photoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_detail);

        presenter = new RestoDetailPresenter(this);
        RestoDetailModel model = new RestoDetailModel(presenter);
        presenter.setModel(model);

        restoDetail = null;
        restoId = getIntent().getIntExtra("resto", -1);
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
        buttonHead[3] = (Button) findViewById(R.id.btn_call);
        buttonHead[4] = (Button) findViewById(R.id.btn_check_in);
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
        reviewFragment = (ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.review_fragment);
        addPhoto = (Button) findViewById(R.id.btn_add_photo);
        addReview = (Button) findViewById(R.id.btn_add_review);
        openDays = (TextView) findViewById(R.id.open_days);
        reportProblem = (Button) findViewById(R.id.btn_report);

        presenter.getRestoDetail(restoId);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btn_bookmark: {
                int idx = 0;
                buttonStatus[idx] = !buttonStatus[idx];
                changeButtonColor(idx);
                // call API
            }
                break;
            case R.id.btn_been_here: {
                int idx = 1;
                buttonStatus[idx] = !buttonStatus[idx];
                changeButtonColor(idx);
                // call API
            }
                break;
            case R.id.btn_book:
                i = new Intent(RestoDetailActivity.this, BookActivity.class);
                i.putExtra("id", restoDetail.getId());
                i.putExtra("title", restoDetail.getTitle());
                i.putExtra("image", restoDetail.getImage());
                startActivity(i);
                break;
            case R.id.btn_call:
//                i = new Intent(RestoDetailActivity.this, CallActivity.class);
//                i.putExtra("id", resto.getId());
//                i.putExtra("title", resto.getTitle());
//                i.putExtra("image", resto.getImage());
//                startActivity(i);
                break;
            case R.id.btn_check_in:
                i = new Intent(RestoDetailActivity.this, CheckInActivity.class);
//                i.putExtra("id", resto.getId());
//                i.putExtra("title", resto.getTitle());
//                i.putExtra("image", resto.getImage());
                startActivity(i);
                break;
            case R.id.btn_map:
                i = new Intent(RestoDetailActivity.this, MapActivity.class);
                i.putExtra("id", restoDetail.getId());
                i.putExtra("title", restoDetail.getTitle());
                i.putExtra("address", restoDetail.getAddress());
                i.putExtra("longitude", restoDetail.getLongitude());
                i.putExtra("latitude", restoDetail.getLatitude());
                startActivity(i);
                break;
            case R.id.btn_add_photo:
                i = new Intent(RestoDetailActivity.this, AddPhotoActivity.class);
                i.putExtra("id", restoId);
                startActivity(i);
                break;
            case R.id.btn_add_review:
                break;
            case R.id.btn_report:
                break;
        }
    }

    @Override
    public void onPhoto(int position) {
        Intent i = new Intent(this, PhotoDetailActivity.class);
        i.putExtra("restoId", restoId);
        i.putExtra("userId", 1);
        i.putExtra("position", position);
        startActivity(i);
    }

    @Override
    public void onReview(Uri uri) {

    }

    private void changeButtonColor(int index){
        int colorId;
        if(buttonStatus[index]){
            colorId = R.color.translucentWhite75;
        }else{
            colorId = R.color.translucentLime75;
        }
        buttonHead[index].setBackgroundColor(ContextCompat.getColor(this, colorId));
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
        if(restoDetail.getRestoImg().size()==0){
            photoLayout.setVisibility(View.GONE);
        }
        // load data
        Picasso.with(this)
                .load(restoDetail.getImage())
                .noFade()
                .fit()
                .centerCrop()
                .into(banner);
//        if(restoDetail.getAvgRate() != null) {
//            rate.setText(restoDetail.getAvgRate());
            guest.setText("From " + restoDetail.getCountReview() + " guests");
//        }
        restoHead[0].setText(restoDetail.getTitle());
        restoHead[1].setText(restoDetail.getAddress());
        if(restoDetail.isOpenNow()) {
            restoHead[2].setTextColor(ContextCompat.getColor(this, R.color.translucentGreen75));
            restoHead[2].setText("OPEN");
        }else{
            restoHead[2].setTextColor(ContextCompat.getColor(this, R.color.translucentRed75));
            restoHead[2].setText("CLOSED");
        }
        restoHead[3].setText(restoDetail.getOpenTime());
        restoHead[4].setText(restoDetail.getStatistics());
        fillRestoContent(data);

        buttonStatus[0] = restoDetail.getStatusBookmark().equals("active");
        buttonStatus[1] = restoDetail.getStatusBeenHere().equals("active");
        for(int i=0;i<2;i++){
            changeButtonColor(i);
        }

        ArrayList<String> imageList = new ArrayList<>();
        for(RestoImage img : data.getRestoImg()){
            imageList.add(img.getImage());
        }
        photoFragment.setData(imageList, restoId, 1);
//        presenter.loadReview(r.getId());
        openDays.setText(restoDetail.getWeekOpenTime());
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
            if(menu && facility){
                break;
            }
        }
    }

    private void toast(String message){
        Toast.makeText(RestoDetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedGetRestoDetail() {
        Log.e("exc", "failedGetRestoImages");
    }

    @Override
    public void successAddBookmark() {

    }

    @Override
    public void failedAddBookmark(String message) {
        toast(message);
    }

    @Override
    public void successRemoveBookmark() {

    }

    @Override
    public void failedRemoveBookmark(String message) {
        toast(message);
    }

    @Override
    public void successAddBeenHere() {

    }

    @Override
    public void failedAddBeenHere(String message) {
        toast(message);
    }

    @Override
    public void successRemoveBeenHere() {

    }

    @Override
    public void failedRemoveBeenHere(String message) {
        toast(message);
    }
}
