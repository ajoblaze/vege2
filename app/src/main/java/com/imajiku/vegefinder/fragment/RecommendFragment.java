package com.imajiku.vegefinder.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.ImageListAdapter;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecommendFragment extends Fragment implements ImageListAdapter.ImageListListener {
    private RecommendListener mListener;
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private ArrayList<String> list;
    private String TAG="exc";

    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) mListener, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        populate();
        adapter = new ImageListAdapter(getContext(), this, false);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onRecommend(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecommendListener) {
            mListener = (RecommendListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RecommendListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onImageTouch(int position) {
//        String d0 = "2016-09-28 21:55:55";
//        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//        DateTime jodatime = dtf.parseDateTime(d0);
//        LocalDateTime d2 = new LocalDateTime(new DateTime());
//        LocalDateTime d1 = new LocalDateTime(jodatime);
//        int seconds = Seconds.secondsBetween(d1, d2).getSeconds() % 60;
//        int minutes = Minutes.minutesBetween(d1, d2).getMinutes() % 60;
//        int hours = Hours.hoursBetween(d1, d2).getHours() % 24;
//        int days = Days.daysBetween(d1, d2).getDays();
//        Log.e(TAG, "now: "+d2.toString("yyyy-MM-dd HH:mm:ss") + " " + days + " " + hours + " " + minutes + " " + seconds);
    }

    public interface RecommendListener {
        void onRecommend(Uri uri);
    }

    public void populate(){
        list = new ArrayList<>();
        list.add("http://i.imgur.com/DvpvklR.png");
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14479524_1130535670315230_6310861083410001029_n.jpg?oh=f831fb6b9393d7d7a855ea30d0a9cdcb&oe=5884F8B3");
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-0/s526x395/14457356_147592955697506_7082025653667303706_n.jpg?oh=083cda12082001e881e89e9bc2c9af86&oe=58672C1F");
    }
}
