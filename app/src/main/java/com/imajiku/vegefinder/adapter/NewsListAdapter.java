package com.imajiku.vegefinder.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.pojo.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-09-26.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {
    private final NewsListListener listener;
    private Typeface tf;
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
        } else {
            final News n = list.get(position);
            Picasso.with(context)
                    .load(n.getImage())
                    .resize(90, 90)
                    .centerCrop()
                    .into(holder.image);
            holder.title.setText(n.getTitle());
            holder.content.setText(n.getContent());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onReadMore(n);
                }
            });
            holder.title.setTypeface(tf);
            holder.content.setTypeface(tf);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setTypeface(Typeface typeface) {
        this.tf = typeface;
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
