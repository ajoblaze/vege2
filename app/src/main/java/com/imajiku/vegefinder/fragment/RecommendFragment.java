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
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.activity.RecommendActivity;
import com.imajiku.vegefinder.adapter.ImageListAdapter;

import java.util.ArrayList;

public class RecommendFragment extends Fragment implements ImageListAdapter.ImageListListener, View.OnClickListener {
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
        TextView seeMore = (TextView) v.findViewById(R.id.see_more);
        seeMore.setOnClickListener(this);
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
    }

    public void setData(ArrayList<String> list) {
        if(adapter!=null){
            adapter.setData(list);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.see_more:
//                startActivity(new Intent(getActivity(), RecommendActivity.class));
                break;
        }
    }

    public interface RecommendListener {
        void onRecommend(Uri uri);
    }

    public void populate(){
        list = new ArrayList<>();
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
    }
}
