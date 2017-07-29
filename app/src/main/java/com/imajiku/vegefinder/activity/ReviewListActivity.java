package com.imajiku.vegefinder.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.ReviewListFragment;
import com.imajiku.vegefinder.model.model.ReviewListModel;
import com.imajiku.vegefinder.model.presenter.ReviewListPresenter;
import com.imajiku.vegefinder.model.view.ReviewListView;
import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.imajiku.vegefinder.utility.Utility;

public class ReviewListActivity extends AppCompatActivity implements ReviewListView {

    private int restoId, userId;
    private ReviewListPresenter presenter;
    private ReviewListFragment reviewListFragment;
    private Typeface tf;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        restoId = getIntent().getIntExtra("restoId", -1);
        userId = CurrentUser.getId(this);
//        if(restoId == -1 || userId == -1){
//            throw new RuntimeException("send userid and placeid");
//        }

        presenter = new ReviewListPresenter(this);
        ReviewListModel model = new ReviewListModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), Utility.regFont);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        initToolbar(getString(R.string.title_review));

        reviewListFragment = (ReviewListFragment) getSupportFragmentManager().findFragmentById(R.id.review_list_fragment);

        progressBar.setVisibility(View.VISIBLE);
        presenter.getRestoReviews(restoId, userId);
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
    public void successGetRestoReviews(RestoDetail restoDetail) {
        progressBar.setVisibility(View.INVISIBLE);
        reviewListFragment.setData(restoDetail.getRatesReview());
    }

    @Override
    public void failedGetRestoReviews() {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(ReviewListActivity.this, "Failed get reviews", Toast.LENGTH_SHORT).show();
    }
}
