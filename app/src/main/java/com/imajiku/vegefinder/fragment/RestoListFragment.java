package com.imajiku.vegefinder.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
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

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Sniglet-Regular.ttf");
        adapter = new RestoListAdapter(getContext(), this);
        adapter.setTypeface(tf);
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

    }

    @Override
    public void goToDetail(Resto r) {
        Intent i = new Intent(getActivity(), RestoDetailActivity.class);
        i.putExtra("placeId", r.getId());
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

    public void setData(ArrayList<Resto> list, boolean isSavedPlace) {
        if(adapter!=null){
            adapter.setData(list, isSavedPlace, false);
        }
    }

    public void filter(boolean[] filterResult, boolean isSavedPlace) {
        new RestoFilterTask(filterResult, isSavedPlace).execute();
    }

    public void sort(ArrayList<Resto> restoList, int[] sortResult, boolean isSavedPlace) {
        new RestoSortTask(restoList, sortResult, isSavedPlace).execute();
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
    }

    /**
     * Filter the Resto list
     * parameter = boolean[] filterResult
     *
     * 0 => Open Now
     * 1 => Rated 8+
     * 2 => Bookmarked
     * 3 => Been Here
     * 4 => Vegan
     * 5 => Vegetarian
     */
    class RestoFilterTask extends AsyncTask<Void, Void, ArrayList<Resto>> {

        private final boolean[] filterResult;
        private final boolean isSavedPlace;

        public RestoFilterTask(boolean[] filterResult, boolean isSavedPlace) {
            this.filterResult = filterResult;
            this.isSavedPlace = isSavedPlace;
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
            setData(restoList, isSavedPlace);
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
        private final boolean isSavedPlace;
        private ArrayList<Resto> sortedList;

        public RestoSortTask(ArrayList<Resto> list, int[] sortResult, boolean isSavedPlace) {
            this.sortResult = sortResult;
            sortedList = list;
            this.isSavedPlace = isSavedPlace;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show loading spinner
        }

        @Override
        protected ArrayList<Resto> doInBackground(Void... params) {
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
                        return a.getTitle().compareTo(b.getTitle());
                    } else if(sortResult[0] == 1) {
                        return Float.valueOf(a.getDistance()).compareTo(b.getDistance());
                    } else if(sortResult[0] == 2) {
                        return a.getDatePost().compareTo(b.getDatePost());
                    } else if(sortResult[0] == 3) {
                        return Integer.valueOf(a.getPriceStart()).compareTo(b.getPriceStart());
                    } else {
                        throw new IllegalArgumentException("Invalid Parameter : "+sortResult[0]);
                    }
                }
            });
            return sortedList;
        }

        @Override
        protected void onPostExecute(ArrayList<Resto> restoList) {
            super.onPostExecute(restoList);
            // hide loading spinner
            setData(restoList, isSavedPlace);
        }
    }
}
