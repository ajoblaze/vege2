package com.imajiku.vegefinder.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.ReviewListFragment;
import com.imajiku.vegefinder.model.model.ReviewListModel;
import com.imajiku.vegefinder.model.presenter.ReviewListPresenter;
import com.imajiku.vegefinder.model.view.ReviewListView;
import com.imajiku.vegefinder.pojo.RestoDetail;
import com.imajiku.vegefinder.utility.CurrentUser;

public class ReviewListActivity extends AppCompatActivity implements ReviewListView {

    private int restoId, userId;
    private ReviewListPresenter presenter;
    private ReviewListFragment reviewListFragment;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        restoId = getIntent().getIntExtra("placeId", -1);
        userId = CurrentUser.getId();
//        if(restoId == -1 || userId == -1){
//            throw new RuntimeException("send userid and placeid");
//        }

        presenter = new ReviewListPresenter(this);
        ReviewListModel model = new ReviewListModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        initToolbar(getString(R.string.title_review));

        reviewListFragment = (ReviewListFragment) getSupportFragmentManager().findFragmentById(R.id.review_list_fragment);

        presenter.getRestoReviews(restoId, userId);
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    @Override
    public void successGetRestoReviews(RestoDetail restoDetail) {
        reviewListFragment.setData(restoDetail.getRatesReview());
    }

    @Override
    public void failedGetRestoReviews() {

    }
}
