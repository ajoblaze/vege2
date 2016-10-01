package com.imajiku.vegefinder.fragment;

import android.content.Context;
import android.net.Uri;
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

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReviewFragment extends Fragment {
    private ReviewListener mListener;
    private RecyclerView recyclerView;
    private ReviewListAdapter adapter;
    private ArrayList<Review> list;
    private String now;
    private String TAG="exc";

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);
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
        now = getNow();
        if(list == null) {
            populate();
        }
        adapter = new ReviewListAdapter(getContext(), now);
        adapter.setData(list);
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

    public interface ReviewListener {
        void onReview(Uri uri);
    }
    
    public static String getNow(){
        LocalDateTime d2 = new LocalDateTime(new DateTime());
        return d2.toString("yyyy-MM-dd HH:mm:ss");
    }

    public void populate(){
        list = new ArrayList<>();
        list.add(new Review("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14390955_1149333328485543_6086096477922428324_n.jpg?oh=0ad3720d056a840f1070ee16542603ed&oe=58672AF9", "AJ", "Dev", now, "4", "gud"));
        list.add(new Review("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-0/s526x395/14391015_1426439520703620_4072477585853414696_n.jpg?oh=40d1dee22863dc7027e4eae45a4a3109&oe=586A5D28", "AJ2", "Dev2", now, "2", "meh"));
    }
}
