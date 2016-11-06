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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.activity.NewsDetailActivity;
import com.imajiku.vegefinder.adapter.NewsListAdapter;
import com.imajiku.vegefinder.pojo.News;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NewsListFragment extends Fragment implements NewsListAdapter.NewsListListener {
    private NewsListListener mListener;
    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private ArrayList<News> list;
    private String TAG="exc";

    public NewsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) mListener, LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new NewsListAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewsListListener) {
            mListener = (NewsListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NewsListListener");
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
    public void onReadMore(News n) {
        Intent i = new Intent(getActivity(), NewsDetailActivity.class);
        i.putExtra("news", n);
        startActivity(i);
    }

    public void sort(ArrayList<News> NewsList, int[] sortResult) {
        new NewsSortTask(NewsList, sortResult).execute();
    }

    private void setData(ArrayList<News> NewsList) {
        if(adapter != null){
            adapter.setData(NewsList);
        }
    }

    public interface NewsListListener {
        void onNewsList(Uri uri);
    }

    /**
     * Filter the News list
     * parameter = boolean[] filterResult
     *
     * 0 => Open Now
     * 1 => Rated 8+
     * 2 => Bookmarked
     * 3 => Been Here
     * 4 => Vegan
     * 5 => Vegetarian
     */
    class NewsFilterTask extends AsyncTask<Void, Void, ArrayList<News>> {

        private final boolean[] filterResult;

        public NewsFilterTask(boolean[] filterResult) {
            this.filterResult = filterResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show loading spinner
        }

        @Override
        protected ArrayList<News> doInBackground(Void... params) {
            ArrayList<News> filteredList = new ArrayList<>();
            for(News r : list){
                if(filterResult[0]){
                    //cek if open now
                }
                if(filterResult[1]){
                    //cek if rated 8+
//                    if(r.getRating() < 8){
//                        continue;
//                    }
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
        protected void onPostExecute(ArrayList<News> NewsList) {
            super.onPostExecute(NewsList);
            // hide loading spinner
            setData(NewsList);
        }
    }

    /**
     * Sort the News list
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
    class NewsSortTask extends AsyncTask<Void, Void, ArrayList<News>> {

        private final int[] sortResult;
        private ArrayList<News> sortedList;

        public NewsSortTask(ArrayList<News> list, int[] sortResult) {
            this.sortResult = sortResult;
            sortedList = list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show loading spinner
        }

        @Override
        protected ArrayList<News> doInBackground(Void... params) {
            Collections.sort(sortedList, new Comparator<News>() {
                @Override
                public int compare(News r1, News r2) {
                    News a, b;
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
//                        return Float.valueOf(a.getDistance()).compareTo(b.getDistance());
                    } else if(sortResult[0] == 2) {
                        return a.getDatePost().compareTo(b.getDatePost());
                    } else if(sortResult[0] == 3) {
//                        return Integer.valueOf(a.getPriceStart()).compareTo(b.getPriceStart());
                    } else {
                        throw new IllegalArgumentException("Invalid Parameter : "+sortResult[0]);
                    }
                    return 0;
                }
            });
            return sortedList;
        }

        @Override
        protected void onPostExecute(ArrayList<News> NewsList) {
            super.onPostExecute(NewsList);
            // hide loading spinner
            setData(NewsList);
        }
    }
}
