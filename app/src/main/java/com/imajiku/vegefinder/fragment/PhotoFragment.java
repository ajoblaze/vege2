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
import com.imajiku.vegefinder.activity.RestaurantActivity;
import com.imajiku.vegefinder.adapter.ImageListAdapter;

import java.util.ArrayList;

public class PhotoFragment extends Fragment implements ImageListAdapter.ImageListListener {
    private PhotoListener mListener;
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private ArrayList<String> list;
    private String TAG="exc";

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
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
            mListener.onPhoto(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PhotoListener) {
            mListener = (PhotoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PhotoListener");
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

    public interface PhotoListener {
        void onPhoto(Uri uri);
    }

    public void populate(){
        list = new ArrayList<>();
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-0/s526x395/14433204_1148221801930029_8539085987530312700_n.jpg?oh=5bbc179a196690183b2ab21a368c8477&oe=587AC9D5");
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14462931_1839981636236993_624893567931667219_n.jpg?oh=3078bb436695c35a2c2047ec56438541&oe=587008FD");
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14502685_1863455243885009_5590750929699740658_n.jpg?oh=68c64b65e69351d7d7903a3eedd6e6e6&oe=58ADE355");
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-0/s526x395/14485039_1099652940111095_3170367858410649794_n.jpg?oh=a9e0f97c78e458255d2b965932b3f946&oe=58661893");
    }
}
