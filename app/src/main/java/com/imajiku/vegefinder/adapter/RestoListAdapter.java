package com.imajiku.vegefinder.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private Typeface tf;
    private ArrayList<Resto> list;
    private Context context;
    private String TAG = "exc";
    private boolean isSavedPlace;
    private boolean isBeenHere;

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
    public void setData(ArrayList<Resto> list, boolean isSavedPlace, boolean isBeenHere) {
        this.list = list;
        this.isSavedPlace = isSavedPlace;
        this.isBeenHere = isBeenHere;
        notifyDataSetChanged();
    }

    private boolean isPositionLast(int position) {
        return position == list.size() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionLast(position)) {
            return FOOTER;
        }
        return NORMAL;
    }

    @Override
    public RestoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == NORMAL) {
            v = LayoutInflater.from(context).inflate(R.layout.item_resto, parent, false);
            return new RestoListViewHolder(v, false);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false);
            return new RestoListViewHolder(v, true);
        }
    }

    private void changeFlag(Resto r, ImageView view) {
        if (isBeenHere) {
            view.setImageResource(R.drawable.ic_close_black_24dp_m);
        } else {
            if (r.isBookmarked()) {
                view.setImageResource(R.drawable.ic_bookmark_black_24dp_m);
            } else {
                view.setImageResource(R.drawable.ic_bookmark_border_black_24dp_m);
            }
        }
    }

    @Override
    public void onBindViewHolder(RestoListViewHolder holder, final int position) {
        View itemView = holder.itemView;

        if (isPositionLast(position)) {
            holder.loadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLoadMore();
                }
            });
        } else {
            final Resto r = list.get(position);
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.goToDetail(r);
                }
            });

            if (!r.getImage().isEmpty()) {
                Picasso.with(context)
                        .load(r.getImage())
                        .resize(90, 90)
                        .centerCrop()
                        .into(holder.image);
            }
            holder.name.setText(r.getTitle());
            holder.distance.setText(r.getDistance() + " ");
            holder.price.setText(" Rp " + r.getPriceStart());
            holder.rating.setText(r.getAverageRate());
            holder.review.setText("20");
            if (isSavedPlace) {
                holder.flagLayout.setVisibility(View.GONE);
            } else {
                holder.flagLayout.setVisibility(View.VISIBLE);
            }
            changeFlag(r, holder.flag);

            holder.flag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isBeenHere) {
                        listener.removeBeenHere(r);
                        list.remove(position);
                        notifyDataSetChanged();
                    } else {
                        r.setBookmarked(!r.isBookmarked());
                        listener.changeBookmark(r.getId());
                    }
                    changeFlag(r, (ImageView) view);
                }
            });
            holder.flag.setColorFilter(ContextCompat.getColor(context, R.color.accentGreenBtn));
//            holder.ratingBar.setRating(list.get(position).getRating());
            holder.name.setTypeface(tf);
            for (int i = 0; i < holder.restoLayouts.length; i++) {
                for (int ii = 0; ii < holder.restoLayouts[i].getChildCount(); ii++) {
                    ((TextView) holder.restoLayouts[i].getChildAt(ii)).setTypeface(tf);
                }
            }

            int color;
            if (position % 2 == 0) {
                color = ContextCompat.getColor(context, R.color.white);
            } else {
                color = ContextCompat.getColor(context, R.color.selectedGray);
            }
            itemView.setBackgroundColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setTypeface(Typeface typeface) {
        this.tf = typeface;
    }

    public interface RestoListListener {
        void onLoadMore();

        void goToDetail(Resto r);

        void changeBookmark(int restoId);

        void removeBeenHere(Resto r);
    }

    class RestoListViewHolder extends RecyclerView.ViewHolder {

        public ImageView image, flag;
        public TextView name, distance, price, rating, review;
        //        public RatingBar ratingBar;
        public Button loadMore;
        public LinearLayout itemLayout, flagLayout;
        public LinearLayout[] restoLayouts = new LinearLayout[3];

        public RestoListViewHolder(View itemView, boolean isFooter) {
            super(itemView);
            if (isFooter) {
                loadMore = (Button) itemView.findViewById(R.id.load_more);
            } else {
                image = (ImageView) itemView.findViewById(R.id.image);
                name = (TextView) itemView.findViewById(R.id.name);
                distance = (TextView) itemView.findViewById(R.id.distance);
                price = (TextView) itemView.findViewById(R.id.price);
                rating = (TextView) itemView.findViewById(R.id.rating);
                review = (TextView) itemView.findViewById(R.id.review);
                flag = (ImageView) itemView.findViewById(R.id.flag);
                itemLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
                flagLayout = (LinearLayout) itemView.findViewById(R.id.flag_layout);
                restoLayouts[0] = (LinearLayout) itemView.findViewById(R.id.distance_layout);
                restoLayouts[1] = (LinearLayout) itemView.findViewById(R.id.review_layout);
                restoLayouts[2] = (LinearLayout) itemView.findViewById(R.id.price_layout);
//                ratingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
            }
        }
    }
}
