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
import com.imajiku.vegefinder.activity.RestaurantActivity;
import com.imajiku.vegefinder.adapter.ImageListAdapter;
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
    public void onLoadMore() {

    }

    public void filter(boolean[] filterResult) {
        new RestoFilterTask(filterResult).execute();
    }

    public void sort(int[] sortResult) {
        new RestoSortTask(sortResult).execute();
    }

    private void reloadAdapterList(ArrayList<Resto> restoList) {
        if(adapter != null){
            adapter.setData(restoList);
        }
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

    /**
     * Filter the Resto list
     * parameter = boolean[] filterResult
     *
     * 0 => Open Now
     * 1 => Rated 8+
     * 2 => Bookmarked
     * 3 => Vegan
     * 4 => Been Here
     * 5 => Vegetarian
     */
    class RestoFilterTask extends AsyncTask<Void, Void, ArrayList<Resto>> {

        private final boolean[] filterResult;

        public RestoFilterTask(boolean[] filterResult) {
            this.filterResult = filterResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show loading spinner
        }

        @Override
        protected ArrayList<Resto> doInBackground(Void... params) {
            ArrayList<Resto> filteredList = new ArrayList<>();
            for(Resto r : list){
                if(filterResult[0]){
                    //cek if open now
                }
                if(filterResult[1]){
                    //cek if rated 8+
                    if(r.getRating() < 8){
                        continue;
                    }
                }
                if(filterResult[2]){
                    //cek if bookmarked
                }
                if(filterResult[3]){
                    //cek if vegan
                }
                if(filterResult[4]){
                    //cek if been here
                }
                if(filterResult[5]){
                    //cek if vegetarian
                }
                filteredList.add(r);
            }
            return filteredList;
        }

        @Override
        protected void onPostExecute(ArrayList<Resto> restoList) {
            super.onPostExecute(restoList);
            // hide loading spinner
            reloadAdapterList(restoList);
        }
    }

    /**
     * Sort the Resto list
     * parameter = int[] sortResult
     *
     * index 0 => type of sort
     * * 0 => by name (alphabetical)
     * * 1 => by distance
     * * 2 => by date
     * * 3 => by price
     * index 1 => order of sort
     * * 0 => ascending
     * * 1 => descending
     */
    class RestoSortTask extends AsyncTask<Void, Void, ArrayList<Resto>> {

        private final int[] sortResult;

        public RestoSortTask(int[] sortResult) {
            this.sortResult = sortResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show loading spinner
        }

        @Override
        protected ArrayList<Resto> doInBackground(Void... params) {
            ArrayList<Resto> sortedList = list;
            Collections.sort(sortedList, new Comparator<Resto>() {
                @Override
                public int compare(Resto r1, Resto r2) {
                    Resto a, b;
                    // order
                    if(sortResult[1] == 0){ // ascending
                        a = r1;
                        b = r2;
                    } else { // descending
                        a = r2;
                        b = r1;
                    }
                    // sort
                    if(sortResult[0] == 0) {
                        return a.getName().compareTo(b.getName());
                    } else if(sortResult[0] == 1) {
                        // compare distance
                    } else if(sortResult[0] == 2) {
                        // compare date
                    } else if(sortResult[0] == 3) {
                        // compare price
                    } else {
                        throw new IllegalArgumentException("Invalid Parameter : "+sortResult[0]);
                    }
                    return 0;
                }
            });
            return sortedList;
        }

        @Override
        protected void onPostExecute(ArrayList<Resto> restoList) {
            super.onPostExecute(restoList);
            // hide loading spinner
            reloadAdapterList(restoList);
        }
    }
}
