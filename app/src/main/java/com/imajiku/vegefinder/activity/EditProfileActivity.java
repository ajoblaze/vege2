package com.imajiku.vegefinder.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.SpinnerAdapter;
import com.imajiku.vegefinder.model.model.EditProfileModel;
import com.imajiku.vegefinder.model.model.RegionModel;
import com.imajiku.vegefinder.model.presenter.EditProfilePresenter;
import com.imajiku.vegefinder.model.presenter.RegionPresenter;
import com.imajiku.vegefinder.model.request.EditProfileRequest;
import com.imajiku.vegefinder.model.view.EditProfileView;
import com.imajiku.vegefinder.model.view.RegionView;
import com.imajiku.vegefinder.pojo.UserProfile;
import com.imajiku.vegefinder.utility.CircularImageView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.imajiku.vegefinder.utility.ImageDecoderHelper;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity implements
        View.OnClickListener,
        EditProfileView, RegionView, AdapterView.OnItemSelectedListener {

    private static final int REQUEST_LOAD_IMAGE = 5;
    private static final int REQUEST_CAMERA = 6;
    private static final int REQUEST_VERIFY = 98;
    private static final int RESULT_VERIFY = 99;
    public static final int ACCOUNT = 21;
    public static final int REGISTER = 22;
    private static final String TAG = "exc";
    private static final int PERMISSION_UPPER = 900;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 901;
    private static final int PERMISSION_GALLERY_REQUEST_CODE = 902;
    private static final int PERMISSION_LOWER = 903;
    private CircularImageView profPic;
    private Spinner countrySpinner, provinceSpinner, citySpinner, sex, pref;
    private Button save;
    private ArrayList<String> sexArray, prefArray;
    private EditProfilePresenter presenter;
    private ArrayAdapter<String> countryDataAdapter, provinceDataAdapter, cityDataAdapter;
    private RegionPresenter regionPresenter;
    private String currProvince, currCity;
    private EditText name, email, oldPass, newPass, confPass;
    private TextView[] labels = new TextView[10];
    private LinearLayout[] layouts = new LinearLayout[5];
    private String picturePath, pictureFilename;
    private int pageType;
    private UserProfile currProfile;
    private Typeface tf;
    private int userId;
    private ProgressBar progressBar;
    private int apiCallCounter = 0;
    private boolean isSubmitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        presenter = new EditProfilePresenter(this);
        EditProfileModel model = new EditProfileModel(presenter);
        presenter.setModel(model);

        regionPresenter = new RegionPresenter(this);
        RegionModel regionModel = new RegionModel(regionPresenter);
        regionPresenter.setModel(regionModel);

        pageType = getIntent().getIntExtra("type", -1);
        if(pageType == REGISTER) {
            userId = getIntent().getIntExtra("userId", -1);
        }else if(pageType == ACCOUNT){
            userId = CurrentUser.getId(this);
        }
        Log.e(TAG, "onCreate: "+CurrentUser.getPassword(this));

        tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");
        initToolbar(getResources().getString(R.string.title_profile));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        profPic = (CircularImageView) findViewById(R.id.profPic);
        FloatingActionButton camera = (FloatingActionButton) findViewById(R.id.fab_camera);
        profPic.setOnClickListener(this);
        camera.setOnClickListener(this);

        labels[0] = (TextView) findViewById(R.id.name_label);
        labels[1] = (TextView) findViewById(R.id.email_label);
        labels[2] = (TextView) findViewById(R.id.old_pass_label);
        labels[3] = (TextView) findViewById(R.id.new_pass_label);
        labels[4] = (TextView) findViewById(R.id.confirm_pass_label);
        labels[5] = (TextView) findViewById(R.id.country_label);
        labels[6] = (TextView) findViewById(R.id.province_label);
        labels[7] = (TextView) findViewById(R.id.city_label);
        labels[8] = (TextView) findViewById(R.id.sex_label);
        labels[9] = (TextView) findViewById(R.id.pref_label);

        for (int i = 0; i < 10; i++) {
            labels[i].setTypeface(tf);
        }

        name = (EditText) findViewById(R.id.name_edittext);
        email = (EditText) findViewById(R.id.email_edittext);
        oldPass = (EditText) findViewById(R.id.old_pass_edittext);
        newPass = (EditText) findViewById(R.id.new_pass_edittext);
        confPass = (EditText) findViewById(R.id.confirm_pass_edittext);

        name.setTypeface(tf);
        email.setTypeface(tf);
        oldPass.setTypeface(tf);
        newPass.setTypeface(tf);
        confPass.setTypeface(tf);

        layouts[0] = (LinearLayout) findViewById(R.id.name_layout);
        layouts[1] = (LinearLayout) findViewById(R.id.email_layout);
        layouts[2] = (LinearLayout) findViewById(R.id.old_pass_layout);
        layouts[3] = (LinearLayout) findViewById(R.id.new_pass_layout);
        layouts[4] = (LinearLayout) findViewById(R.id.confirm_pass_layout);

        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        provinceSpinner = (Spinner) findViewById(R.id.province_spinner);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        sex = (Spinner) findViewById(R.id.sex_spinner);
        pref = (Spinner) findViewById(R.id.pref_spinner);

        regionPresenter.getCountry();
        addApiCounter(true);

        initArray();

        countryDataAdapter = new SpinnerAdapter(this,
                R.layout.item_dropdown_profile, new ArrayList<String>(), tf);
        provinceDataAdapter = new SpinnerAdapter(this,
                R.layout.item_dropdown_profile, new ArrayList<String>(), tf);
        cityDataAdapter = new SpinnerAdapter(this,
                R.layout.item_dropdown_profile, new ArrayList<String>(), tf);
        ArrayAdapter<String> sexDataAdapter = new SpinnerAdapter(this,
                R.layout.item_dropdown_profile, sexArray, tf);
        ArrayAdapter<String> prefDataAdapter = new SpinnerAdapter(this,
                R.layout.item_dropdown_profile, prefArray, tf);

        countrySpinner.getAdapter();
        provinceSpinner = (Spinner) findViewById(R.id.province_spinner);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        sex = (Spinner) findViewById(R.id.sex_spinner);
        pref = (Spinner) findViewById(R.id.pref_spinner);

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

        countrySpinner.setOnItemSelectedListener(this);
        provinceSpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);

        save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(this);
        save.setTypeface(tf);

        if (pageType == ACCOUNT) {
            currProfile = (UserProfile) getIntent().getSerializableExtra("profile");
            fillData(currProfile);
        }

        isSubmitting = false;
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillData(UserProfile profile) {
        for (int i = 0; i < 5; i++) {
            layouts[i].setVisibility(View.VISIBLE);
        }
        if(profile.getImageUrl() != null && !profile.getImageUrl().isEmpty()) {
            Picasso.with(this)
                    .load(profile.getImageUrl())
                    .noFade()
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.empty_image)
                    .into(profPic);
        }
        name.setText(profile.getName());
        email.setText(profile.getEmail());
    }

    private void initArray() {
        sexArray = new ArrayList<>();
        prefArray = new ArrayList<>();

        sexArray.add("Male");
        sexArray.add("Female");
        prefArray.add("Vegan");
        prefArray.add("Vegetarian");
        prefArray.add("Participant");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOAD_IMAGE || requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    pictureFilename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                    cursor.close();
                    profPic.setImageBitmap(
                            ImageDecoderHelper.decodeSampledBitmapFromFile(
                                    picturePath,
                                    profPic.getWidth(),
                                    profPic.getHeight()
                            )
                    );
                } else {
                    Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        String[] permissions;
        switch (v.getId()) {
            case R.id.fab_camera:
                permissions = new String[]{Manifest.permission.CAMERA};
                if(hasPermissions(permissions)) {
                    getImageFromCamera();
                }else{
                    requestPerms(permissions, PERMISSION_CAMERA_REQUEST_CODE);
                }
                break;
            case R.id.profPic:
                permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                if(hasPermissions(permissions)) {
                    getImageFromGallery();
                }else{
                    requestPerms(permissions, PERMISSION_GALLERY_REQUEST_CODE);
                }
                break;
            case R.id.save_button:
                if(citySpinner.getSelectedItem() == null){
                    labels[6].setTextColor(ContextCompat.getColor(this, R.color.translucentRed50));
                    Toast.makeText(EditProfileActivity.this, "Please choose province", Toast.LENGTH_SHORT).show();
                }else{
                    int color = labels[5].getCurrentTextColor();
                    labels[6].setTextColor(color);
                    submit();
                }
                break;
        }
    }

    private void getImageFromCamera(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private void getImageFromGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_LOAD_IMAGE);
    }

    // CHECK PERMISSION FOR MARSHMALLOW
    private boolean hasPermissions(String[] permissions){
        int res;
        for(String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if(!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms(String[] permissions, int requestCode){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isAllowed = false;
        if(requestCode > PERMISSION_UPPER && requestCode < PERMISSION_LOWER){
            for(int res : grantResults){
                isAllowed = (res == PackageManager.PERMISSION_GRANTED);
                if(isAllowed){
                    break;
                }
            }
        }
        if(isAllowed){
            switch(requestCode){
                case PERMISSION_CAMERA_REQUEST_CODE:
                    getImageFromCamera();
                    break;
                case PERMISSION_GALLERY_REQUEST_CODE:
                    getImageFromGallery();
                    break;
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(EditProfileActivity.this, "Read from file permission denied.", Toast.LENGTH_SHORT).show();
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Toast.makeText(EditProfileActivity.this, "Camera permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void submit() {
        if(!isSubmitting) {
            isSubmitting = true;
            String selectedCountry = countrySpinner.getSelectedItem().toString();
            String selectedProvince = provinceSpinner.getSelectedItem().toString();
            String selectedCity = citySpinner.getSelectedItem().toString();
            String selectedSex = sex.getSelectedItem().toString();
            String selectedPref = pref.getSelectedItem().toString();
            EditProfileRequest request = new EditProfileRequest(
                    userId,
                    regionPresenter.getCountryId(selectedCountry),
                    regionPresenter.getProvinceId(selectedProvince),
                    regionPresenter.getCityId(selectedCity),
                    selectedSex,
                    selectedPref
            );
            if (pageType == ACCOUNT) {
                String n = name.getText().toString();
                if (isResetPassword()) {
                    presenter.changePassword(email.getText().toString(), newPass.getText().toString());
                    addApiCounter(true);
                } else {
                    if (n.isEmpty()) {
                        Toast.makeText(EditProfileActivity.this, "Name must be filled", Toast.LENGTH_SHORT).show();
                        isSubmitting = false;
                    } else {
                        request.setName(n);
                        presenter.updateProfile(request);
                        addApiCounter(true);
                    }
                }
            } else {
                if (pictureFilename != null) {
                    request.setImage(pictureFilename);
                    request.setImageCode(getImageCode());
                } else {
                    request.setImage(".a"); // TODO change this
                    request.setImageCode("");
                }
                presenter.registerProfile(request);
                addApiCounter(true);
            }
        }
    }

    private boolean isResetPassword() {
        String oldP = oldPass.getText().toString();
        String newP = newPass.getText().toString();
        String confP = confPass.getText().toString();
        // assume user doesn't want to change password
        if(oldP.isEmpty() || newP.isEmpty() || confP.isEmpty()){
            return false;
        }
        // assume user wants to change password
        ColorStateList oldColor = labels[2].getTextColors();
        if(!oldP.equals(CurrentUser.getPassword(this))){
            Toast.makeText(EditProfileActivity.this, "Old Password doesn't match", Toast.LENGTH_LONG).show();
            labels[2].setTextColor(ContextCompat.getColor(this, R.color.red));
            return false;
        }else{
            labels[2].setTextColor(oldColor);
            if(!newP.equals(confP)){
                Toast.makeText(EditProfileActivity.this, "Confirm Password doesn't match", Toast.LENGTH_LONG).show();
                labels[3].setTextColor(ContextCompat.getColor(this, R.color.red));
                labels[4].setTextColor(ContextCompat.getColor(this, R.color.red));
                return false;
            }else{
                labels[3].setTextColor(oldColor);
                labels[4].setTextColor(oldColor);
                return true;
            }
        }
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
        Log.e(TAG, "apiCallCounter: "+apiCallCounter);
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
    public void updateDropdown(int type, ArrayList<String> content) {
        progressBar.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> adapter;
        if (type == RegionPresenter.COUNTRY) {
            adapter = countryDataAdapter;
        } else if (type == RegionPresenter.PROVINCE) {
            adapter = provinceDataAdapter;
            if (content.size() > 0) {
                content.add(0, "Choose Province..");
            }
        } else if (type == RegionPresenter.CITY) {
            adapter = cityDataAdapter;
            if (content.size() > 0) {
                content.add(0, "Choose City..");
            }
        } else {
            return;
        }
        adapter.clear();
        adapter.addAll(content);
        if (pageType == ACCOUNT) {
            int countryId = currProfile.getCountryId();
            int provinceId = currProfile.getProvinceId();
            int cityId = currProfile.getCityId();
            String countryName = regionPresenter.getCountryName(countryId);
            String provinceName = regionPresenter.getProvinceName(provinceId);
            String cityName = regionPresenter.getCityName(cityId);

            if (type == RegionPresenter.COUNTRY && countryName != null) {
                countrySpinner.setSelection(adapter.getPosition(countryName));
            }
            if (type == RegionPresenter.PROVINCE && provinceName != null) {
                provinceSpinner.setSelection(adapter.getPosition(provinceName));
            }
            if (type == RegionPresenter.CITY && cityName != null) {
                citySpinner.setSelection(adapter.getPosition(cityName));
            }
        } else {
            if (type == RegionPresenter.COUNTRY) {
                countrySpinner.setSelection(adapter.getPosition("Indonesia"));
            }
        }
        addApiCounter(false);
    }

    @Override
    public void successRegisterProfile() {
        addApiCounter(false);
        isSubmitting = false;
        progressBar.setVisibility(View.INVISIBLE);
        if (pageType == ACCOUNT) {
            finish();
        } else {
            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void failedRegisterProfile() {
        addApiCounter(false);
        isSubmitting = false;
        Log.e(TAG, "failedRegisterProfile: ");
        Toast.makeText(EditProfileActivity.this, "Failed register profile", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successUpdateProfile() {
        if(picturePath != null) {
            addApiCounter(true);
            presenter.updatePhotoProfile(new EditProfileRequest(CurrentUser.getId(this), pictureFilename, getImageCode()));
        }else{
            successUpdatePhotoProfile();
        }
        addApiCounter(false);
        isSubmitting = false;
    }

    @Override
    public void failedUpdateProfile() {
        addApiCounter(false);
        isSubmitting = false;
        Toast.makeText(EditProfileActivity.this, "Failed update profile", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successUpdatePhotoProfile() {
        addApiCounter(false);
        isSubmitting = false;
        Toast.makeText(EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedUpdatePhotoProfile() {
        addApiCounter(false);
        isSubmitting = false;
        Toast.makeText(EditProfileActivity.this, "Failed update photo profile", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successResetPassword() {
        addApiCounter(false);
        isSubmitting = false;
        Toast.makeText(EditProfileActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedResetPassword() {
        addApiCounter(false);
        isSubmitting = false;
        Toast.makeText(EditProfileActivity.this, "Failed change password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.country_spinner:
                addApiCounter(true);
                regionPresenter.getProvince(countryDataAdapter.getItem(position));
                provinceSpinner.setSelection(0);
                break;
            case R.id.province_spinner:
                currProvince = provinceDataAdapter.getItem(position);
                addApiCounter(true);
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

    private String getImageCode() {
        if(picturePath == null){
            return "";
        }
        Bitmap bm =
                ImageDecoderHelper.decodeSampledBitmapFromFile(picturePath, 170, 170);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
