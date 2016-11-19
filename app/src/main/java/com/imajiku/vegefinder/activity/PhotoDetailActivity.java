package com.imajiku.vegefinder.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.PhotoDetailAdapter;
import com.imajiku.vegefinder.model.model.PhotoListModel;
import com.imajiku.vegefinder.model.presenter.PhotoListPresenter;
import com.imajiku.vegefinder.model.view.PhotoListView;

import java.util.ArrayList;

public class PhotoDetailActivity extends AppCompatActivity implements PhotoListView, View.OnClickListener {

    private PhotoListPresenter presenter;
    private ViewPager viewPager;
    private ImageView share;
    private int restoId, userId, position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        restoId = getIntent().getIntExtra("restoId", -1);
        userId = getIntent().getIntExtra("userId", -1);
        position = getIntent().getIntExtra("position", -1);

        presenter = new PhotoListPresenter(this);
        PhotoListModel model = new PhotoListModel(presenter);
        presenter.setModel(model);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(this);

        presenter.getRestoImages(restoId, userId);
    }

    @Override
    public void successGetRestoImages(ArrayList<String> list) {
        PhotoDetailAdapter pagerAdapter = new PhotoDetailAdapter(getSupportFragmentManager(), list.size());
        pagerAdapter.setData(list);
        viewPager.setAdapter(pagerAdapter);
        if(position != -1) {
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void failedGetRestoImages() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                break;
        }
    }
}
