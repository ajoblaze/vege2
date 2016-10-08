package com.imajiku.vegefinder.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.imajiku.vegefinder.activity.BrowseActivity;
import com.imajiku.vegefinder.activity.RestaurantActivity;
import com.imajiku.vegefinder.adapter.ImageListAdapter;
import com.imajiku.vegefinder.adapter.RestoListAdapter;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

public class RestoListFragment extends Fragment implements ImageListAdapter.ImageListListener, RestoListAdapter.RestoListListener {
    private RestoListListener mListener;
    private RecyclerView recyclerView;
    private RestoListAdapter adapter;
    private ArrayList<Resto> list;
    private String TAG="exc";

    public RestoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resto_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) mListener, LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        populate();
        adapter = new RestoListAdapter(getContext(), this);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onRestoList(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RestoListListener) {
            mListener = (RestoListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RestoListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onImageTouch(int position) {
        if(position == 1) {
            Intent i = new Intent(getActivity(), RestaurantActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onLoadMore() {

    }

    public interface RestoListListener {
        void onRestoList(Uri uri);
    }

    public void populate(){
        list = new ArrayList<>();
        list.add(new Resto("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14492410_1429600360387536_9172225207056049335_n.jpg?oh=b476b85a0a8019e9063060b998d6a8fb&oe=587AB0C5", "Shigure", (float)2, 100000));
        list.add(new Resto("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14523122_1430351520312420_4483740540657843801_n.jpg?oh=0e5794c25a59c346e6b25427d0b1f700&oe=5876AAEF", "Kongou", (float)4.8, 90000));
        list.add(new Resto("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14462724_1428972547116984_875808623472915473_n.jpg?oh=701f89b822678f9e072ecef053209505&oe=587ADA92", "Yahagi", 5, 120000));
        list.add(new Resto("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14463050_1427746467239592_4052477395478439910_n.jpg?oh=642e9939fee48bc9fd836330473940e4&oe=587C51DE", "U-511", (float)2.5, 80000));
        list.add(new Resto("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14517413_1427609660586606_4275927304837092098_n.jpg?oh=26c5b9994fbbe7aca54c46c892960ca6&oe=58655924", "Kaga Akagi", 5, 9999999));
        //footer
        list.add(new Resto());
    }
}
