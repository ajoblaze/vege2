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
        list.add(new Resto("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg", "Potato1", (float)2, 100000));
        list.add(new Resto("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg", "Potato2", (float)4.8, 90000));
        list.add(new Resto("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg", "Potato3", 5, 120000));
        list.add(new Resto("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg", "Potato4", (float)2.5, 80000));
        list.add(new Resto("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg", "Potato5", 5, 9999999));
        //footer
        list.add(new Resto());
    }
}
