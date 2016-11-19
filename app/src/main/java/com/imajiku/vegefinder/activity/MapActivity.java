package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.MapViewFragment;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    private String longitude, latitude;

    /**
     * Shows map given longitude and latitude
     * from http://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
     * Answer by Arshu, Brandon Yang
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String title = getIntent().getStringExtra("title");
        String address = getIntent().getStringExtra("address");
        longitude = getIntent().getStringExtra("longitude");
        latitude = getIntent().getStringExtra("latitude");

        TextView restoTitle = (TextView) findViewById(R.id.title);
        TextView restoAddress = (TextView) findViewById(R.id.address);
        Button direction = (Button) findViewById(R.id.btn_direction);
        restoTitle.setText(title);
        restoAddress.setText(address);
        direction.setOnClickListener(this);

        // Pushing MapView Fragment
        MapViewFragment fragment = MapViewFragment.newInstance(longitude, latitude);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    /**
     * Launching Google Maps with directions from current location
     * from http://stackoverflow.com/questions/2662531/launching-google-maps-directions-via-an-intent-on-android
     * Answer by Jan S.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_direction: {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+latitude+","+longitude));
                startActivity(intent);
            }
                break;
        }
    }
}
