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
import com.imajiku.vegefinder.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alvin on 2016-09-26.
 */
public class RestoListAdapter extends RecyclerView.Adapter<RestoListAdapter.RestoListViewHolder> {
    private final RestoListListener listener;
    private Typeface tf, tfBold;
    private ArrayList<Resto> list;
    private Context context;
    private String TAG = "exc";
    private boolean isHideFlag;
    private boolean isBookmark;
    private boolean isBeenHere;
    private boolean isLoadMore;

    public static final int NORMAL = 1;
    public static final int FOOTER = 2;

    /**
     * empty adapter
     */
    public RestoListAdapter(Context context, RestoListListener listener) {
        this.context = context;
        this.listener = listener;
        tf = Typeface.createFromAsset(context.getAssets(), Utility.regFont);
        tfBold = Typeface.createFromAsset(context.getAssets(), Utility.boldFont);
        list = new ArrayList<>();
    }

    /**
     * updates adapter with content
     *
     * params
     * 0 = isHideFlag
     * 1 = isLoadMore
     * 2 = isBookmark
     * 3 = isBeenHere
     * */
    public void setData(ArrayList<Resto> list, boolean[] params) {
        this.list = list;
        this.isHideFlag = params[0];
        this.isLoadMore = params[1];
        this.isBookmark = params[2];
        this.isBeenHere = params[3];
        if(isLoadMore) {
            list.add(new Resto());
        }
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
            holder.loadMore.setTypeface(tfBold);
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

            if (!r.getImageUrl().isEmpty()) {
                Picasso.with(context)
                        .load(r.getImageUrl())
                        .resize(90, 90)
                        .centerCrop()
                        .placeholder(R.drawable.empty_image)
                        .into(holder.image);
            }
            holder.name.setText(r.getMetaTitle());
            holder.distance.setText(r.getDistanceStr());

            //hides distance to user
//            if(isBookmark || isBeenHere){
//                holder.restoLayouts[0].setVisibility(View.GONE);
//            }

            String rating = r.getRating();
            int review = r.getReview();

            if(review != 0 && rating != null && !rating.isEmpty()){
                holder.restoLayouts[1].setVisibility(View.VISIBLE);
                holder.rating.setText(r.getRating());
                holder.review.setText(r.getReview() + "");
            }
            holder.restoLayouts[2].setVisibility(View.VISIBLE);
            holder.price.setText(" Rp " + r.getAverageCost());

            if (isHideFlag) {
                holder.flagLayout.setVisibility(View.GONE);
            } else {
                holder.flagLayout.setVisibility(View.VISIBLE);
                changeFlag(r, holder.flag);
            }

            holder.flag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isBeenHere) {
                        listener.removeBeenHere(r);
                        list.remove(position);
                        notifyDataSetChanged();
                    } else {
                        r.setBookmarked(!r.isBookmarked());
                        listener.changeBookmark(r.getId(), r.isBookmarked());
                    }
                }
            });
            holder.flag.setColorFilter(ContextCompat.getColor(context, R.color.accentGreenBtn));
//            holder.ratingBar.setRating(list.get(position).getRating());
            holder.name.setTypeface(tfBold);
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

    public void updateBookmark(int placeId, boolean isBookmarked) {
        for(Resto r : list){
            if(r.getId() == placeId && r.isBookmarked() != isBookmarked){
                r.setBookmarked(isBookmarked);
            }
            notifyDataSetChanged();
        }
    }

    public void removeData(int placeId) {
        Iterator<Resto> iterator = list.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getId() == placeId){
                iterator.remove();
            }
        }
        notifyDataSetChanged();
    }

    public interface RestoListListener {
        void onLoadMore();

        void goToDetail(Resto r);

        void changeBookmark(int restoId, boolean isBookmarked);

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
                if(isLoadMore && !list.isEmpty()) {
                    loadMore.setVisibility(View.VISIBLE);
                }
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
