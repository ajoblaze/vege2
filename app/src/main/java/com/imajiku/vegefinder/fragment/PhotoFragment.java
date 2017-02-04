package com.imajiku.vegefinder.fragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.activity.PhotoListActivity;
import com.imajiku.vegefinder.activity.RestoDetailActivity;
import com.imajiku.vegefinder.adapter.ImageListAdapter;
import com.imajiku.vegefinder.adapter.PreviewListAdapter;
import com.imajiku.vegefinder.pojo.RestoImage;

import java.util.ArrayList;

public class PhotoFragment extends Fragment implements View.OnClickListener, ImageListAdapter.ImageListListener {
    private PhotoListener mListener;
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private ArrayList<String> list;
    private String TAG="exc";
    private int restoId, userId;
    private TextView emptyPhoto;

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/VDS_New.ttf");
        TextView seeMore = (TextView) v.findViewById(R.id.see_more);
        seeMore.setOnClickListener(this);
        seeMore.setTypeface(tf);
        TextView label = (TextView) v.findViewById(R.id.photo_label);
        emptyPhoto = (TextView) v.findViewById(R.id.empty_photo);
        emptyPhoto.setTypeface(tf);
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
        adapter = new ImageListAdapter(getContext(), this);
//        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        return v;
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
    public void onItemTouch(int position) {
        if (mListener != null) {
            mListener.onPhoto(position);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.see_more: {
                Intent i = new Intent(getActivity(), PhotoListActivity.class);
                i.putExtra("restoId", restoId);
                startActivity(i);
            }
            break;
        }
    }

    public void setData(ArrayList<String> list, int restoId, int userId) {
        this.restoId = restoId;
        this.userId = userId;
        if(list.isEmpty()){
            emptyPhoto.setVisibility(View.VISIBLE);
        }else {
            setData(list);
        }
    }

    private void setData(ArrayList<String> list) {
        if(adapter!=null){
            adapter.setData(list);
        }
    }

    public interface PhotoListener {
        void onPhoto(int position);
    }

    public void populate(){
        list = new ArrayList<>();
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
        list.add("http://oregonaitc.org/wp-content/uploads/2016/02/potato.jpg");
    }
}
