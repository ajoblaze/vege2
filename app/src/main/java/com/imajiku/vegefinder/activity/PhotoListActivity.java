package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.ImageListAdapter;
import com.imajiku.vegefinder.model.model.PhotoListModel;
import com.imajiku.vegefinder.model.presenter.PhotoListPresenter;
import com.imajiku.vegefinder.model.view.PhotoListView;
import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.pojo.RestoImage;

import java.util.ArrayList;

public class PhotoListActivity extends AppCompatActivity implements ImageListAdapter.ImageListListener, PhotoListView {

    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private PhotoListPresenter presenter;
    private int restoId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        presenter = new PhotoListPresenter(this);
        PhotoListModel model = new PhotoListModel(presenter);
        presenter.setModel(model);

        restoId = getIntent().getIntExtra("restoId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new ImageListAdapter(this, this);
        recyclerView.setAdapter(adapter);

        presenter.getRestoImages(restoId, userId);
    }

    @Override
    public void onItemTouch(int position) {
        Intent i = new Intent(this, PhotoDetailActivity.class);
        i.putExtra("restoId", restoId);
        i.putExtra("userId", userId);
        i.putExtra("position", position);
        startActivity(i);
    }

    @Override
    public void successGetRestoImages(ArrayList<String> list) {
        adapter.setData(list);
    }

    @Override
    public void failedGetRestoImages() {

    }
}
