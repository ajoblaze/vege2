package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.RegionModel;
import com.imajiku.vegefinder.model.presenter.RegionPresenter;
import com.imajiku.vegefinder.model.view.FindPlaceView;
import com.imajiku.vegefinder.model.view.RegionView;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

public class FindPlaceActivity extends AppCompatActivity implements FindPlaceView, RegionView, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner countrySpinner, provinceSpinner, citySpinner;
    private ArrayAdapter<String> countryDataAdapter, provinceDataAdapter, cityDataAdapter;
    private RegionPresenter presenter;
    private Button findRegion, findKeyword;
    private String currProvince, currCity;
    private EditText keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);

        presenter = new RegionPresenter(this);
        RegionModel model = new RegionModel(presenter);
        presenter.setModel(model);

        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        provinceSpinner = (Spinner) findViewById(R.id.province_spinner);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        keyword = (EditText) findViewById(R.id.keyword);

        presenter.getCountry();

        countryDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        provinceDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        cityDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());

        countryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryDataAdapter);
        provinceSpinner.setAdapter(provinceDataAdapter);
        citySpinner.setAdapter(cityDataAdapter);

        countrySpinner.setOnItemSelectedListener(this);
        provinceSpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);

        findRegion = (Button) findViewById(R.id.find_region);
        findKeyword = (Button) findViewById(R.id.find_keyword);
        findRegion.setOnClickListener(this);
        findKeyword.setOnClickListener(this);
    }

    @Override
    public void updateDropdown(int type, ArrayList<String> content) {
        ArrayAdapter<String> adapter;
        if(type == RegionPresenter.COUNTRY){
            adapter = countryDataAdapter;
        } else if(type == RegionPresenter.PROVINCE){
            adapter = provinceDataAdapter;
            content.add(0, "All Province");
        } else if(type == RegionPresenter.CITY) {
            adapter = cityDataAdapter;
            content.add(0, "All City");
        } else {
            return;
        }
        adapter.clear();
        adapter.addAll(content);
        if(type==RegionPresenter.COUNTRY){
            countrySpinner.setSelection(adapter.getPosition("Indonesia"));
        }
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        Intent i  = new Intent(FindPlaceActivity.this, RestoListActivity.class);
        switch(v.getId()){
            case R.id.find_region:
                i.putExtra("page", RestoListActivity.PAGE_SEARCH);
                i.putExtra("type", "region");
                i.putExtra("province", presenter.getProvinceId(currProvince));
                i.putExtra("city", presenter.getCityId(currCity));
                startActivity(i);
                break;
            case R.id.find_keyword:
                i.putExtra("page", RestoListActivity.PAGE_SEARCH);
                i.putExtra("type", "keyword");
                i.putExtra("keyword", keyword.getText().toString());
                startActivity(i);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.country_spinner:
                presenter.getProvince(countryDataAdapter.getItem(position));
                provinceSpinner.setSelection(countryDataAdapter.getPosition("Indonesia"));
                break;
            case R.id.province_spinner:
                currProvince = provinceDataAdapter.getItem(position);
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

    @Override
    public void successFindKeyword(ArrayList<Resto> data) {

    }

    @Override
    public void successFindPlace(ArrayList<Resto> data) {

    }
}
