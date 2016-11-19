package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1));

//        getIntent().getSerializableExtra("resto");
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:"+asd.getText().toString()));
//        startActivity(intent);
    }
}
