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
import java.util.List;

/**
 * Created by Alvin on 2016-12-04.
 */
public class ListAdapter extends ArrayAdapter<String> {

    private Typeface tf;
    private Context mContext;
    private List<String> mList;
    private int mResource;

    public ListAdapter(Context context, int resource, List<String> list, Typeface tf) {
        super(context, resource, list);
        mContext = context;
        mResource = resource;
        mList = list;
        this.tf = tf;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavigationMenuHolder holder;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(mResource, parent, false);
            holder = new NavigationMenuHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (NavigationMenuHolder) convertView.getTag();
        }

        holder.menuText.setText(mList.get(position));
        holder.menuText.setTypeface(tf);

        return convertView;
    }

    public void setData(ArrayList<String> list){
        mList = list;
        notifyDataSetChanged();
    }

    class NavigationMenuHolder {
        private TextView menuText;

        public NavigationMenuHolder(View v) {
            this.menuText = (TextView) v.findViewById(R.id.phone);
        }
    }
}
