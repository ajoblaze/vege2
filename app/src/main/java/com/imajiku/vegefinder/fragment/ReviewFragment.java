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
import android.widget.TextView;

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

public class ReviewFragment extends Fragment implements View.OnClickListener {
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
        TextView seeMore = (TextView) v.findViewById(R.id.see_more);
        seeMore.setOnClickListener(this);

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
//        if(list == null) {
//            populate();
//        }
        adapter = new ReviewListAdapter(getContext(), now);
//        adapter.setData(list);
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
//                Intent i = new Intent(getActivity(), RestoListActivity.class);
//                i.putExtra("page", RestoListActivity.PAGE_SAVED);
//                startActivity(i);
            }
            break;
        }
    }

    public interface ReviewListener {
        void onReview(Uri uri);
    }
    
    public static String getNow(){
        LocalDateTime d2 = new LocalDateTime(new DateTime());
        return d2.toString("yyyy-MM-dd");
    }
}
