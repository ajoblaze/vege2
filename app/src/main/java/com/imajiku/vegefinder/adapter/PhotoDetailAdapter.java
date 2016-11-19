package com.imajiku.vegefinder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.imajiku.vegefinder.fragment.PhotoDetailFragment;

import java.util.ArrayList;

/**
 * Created by SRIN on 8/25/2015.
 */
public class PhotoDetailAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> list;
    private int length;

    public PhotoDetailAdapter(FragmentManager fm, int length) {
        super(fm);
        this.length = length;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoDetailFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return length;
    }

    public void setData(ArrayList<String> list) {
        this.list = list;
    }
}
