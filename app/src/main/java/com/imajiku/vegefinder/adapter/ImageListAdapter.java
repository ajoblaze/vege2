package com.imajiku.vegefinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
    private ImageListListener listener;
    private ArrayList<String> list;
    private Context context;
    private int width, height;
    private boolean isGrid;
    private String TAG = "exc";

    /**
     * empty adapter
     */
    public ImageListAdapter(Context context, ImageListListener listener, boolean isGrid) {
        this.context = context;
        this.listener = listener;
        this.width = 90;
        this.height = 90;
        this.isGrid = isGrid;
        list = new ArrayList<>();
    }

    public void setImageSize(int width, int height) {
        this.width = width;
        this.height = height;
        notifyDataSetChanged();
    }

    /**
     * updates adapter with content
     */
    public void setData(ArrayList<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if(isGrid){
            layout = R.layout.item_grid;
        }else{
            layout = R.layout.item_horizontal;
        }
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ImageListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageListViewHolder holder, final int position) {
        Picasso.with(context)
                .load(list.get(position))
                .resize(width, height)
                .centerCrop()
                .into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageTouch(position);
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
            if(isGrid) {
                img = (ImageView) itemView.findViewById(R.id.grid_img);
            }else{
                img = (ImageView) itemView.findViewById(R.id.img);
            }
        }
    }

    public interface ImageListListener {
        void onImageTouch(int position);
    }
}
