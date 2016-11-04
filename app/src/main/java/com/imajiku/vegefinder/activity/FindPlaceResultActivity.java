package com.imajiku.vegefinder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.FindPlaceModel;
import com.imajiku.vegefinder.model.presenter.FindPlacePresenter;
import com.imajiku.vegefinder.model.view.FindPlaceView;

public class FindPlaceResultActivity extends AppCompatActivity implements FindPlaceView {

    private FindPlacePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place_result);

        presenter = new FindPlacePresenter(this);
        FindPlaceModel model = new FindPlaceModel(presenter);
        presenter.setModel(model);

        String type = getIntent().getStringExtra("type");
        if(type.equals("region")) {
            int provinceId = getIntent().getIntExtra("province", -1);
            int cityId = getIntent().getIntExtra("city", -1);
            if(provinceId != -1 && cityId != -1) {
                presenter.findPlace(provinceId, cityId);
            }
        }
    }
}
