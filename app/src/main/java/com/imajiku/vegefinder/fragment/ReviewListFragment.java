package com.imajiku.vegefinder.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.ReviewListAdapter;
import com.imajiku.vegefinder.pojo.Review;

import java.util.ArrayList;

public class ReviewListFragment extends Fragment implements ReviewListAdapter.ReviewListListener {
    private RecyclerView recyclerView;
    private ReviewListAdapter adapter;
    private String TAG="exc";

    public ReviewListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/VDS_New.ttf");
        adapter = new ReviewListAdapter(getContext());
        adapter.setTypeface(tf);
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLoadMore() {

    }

    public void setData(ArrayList<Review> reviewList) {
        if(adapter != null){
            adapter.setData(reviewList);
        }
    }
}
