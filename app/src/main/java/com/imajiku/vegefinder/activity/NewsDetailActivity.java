package com.imajiku.vegefinder.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.pojo.News;

import uk.co.deanwild.flowtextview.FlowTextView;

public class NewsDetailActivity extends AppCompatActivity {

    private ImageView image;
    private FlowTextView flowContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        News n = (News) getIntent().getSerializableExtra("news");
        initToolbar(n.getTitle());
        image = (ImageView) findViewById(R.id.news_image);
        flowContent = (FlowTextView) findViewById(R.id.flow_news_content);
//        Picasso.with(this)
//                .load(n.getImage())
//                .resize(90, 90)
//                .centerCrop()
//                .into(image);
        Spanned html;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html = Html.fromHtml(n.getContent(), Html.FROM_HTML_MODE_LEGACY);
        } else {
            html = Html.fromHtml(n.getContent());
        }
        flowContent.setText(html);
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
    }
}