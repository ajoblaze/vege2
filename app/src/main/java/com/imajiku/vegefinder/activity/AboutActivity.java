package com.imajiku.vegefinder.activity;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.utility.Utility;

public class AboutActivity extends AppCompatActivity {

    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initToolbar("About");

        LinearLayout aboutLayout = (LinearLayout) findViewById(R.id.about_layout);
        tf = Typeface.createFromAsset(getAssets(), Utility.regFont);

        for(int ii = 0; ii < aboutLayout.getChildCount(); ii++){
            if(aboutLayout.getChildAt(ii) instanceof TextView){
                ((TextView)aboutLayout.getChildAt(ii)).setTypeface(tf);
            }
        }
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
}
