package com.imajiku.vegefinder.fragment;

import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.activity.ReviewListActivity;
import com.imajiku.vegefinder.adapter.ReviewListAdapter;
import com.imajiku.vegefinder.pojo.Review;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReviewFragment extends Fragment implements View.OnClickListener {
    private ReviewListener mListener;
    private RecyclerView recyclerView;
    private ReviewListAdapter adapter;
    private ArrayList<Review> list;
    private String TAG="exc";
    private int restoId, userId;
    private TextView emptyReview;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/VDS_New.ttf");
        TextView seeMore = (TextView) v.findViewById(R.id.see_more);
        seeMore.setOnClickListener(this);
        seeMore.setTypeface(tf);
        TextView label = (TextView) v.findViewById(R.id.review_label);
        emptyReview = (TextView) v.findViewById(R.id.empty_review);
        emptyReview.setTypeface(tf);
        label.setOnClickListener(this);
        label.setTypeface(tf);

        //Tint Drawable
        Drawable addIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_add_circle_black_24dp_m);
        addIcon = DrawableCompat.wrap(addIcon);
        int btnGreen = ContextCompat.getColor(getContext(), R.color.accentGreenBtn);
        DrawableCompat.setTint(addIcon, btnGreen);
        seeMore.setCompoundDrawablesWithIntrinsicBounds(null, null, addIcon, null);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) mListener, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new ReviewListAdapter(getContext());
        adapter.setTypeface(tf);
        recyclerView.setAdapter(adapter);
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onReview(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReviewListener) {
            mListener = (ReviewListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReviewListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.see_more: {
                Intent i = new Intent(getActivity(), ReviewListActivity.class);
                i.putExtra("restoId", restoId);
                startActivity(i);
            }
            break;
        }
    }

    public void setData(ArrayList<Review> list, int restoId) {
        this.restoId = restoId;
        if(list.isEmpty()){
            emptyReview.setVisibility(View.VISIBLE);
        }else {
            setData(list);
        }
    }

    private void setData(ArrayList<Review> list) {
        if(adapter!=null){
            adapter.setData(list);
        }
    }

    public interface ReviewListener {
        void onReview(Uri uri);
    }
}
