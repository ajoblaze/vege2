package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.SpinnerAdapter;
import com.imajiku.vegefinder.model.model.RegionModel;
import com.imajiku.vegefinder.model.presenter.RegionPresenter;
import com.imajiku.vegefinder.model.view.RegionView;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

public class FindPlaceActivity extends AppCompatActivity implements RegionView, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "exc";
    private Spinner countrySpinner, provinceSpinner, citySpinner;
    private ArrayAdapter<String> countryDataAdapter, provinceDataAdapter, cityDataAdapter;
    private RegionPresenter presenter;
    private Button findKeywordBtn, findRegionBtn;
    private String currCountry, currProvince, currCity;
    private EditText keywordText;
    private Typeface tf;
    private TextView title;
    private ImageView arrow1, arrow2, arrow3;
    private final String ALL_PROVINCE = "All Province";
    private final String ALL_CITY = "All City";
    private ProgressBar progressBar;
    private int apiCallCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);

        presenter = new RegionPresenter(this);
        RegionModel model = new RegionModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        title = (TextView) findViewById(R.id.find_title);
        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        provinceSpinner = (Spinner) findViewById(R.id.province_spinner);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        keywordText = (EditText) findViewById(R.id.keyword);

        addApiCounter(true);
        presenter.getCountry();

        countryDataAdapter = new SpinnerAdapter(this,
                R.layout.item_dropdown_profile, new ArrayList<String>(), tf);
        provinceDataAdapter = new SpinnerAdapter(this,
                R.layout.item_dropdown_profile, new ArrayList<String>(), tf);
        cityDataAdapter = new SpinnerAdapter(this,
                R.layout.item_dropdown_profile, new ArrayList<String>(), tf);

        countryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryDataAdapter);
        provinceSpinner.setAdapter(provinceDataAdapter);
        citySpinner.setAdapter(cityDataAdapter);

        countrySpinner.setOnItemSelectedListener(this);
        provinceSpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);

        findRegionBtn = (Button) findViewById(R.id.find_region);
        findRegionBtn.setOnClickListener(this);
        findKeywordBtn = (Button) findViewById(R.id.find_keyword);
        findKeywordBtn.setOnClickListener(this);

        title.setTypeface(tf);
        keywordText.setTypeface(tf);
        findRegionBtn.setTypeface(tf);
        findKeywordBtn.setTypeface(tf);

        arrow1 = (ImageView) findViewById(R.id.arrow1);
        arrow2 = (ImageView) findViewById(R.id.arrow2);
        arrow3 = (ImageView) findViewById(R.id.arrow3);
    }

    @Override
    public void updateDropdown(int type, ArrayList<String> content) {
        ArrayAdapter<String> adapter;
        ImageView arrow;
        if (type == RegionPresenter.COUNTRY) {
            adapter = countryDataAdapter;
            arrow = arrow1;
        } else if (type == RegionPresenter.PROVINCE) {
            adapter = provinceDataAdapter;
            content.add(0, ALL_PROVINCE);
            arrow = arrow2;
        } else if (type == RegionPresenter.CITY) {
            adapter = cityDataAdapter;
            content.add(0, ALL_CITY);
            arrow = arrow3;
        } else {
            return;
        }
        adapter.clear();
        adapter.addAll(content);
        arrow.setVisibility(View.VISIBLE);
        if (type == RegionPresenter.COUNTRY) {
            countrySpinner.setSelection(adapter.getPosition("Indonesia"));
        }
        addApiCounter(false);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        Intent i = new Intent(FindPlaceActivity.this, RestoListActivity.class);
        switch (v.getId()) {
            case R.id.find_region:
                if (currProvince.equals(ALL_PROVINCE)) {
                    Toast.makeText(FindPlaceActivity.this, "Please pick a province", Toast.LENGTH_SHORT).show();
                    changeBorder(provinceSpinner, true);
                    changeBorder(keywordText, false);
                }else{
                    i.putExtra("page", RestoListActivity.PAGE_SEARCH);
                    i.putExtra("type", "region");
                    i.putExtra("country", String.valueOf(presenter.getCountryId(currCountry)));
                    i.putExtra("province", String.valueOf(presenter.getProvinceId(currProvince)));
                    if (currCity.equals(ALL_CITY)) {
                        i.putExtra("city", "");
                    } else {
                        i.putExtra("city", String.valueOf(presenter.getCityId(currCity)));
                    }
                    startActivity(i);
                }
                break;
            case R.id.find_keyword:
                String keyword = keywordText.getText().toString();
                if (keyword.equals("")) {
                    Toast.makeText(FindPlaceActivity.this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
                    changeBorder(keywordText, true);
                    changeBorder(provinceSpinner, false);
                } else {
                    i.putExtra("page", RestoListActivity.PAGE_SEARCH);
                    i.putExtra("type", "keyword");
                    i.putExtra("keyword", keyword);
                    startActivity(i);
                }
                break;
        }
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void changeBorder(View v, boolean isError){
        int id;
        if(isError){
            id = R.drawable.rounded_error;
        }else{
            id = R.drawable.selector_rounded_edittext;
        }
        v.setBackground(ContextCompat.getDrawable(this, id));
    }

    public void addApiCounter(boolean isStart){
        if(isStart){
            apiCallCounter++;
        }else{
            if(apiCallCounter > 0) {
                apiCallCounter--;
            }
        }
        if(apiCallCounter == 1){
            progressBar.setVisibility(View.VISIBLE);
        }else if(apiCallCounter == 0){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.country_spinner:
                currCountry = countryDataAdapter.getItem(position);
                addApiCounter(true);
                presenter.getProvince(countryDataAdapter.getItem(position));
                break;
            case R.id.province_spinner:
                currProvince = provinceDataAdapter.getItem(position);
                addApiCounter(true);
                presenter.getCity(provinceDataAdapter.getItem(position));
                citySpinner.setSelection(0);
                break;
            case R.id.city_spinner:
                currCity = cityDataAdapter.getItem(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
