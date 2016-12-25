package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.ListAdapter;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;
import java.util.List;

public class CallActivity extends AppCompatActivity {

    private Typeface tf;
    private List<String> phoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");
        initToolbar("Call");

        TextView title = (TextView) findViewById(R.id.title);
        ListView listView = (ListView) findViewById(R.id.list);

        phoneList = new ArrayList<>();
        // case of only 1 phone per place
        String phone = getIntent().getStringExtra("phone");
        phone = "900000"; // TODO: delet dis
        if(!phone.isEmpty()){
            phoneList.add(phone);
        }

        title.setTypeface(tf);
        listView.setAdapter(new ListAdapter(
                this,
                R.layout.item_phone,
                phoneList,
                tf
        ));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                call(phoneList.get(i));
            }
        });
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    private void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }
}
