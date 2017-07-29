package com.imajiku.vegefinder.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.imajiku.vegefinder.activity.RestoDetailActivity;
import com.imajiku.vegefinder.adapter.RestoListAdapter;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RestoListFragment extends Fragment implements RestoListAdapter.RestoListListener {
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

        adapter = new RestoListAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
        return v;
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
    public void onLoadMore() {
        mListener.onLoadMore();
    }

    @Override
    public void goToDetail(Resto r) {
        Intent i = new Intent(getActivity(), RestoDetailActivity.class);
        i.putExtra("restoId", r.getId());
        startActivity(i);
    }

    @Override
    public void changeBookmark(int restoId, boolean isBookmarked) {
        mListener.changeBookmark(restoId, isBookmarked);
    }

    @Override
    public void removeBeenHere(Resto r) {
        mListener.removeBeenHere(r);
    }

    /**
     * 0 = isHideFlag
     * 1 = isLoadMore
     * 2 = isBookmark
     * 3 = isBeenHere
     * */
    public void setData(ArrayList<Resto> list, boolean[] params, int src) {
        if(adapter!=null){
            params[1] = params[1] && !list.isEmpty(); // if list is empty, no need to show load more
            adapter.setData(list, params);
            Log.e(TAG, "src: "+src);
        }
    }

    public void updateBookmark(int placeId, boolean isBookmarked) {
        adapter.updateBookmark(placeId, isBookmarked);
    }

    public void removeData(int placeId) {
        adapter.removeData(placeId);
    }

    public interface RestoListListener {
        void onRestoList(Uri uri);

        void changeBookmark(int restoId, boolean isBookmarked);

        void removeBeenHere(Resto r);

        void toggleSpinner(boolean b);

        void onLoadMore();
    }
}
