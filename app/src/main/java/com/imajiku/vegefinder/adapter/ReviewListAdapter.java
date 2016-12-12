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
import com.imajiku.vegefinder.pojo.Review;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-09-26.
 */
public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder> {
    private Typeface tf;
    private ArrayList<Review> list;
    private Context context;
    private String now;
    private String TAG = "exc";

    /**
     * empty adapter
     */
    public ReviewListAdapter(Context context) {
        this.context = context;
        this.now = getNow();
        list = new ArrayList<>();
    }

    /**
     * updates adapter with content
     */
    public void setData(ArrayList<Review> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public String getNow(){
        LocalDateTime d2 = new LocalDateTime(new DateTime());
        return d2.toString("yyyy-MM-dd");
    }

    @Override
    public ReviewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewListViewHolder holder, int position) {
//        Picasso.with(context)
//                .load(list.get(position).getProfileImg())
//                .resize(36, 36)
//                .noFade()
//                .into(holder.img);
        holder.name.setText(list.get(position).getFirstName());
        holder.title.setText(list.get(position).getTitle());
        holder.date.setText(list.get(position).getDateDiff(now));
        holder.rate.setText(list.get(position).getRateString());
        holder.desc.setText(list.get(position).getComment());

        holder.name.setTypeface(tf);
        holder.title.setTypeface(tf);
        holder.date.setTypeface(tf);
        holder.rate.setTypeface(tf);
        holder.desc.setTypeface(tf);

        if(position == list.size()-1){
            holder.separator.setVisibility(View.GONE);
        }
    }

    public void setTypeface(Typeface typeface) {
        this.tf = typeface;
    }

    public interface ReviewListListener {
        void onLoadMore();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ReviewListViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView name, title, date, rate, desc;
        public View separator;

        public ReviewListViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.profile_image);
            name = (TextView) itemView.findViewById(R.id.name);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            rate = (TextView) itemView.findViewById(R.id.rate);
            desc = (TextView) itemView.findViewById(R.id.desc);
            separator = itemView.findViewById(R.id.separator);
        }
    }
}
