package com.imajiku.vegefinder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    private ImageView banner;
    private TextView restoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        banner = (ImageView) findViewById(R.id.resto_img);
        restoTitle = (TextView) findViewById(R.id.resto_title);
        int id = getIntent().getIntExtra("id", -1);
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        restoTitle.setText(title);
        Picasso.with(this)
                .load(image)
                .noFade()
                .fit()
                .centerCrop()
                .into(banner);
    }
}
