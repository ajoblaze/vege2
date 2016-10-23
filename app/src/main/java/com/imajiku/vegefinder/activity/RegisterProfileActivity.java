package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.RegionModel;
import com.imajiku.vegefinder.model.RegisterProfileModel;
import com.imajiku.vegefinder.model.presenter.RegionPresenter;
import com.imajiku.vegefinder.model.presenter.RegisterProfilePresenter;
import com.imajiku.vegefinder.model.presenter.view.RegionView;
import com.imajiku.vegefinder.model.presenter.view.RegisterProfileView;
import com.imajiku.vegefinder.utility.CircularImageView;
import com.imajiku.vegefinder.utility.Utility;

import java.util.ArrayList;

public class RegisterProfileActivity extends AppCompatActivity implements
        View.OnClickListener,
        RegisterProfileView, RegionView, AdapterView.OnItemSelectedListener {

    private static final int RESULT_LOAD_IMAGE = 5;
    private static final int REQUEST_VERIFY = 98;
    private static final int RESULT_VERIFY = 99;
    private static final String TAG = "exc";
    private CircularImageView profPic, camera;
    private Spinner countrySpinner, provinceSpinner, citySpinner, sex, pref;
    private Button save;
    private ArrayList<String> sexArray, prefArray;
    private RegisterProfilePresenter presenter;
    private ArrayAdapter<String> countryDataAdapter, provinceDataAdapter, cityDataAdapter;
    private String username, email, password;
    private RegionPresenter regionPresenter;
    private String currProvince, currCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);
        presenter = new RegisterProfilePresenter(this);
        RegisterProfileModel model = new RegisterProfileModel(presenter);
        presenter.setModel(model);

        regionPresenter = new RegionPresenter(this);
        RegionModel regionModel = new RegionModel(regionPresenter);
        regionPresenter.setModel(regionModel);

        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        profPic = (CircularImageView) findViewById(R.id.profPic);
        camera = (CircularImageView) findViewById(R.id.iv_camera);
        camera.setOnClickListener(this);

        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        provinceSpinner = (Spinner) findViewById(R.id.province_spinner);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        sex = (Spinner) findViewById(R.id.sex_spinner);
        pref = (Spinner) findViewById(R.id.pref_spinner);

        regionPresenter.getCountry();

        initArray();

        countryDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        provinceDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        cityDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        ArrayAdapter<String> sexDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sexArray);
        ArrayAdapter<String> prefDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, prefArray);

        countryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countrySpinner.setAdapter(countryDataAdapter);
        provinceSpinner.setAdapter(provinceDataAdapter);
        citySpinner.setAdapter(cityDataAdapter);
        sex.setAdapter(sexDataAdapter);
        pref.setAdapter(prefDataAdapter);

//        String selectedCountry = countrySpinner.getSelectedItem().toString();
//        String selectedProvince = provinceSpinner.getSelectedItem().toString();
//        String selectedCity = citySpinner.getSelectedItem().toString();
//        String selectedSex = sex.getSelectedItem().toString();
//        String selectedPref = pref.getSelectedItem().toString();

        countrySpinner.setOnItemSelectedListener(this);
        provinceSpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);

        save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(this);
    }

    private void initArray() {
        sexArray = new ArrayList<>();
        prefArray = new ArrayList<>();

        sexArray.add("Male");
        sexArray.add("Female");
        prefArray.add("Vege");
        prefArray.add("NonVege");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                profPic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }else{
                Toast.makeText(RegisterProfileActivity.this, "Image not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_camera:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.save_button:
                presenter.submitRegister(username, email, password);
                break;
        }
    }

    private Context self(){
        return RegisterProfileActivity.this;
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
            }
        } else if(type == RegionPresenter.CITY) {
            adapter = cityDataAdapter;
            if(content.size() > 0) {
                content.add(0, "Choose City..");
            }
        } else {
            return;
        }
        adapter.clear();
        adapter.addAll(content);
    }

    @Override
    public void sendActivationCode(String code, String email) {
        new SendRegisterCodeTask(code, email).execute();
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

    class SendRegisterCodeTask extends AsyncTask<Void, Void, Void> {

        private String code, email;

        public SendRegisterCodeTask(String code, String email) {
            this.code = code;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Utility.sendEmail(self(), email, "Subject", "Code : "+code);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(RegisterProfileActivity.this, VerifyActivity.class);
            i.putExtra("code", code);
            i.putExtra("email", email);
            startActivityForResult(i, REQUEST_VERIFY);
        }
    }
}
