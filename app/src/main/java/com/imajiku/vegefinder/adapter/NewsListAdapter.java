package com.imajiku.vegefinder.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-09-26.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {
    private final NewsListListener listener;
    private Typeface tf, tfBold, tfThin;
    private ArrayList<News> list;
    private Context context;
    private String TAG = "exc";
    private boolean isSavedPlace;

    public static final int NORMAL = 1;
    public static final int FOOTER = 2;

    /**
     * empty adapter
     */
    public NewsListAdapter(Context context, NewsListListener listener) {
        this.context = context;
        this.listener = listener;
        tf = Typeface.createFromAsset(context.getAssets(), Utility.regFont);
        tfBold = Typeface.createFromAsset(context.getAssets(), Utility.boldFont);
        tfThin = Typeface.createFromAsset(context.getAssets(), Utility.thinFont);
        list = new ArrayList<>();
    }

    /**
     * updates adapter with content
     */
    public void setData(ArrayList<News> list) {
        this.list = list;
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
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == NORMAL) {
            v = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
            return new NewsListViewHolder(v, false);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false);
            return new NewsListViewHolder(v, true);
        }
    }

    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {
        View itemView = holder.itemView;
        if (isPositionLast(position)) {
            holder.loadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLoadMore();
                }
            });
            holder.loadMore.setTypeface(tf);
        } else {
            final News n = list.get(position);
            if(!n.getImageUrl().isEmpty()) {
                Picasso.with(context)
                        .load(n.getImageUrl())
                        .resize(90, 90)
                        .centerCrop()
                        .placeholder(R.drawable.empty_image)
                        .into(holder.image);
            }
            holder.title.setText(n.getTitle());

            Spanned html;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                html = Html.fromHtml(n.getContent(), Html.FROM_HTML_MODE_LEGACY);
            } else {
                html = Html.fromHtml(n.getContent());
            }
            holder.content.setText(html);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onReadMore(n);
                }
            });

            int color;
            if (position % 2 == 0) {
                color = ContextCompat.getColor(context, R.color.white);
            } else {
                color = ContextCompat.getColor(context, R.color.selectedGray);
            }
            itemView.setBackgroundColor(color);

            holder.title.setTypeface(tfBold);
            holder.content.setTypeface(tf);
            holder.readMore.setTypeface(tfThin);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface NewsListListener {
        void onLoadMore();

        void onReadMore(News n);
    }

    class NewsListViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title, content, readMore;
        public Button loadMore;

        public NewsListViewHolder(View itemView, boolean isFooter) {
            super(itemView);
            if (isFooter) {
                loadMore = (Button) itemView.findViewById(R.id.load_more);
            } else {
                image = (ImageView) itemView.findViewById(R.id.image);
                title = (TextView) itemView.findViewById(R.id.title);
                content = (TextView) itemView.findViewById(R.id.content);
                readMore = (TextView) itemView.findViewById(R.id.read_more);
            }
        }
    }
}
