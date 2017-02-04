package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.ImageListAdapter;
import com.imajiku.vegefinder.model.model.PhotoListModel;
import com.imajiku.vegefinder.model.presenter.PhotoListPresenter;
import com.imajiku.vegefinder.model.view.PhotoListView;
import com.imajiku.vegefinder.utility.CurrentUser;

import java.util.ArrayList;

public class PhotoListActivity extends AppCompatActivity implements ImageListAdapter.ImageListListener, PhotoListView {

    private static final String TAG = "exc";
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private PhotoListPresenter presenter;
    private int restoId, userId;
    private Typeface tf;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        presenter = new PhotoListPresenter(this);
        PhotoListModel model = new PhotoListModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");
        initToolbar(getResources().getString(R.string.photos));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        restoId = getIntent().getIntExtra("restoId", -1);
        userId = CurrentUser.getId(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new ImageListAdapter(this, this, true);
        recyclerView.setAdapter(adapter);

        presenter.getRestoImages(restoId, userId);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
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
    public void onItemTouch(int position) {
        Intent i = new Intent(this, PhotoDetailActivity.class);
        i.putExtra("restoId", restoId);
        i.putExtra("position", position);
        startActivity(i);
    }

    @Override
    public void successGetRestoImages(ArrayList<String> list, String title) {
        adapter.setData(list);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void failedGetRestoImages() {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(PhotoListActivity.this, "Failed getting images", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successAddPhoto(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(PhotoListActivity.this, "Image sent successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedAddPhoto(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(PhotoListActivity.this, "Failed sending image", Toast.LENGTH_SHORT).show();
    }
}
