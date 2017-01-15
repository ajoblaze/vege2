package com.imajiku.vegefinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imajiku.vegefinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-09-26.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder> {
    private final int screenWidth;
    private boolean isGrid;
    private ImageListListener listener;
    private ArrayList<String> list;
    private Context context;
    private int width, height;
    private String TAG = "exc";

    public ImageListAdapter(Context context, ImageListListener listener) {
        this.context = context;
        this.listener = listener;
        this.width = this.height = 90;
        this.isGrid = false;
        list = new ArrayList<>();
        screenWidth = getScreenWidth(context);
    }

    public ImageListAdapter(Context context, ImageListListener listener, boolean isGrid) {
        this.context = context;
        this.listener = listener;
        this.isGrid = isGrid;
        list = new ArrayList<>();
        screenWidth = getScreenWidth(context);
        if(isGrid){
            this.width = this.height = screenWidth/3;
        }else {
            this.width = this.height = 90;
        }
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public void setImageSize(int width, int height) {
        this.width = width;
        this.height = height;
        notifyDataSetChanged();
    }

    /**
     * updates adapter with content
     * @param list
     */
    public void setData(ArrayList<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if(isGrid) {
            layout = R.layout.item_image_grid;
        } else {
            layout = R.layout.item_image_horizontal;
        }
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ImageListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageListViewHolder holder, final int position) {
        final String item = list.get(position);
        View itemView = holder.itemView;
        if(!item.equals("")) {
            Picasso.with(context)
                    .load(item)
                    .resize(width, height)
                    .centerCrop()
                    .placeholder(R.drawable.empty_image)
                    .into(holder.img);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemTouch(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ImageListViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;

        public ImageListViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public interface ImageListListener {
        void onItemTouch(int position);
    }
}
