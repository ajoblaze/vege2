package com.imajiku.vegefinder.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.imajiku.vegefinder.R;

import java.util.ArrayList;


/**
 * Created by SRIN on 8/26/2015.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private Typeface tf;
    private ArrayList<String> list;
    private int hiddenIndex;
    private Context mContext;
    private int mResource;

    public SpinnerAdapter(Context context, int resource, ArrayList<String> list, Typeface tf) {
        super(context, resource, list);
        mContext = context;
        mResource = resource;
        this.list = list;
        this.tf = tf;
        hiddenIndex = -1;
    }

    public SpinnerAdapter(Context context, int resource, ArrayList<String> list, Typeface tf, int hiddenIndex) {
        super(context, resource, list);
        mContext = context;
        mResource = resource;
        this.list = list;
        this.tf = tf;
        this.hiddenIndex = hiddenIndex;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        MenuHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(mResource, parent, false);
        holder = new MenuHolder(v);

        holder.menuText.setText(list.get(position));
        holder.menuText.setTypeface(tf);

        if (position == hiddenIndex) {
            holder.menuText.setHeight(0);
            holder.menuText.setWidth(0);
            holder.menuText.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(tf);
        return view;
    }

    static class MenuHolder {

        private final TextView menuText;

        public MenuHolder(View v) {
            this.menuText = (TextView) v.findViewById(R.id.spinner_item);
        }
    }
}