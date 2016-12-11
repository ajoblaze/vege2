package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.SpinnerAdapter;
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
    private Button findBtn;
    private String currCountry, currProvince, currCity;
    private EditText keywordText;
    private Typeface tf;
    private TextView title;
    private ImageView arrow1, arrow2, arrow3;
    private final String ALL_PROVINCE = "All Province";
    private final String ALL_CITY = "All City";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);

        presenter = new RegionPresenter(this);
        RegionModel model = new RegionModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        title = (TextView) findViewById(R.id.find_title);
        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        provinceSpinner = (Spinner) findViewById(R.id.province_spinner);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        keywordText = (EditText) findViewById(R.id.keyword);

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

        findBtn = (Button) findViewById(R.id.find_btn);
        findBtn.setOnClickListener(this);

        title.setTypeface(tf);
        keywordText.setTypeface(tf);
        findBtn.setTypeface(tf);

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
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        Intent i = new Intent(FindPlaceActivity.this, RestoListActivity.class);
        switch (v.getId()) {
            case R.id.find_btn:
                String keyword = keywordText.getText().toString();
                if (keyword.equals("")) {
                    if (currProvince.equals(ALL_PROVINCE)) {
                        Toast.makeText(FindPlaceActivity.this, "Please pick a province", Toast.LENGTH_SHORT).show();
                    }
                    else if (currCity.equals(ALL_CITY)) {
                        Toast.makeText(FindPlaceActivity.this, "Please pick a city", Toast.LENGTH_SHORT).show();
                    }else{
                        i.putExtra("page", RestoListActivity.PAGE_SEARCH);
                        i.putExtra("country", presenter.getCountryId(currCountry));
                        i.putExtra("province", presenter.getProvinceId(currProvince));
                        i.putExtra("city", presenter.getCityId(currCity));
                        startActivity(i);
                    }
                } else {
                    i.putExtra("page", RestoListActivity.PAGE_SEARCH);
                    i.putExtra("country", presenter.getCountryId(currCountry));
                    i.putExtra("province", presenter.getProvinceId(currProvince));
                    i.putExtra("city", presenter.getCityId(currCity));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.country_spinner:
                currCountry = countryDataAdapter.getItem(position);
                presenter.getProvince(countryDataAdapter.getItem(position));
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
