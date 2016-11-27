package com.imajiku.vegefinder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.ReviewListFragment;
import com.imajiku.vegefinder.model.model.ReviewListModel;
import com.imajiku.vegefinder.model.presenter.ReviewListPresenter;
import com.imajiku.vegefinder.model.view.ReviewListView;
import com.imajiku.vegefinder.pojo.Review;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity implements ReviewListView {

    private int restoId, userId;
    private ReviewListPresenter presenter;
    private ReviewListFragment reviewListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        restoId = getIntent().getIntExtra("placeId", -1);
        userId = getIntent().getIntExtra("userId", -1);
        if(restoId == -1 || userId == -1){
            throw new RuntimeException("send userid and placeid");
        }

        presenter = new ReviewListPresenter(this);
        ReviewListModel model = new ReviewListModel(presenter);
        presenter.setModel(model);

        reviewListFragment = (ReviewListFragment) getSupportFragmentManager().findFragmentById(R.id.review_list_fragment);

        presenter.getRestoReviews(restoId, userId);
    }

    @Override
    public void successGetRestoReviews(ArrayList<Review> list) {

    }

    @Override
    public void failedGetRestoReviews() {

    }
}
