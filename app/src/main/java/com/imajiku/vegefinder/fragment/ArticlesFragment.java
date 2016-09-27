package com.imajiku.vegefinder.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.ImageListAdapter;

import java.util.ArrayList;

public class ArticlesFragment extends Fragment implements ImageListAdapter.ImageListListener {
    private ArticlesListener mListener;
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private ArrayList<String> list;
    private String TAG="exc";

    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_articles, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager((Context) mListener, 2, LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        populate();
        adapter = new ImageListAdapter(getContext(), this, true);
        adapter.setImageSize(140, 140);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onArticles(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ArticlesListener) {
            mListener = (ArticlesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ArticlesListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onImageTouch(int position) {
        Log.e(TAG, "onImageTouch3: "+position);
    }

    public interface ArticlesListener {
        void onArticles(Uri uri);
    }

    public void populate(){
        list = new ArrayList<>();
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14440707_612818242230902_7714453475626010433_n.jpg?oh=9bcfa7bed71f137bc85a8886f5dd7945&oe=58737D01");
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14390726_1046468598784674_2383166123768680558_n.png?oh=9da464b6e470e20324ea2a79c25511be&oe=58644DBB");
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14358918_1276195212415169_911541256344026681_n.jpg?oh=a86758afbf2bf96f9f13756a739039dd&oe=58732952");
        list.add("https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/14233238_1262037247139601_4233367374367390354_n.jpg?oh=0848158c58810d234bd0dba45305b16c&oe=586DA98E");
    }
}
