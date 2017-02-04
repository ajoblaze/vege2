package com.imajiku.vegefinder.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import com.imajiku.vegefinder.activity.RestoListActivity;
import com.imajiku.vegefinder.adapter.PreviewListAdapter;
import com.imajiku.vegefinder.pojo.RestoPreview;

import java.util.ArrayList;

public class RecommendFragment extends Fragment implements PreviewListAdapter.PreviewListListener, View.OnClickListener {
    private RecommendListener mListener;
    private RecyclerView recyclerView;
    private PreviewListAdapter adapter;
    private ArrayList<String> list;
    private String TAG="exc";

    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/VDS_New.ttf");
        TextView seeMore = (TextView) v.findViewById(R.id.see_more);
        seeMore.setOnClickListener(this);
        seeMore.setTypeface(tf);
        TextView label = (TextView) v.findViewById(R.id.recommend_label);
        label.setOnClickListener(this);
        label.setTypeface(tf);

        //Tint Drawable
        Drawable addIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_add_circle_black_24dp_m);
        addIcon = DrawableCompat.wrap(addIcon);
        int btnGreen = ContextCompat.getColor(getContext(), R.color.accentGreenBtn);
        DrawableCompat.setTint(addIcon, btnGreen);
        seeMore.setCompoundDrawablesWithIntrinsicBounds(null, null, addIcon, null);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) mListener, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
//        populate();
        adapter = new PreviewListAdapter(getContext(), this, false);
        adapter.setImageSize(120, 90);
        adapter.setTypeface(tf);
//        adapter.setData(list);
        recyclerView.setAdapter(adapter);

        return v;
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
    public void onItemTouch(int id) {
        if (mListener != null) {
            mListener.onRecommend(id);
        }
    }

    public void setData(ArrayList<RestoPreview> list) {
        if(adapter!=null){
            adapter.setData(list);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.see_more: {
                Intent i = new Intent(getActivity(), RestoListActivity.class);
                i.putExtra("page", RestoListActivity.PAGE_RECOMMEND);
                startActivity(i);
            }
                break;
        }
    }

    public interface RecommendListener {
        void onRecommend(int uri);
    }

    public void populate(){
        list = new ArrayList<>();
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
    }
}
