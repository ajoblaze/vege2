package com.imajiku.vegefinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.pojo.Resto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-09-26.
 */
public class RestoListAdapter extends RecyclerView.Adapter<RestoListAdapter.RestoListViewHolder> {
    private final RestoListListener listener;
    private ArrayList<Resto> list;
    private Context context;
    private String TAG = "exc";

    public static final int NORMAL = 1;
    public static final int FOOTER = 2;

    /**
     * empty adapter
     */
    public RestoListAdapter(Context context, RestoListListener listener) {
        this.context = context;
        this.listener = listener;
        list = new ArrayList<>();
    }

    /**
     * updates adapter with content
     */
    public void setData(ArrayList<Resto> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private boolean isPositionLast(int position){
        return position == list.size()-1;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionLast(position)){
            return FOOTER;
        }
        return NORMAL;
    }

    @Override
    public RestoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == NORMAL) {
            v = LayoutInflater.from(context).inflate(R.layout.item_resto, parent, false);
            return new RestoListViewHolder(v, false);
        }else{
            v = LayoutInflater.from(context).inflate(R.layout.item_resto_footer, parent, false);
            return new RestoListViewHolder(v, true);
        }
    }

    @Override
    public void onBindViewHolder(RestoListViewHolder holder, int position) {
        if(isPositionLast(position)){
            holder.loadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLoadMore();
                }
            });
        }else {
            Picasso.with(context)
                    .load(list.get(position).getImgPath())
                    .resize(90, 90)
                    .centerCrop()
                    .into(holder.image);
            holder.name.setText(list.get(position).getName());
            holder.distance.setText(list.get(position).getDistance() + " km dari lokasi Anda");
            holder.price.setText("start dari Rp" + list.get(position).getPrice());
            holder.ratingBar.setRating(list.get(position).getRating());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RestoListListener {
        void onLoadMore();
    }

    class RestoListViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name, distance, price;
        public RatingBar ratingBar;
        public Button loadMore;

        public RestoListViewHolder(View itemView, boolean isFooter) {
            super(itemView);
            if(isFooter){
                loadMore = (Button) itemView.findViewById(R.id.load_more);
            }else {
                image = (ImageView) itemView.findViewById(R.id.image);
                name = (TextView) itemView.findViewById(R.id.name);
                distance = (TextView) itemView.findViewById(R.id.distance);
                price = (TextView) itemView.findViewById(R.id.price);
                ratingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
            }
        }
    }
}
