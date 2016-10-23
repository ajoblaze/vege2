package com.imajiku.vegefinder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.FindPlaceModel;
import com.imajiku.vegefinder.model.RegionModel;
import com.imajiku.vegefinder.model.presenter.FindPlacePresenter;
import com.imajiku.vegefinder.model.presenter.RegionPresenter;
import com.imajiku.vegefinder.model.presenter.view.FindPlaceView;
import com.imajiku.vegefinder.model.presenter.view.RegionView;

import java.util.ArrayList;

public class FindPlaceActivity extends AppCompatActivity implements FindPlaceView, RegionView, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner countrySpinner, provinceSpinner, citySpinner;
    private ArrayAdapter<String> countryDataAdapter, provinceDataAdapter, cityDataAdapter;
    private FindPlacePresenter presenter;
    private RegionPresenter regionPresenter;
    private Button search;
    private String currProvince, currCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);

        presenter = new FindPlacePresenter(this);
        FindPlaceModel model = new FindPlaceModel(presenter);
        presenter.setModel(model);

        regionPresenter = new RegionPresenter(this);
        RegionModel regionModel = new RegionModel(regionPresenter);
        regionPresenter.setModel(regionModel);

        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        provinceSpinner = (Spinner) findViewById(R.id.province_spinner);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);

        regionPresenter.getCountry();

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

        search = (Button) findViewById(R.id.find_button);
        search.setOnClickListener(this);
    }

    @Override
    public void updateDropdown(int type, ArrayList<String> content) {
        ArrayAdapter<String> adapter;
        if(type == RegionPresenter.COUNTRY){
            adapter = countryDataAdapter;
        } else if(type == RegionPresenter.PROVINCE){
            adapter = provinceDataAdapter;
            if(content.size() > 0) {
                content.add(0, "Choose Province..");
            }else{
                content.add("Province");
            }
        } else if(type == RegionPresenter.CITY) {
            adapter = cityDataAdapter;
            if(content.size() > 0) {
                content.add(0, "Choose City..");
            }else{
                content.add("City");
            }
        } else {
            return;
        }
        adapter.clear();
        adapter.addAll(content);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.find_button:
                int provinceId = regionPresenter.getProvinceId(currProvince);
                int cityId = regionPresenter.getCityId(currCity);
                presenter.findPlace(provinceId, cityId);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.country_spinner:
                regionPresenter.getProvince(countryDataAdapter.getItem(position));
                provinceSpinner.setSelection(0);
                break;
            case R.id.province_spinner:
                currProvince = provinceDataAdapter.getItem(position);
                regionPresenter.getCity(provinceDataAdapter.getItem(position));
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
