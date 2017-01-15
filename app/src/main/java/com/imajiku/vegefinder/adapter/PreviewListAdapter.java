package com.imajiku.vegefinder.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.pojo.RestoPreview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-09-26.
 */
public class PreviewListAdapter extends RecyclerView.Adapter<PreviewListAdapter.PreviewListViewHolder> {
    private Typeface tf;
    private PreviewListListener listener;
    private ArrayList<RestoPreview> list;
    private Context context;
    private int width, height;
    private boolean isGrid;
    private String TAG = "exc";

    /**
     * empty adapter
     */
    public PreviewListAdapter(Context context, PreviewListListener listener, boolean isGrid) {
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
     * @param list
     */
    public void setData(ArrayList<RestoPreview> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public PreviewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if(isGrid){
            layout = R.layout.item_preview_grid;
        }else{
            layout = R.layout.item_preview_horizontal;
        }
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new PreviewListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PreviewListViewHolder holder, final int position) {
        final RestoPreview item = list.get(position);
        View itemView = holder.itemView;
        if(!item.getImage().equals("")) {
            Log.e(TAG, item.getImage());
            Picasso.with(context)
                    .load(item.getImage())
                    .resize(width, height)
                    .centerCrop()
                    .placeholder(R.drawable.empty_image)
                    .into(holder.img);
        }
        holder.title.setText(item.getTitle());
        holder.title.setTypeface(tf);
        if(!isGrid){
            holder.city.setText(item.getCity());
            holder.city.setTypeface(tf);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemTouch(item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setTypeface(Typeface typeface) {
        this.tf = typeface;
    }

    class PreviewListViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView title, city;

        public PreviewListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            if(isGrid) {
                img = (ImageView) itemView.findViewById(R.id.grid_img);
            }else{
                img = (ImageView) itemView.findViewById(R.id.img);
                city = (TextView) itemView.findViewById(R.id.city);
            }
        }
    }

    public interface PreviewListListener {
        void onItemTouch(int id);
    }
}
