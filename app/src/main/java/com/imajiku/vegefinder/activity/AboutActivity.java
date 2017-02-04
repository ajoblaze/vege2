package com.imajiku.vegefinder.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imajiku.vegefinder.R;

public class AboutActivity extends AppCompatActivity {

    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LinearLayout aboutLayout = (LinearLayout) findViewById(R.id.about_layout);
        tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");

        for(int ii = 0; ii < aboutLayout.getChildCount(); ii++){
            if(aboutLayout.getChildAt(ii) instanceof TextView){
                ((TextView)aboutLayout.getChildAt(ii)).setTypeface(tf);
            }
        }
    }
}
