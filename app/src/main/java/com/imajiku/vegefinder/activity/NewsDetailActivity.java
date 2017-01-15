package com.imajiku.vegefinder.activity;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.pojo.News;
import com.squareup.picasso.Picasso;

import uk.co.deanwild.flowtextview.FlowTextView;

public class NewsDetailActivity extends AppCompatActivity {

    private ImageView image;
    private FlowTextView flowContent;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        News n = (News) getIntent().getSerializableExtra("news");

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        initToolbar(n.getTitle());
        image = (ImageView) findViewById(R.id.news_image);
        flowContent = (FlowTextView) findViewById(R.id.flow_news_content);
        if(!n.getImageUrl().isEmpty()) {
            Picasso.with(this)
                    .load(n.getImageUrl())
                    .resize(90, 90)
                    .centerCrop()
                    .placeholder(R.drawable.empty_image)
                    .into(image);
        }
        Spanned html;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html = Html.fromHtml(n.getContent(), Html.FROM_HTML_MODE_LEGACY);
        } else {
            html = Html.fromHtml(n.getContent());
        }
        flowContent.setText(html);
        flowContent.setTextSize(getResources().getDimension(R.dimen.text_size_news));
        flowContent.setTypeface(tf);
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
