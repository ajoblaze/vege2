package com.imajiku.vegefinder.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.PhotoListModel;
import com.imajiku.vegefinder.model.presenter.PhotoListPresenter;
import com.imajiku.vegefinder.model.view.PhotoListView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.imajiku.vegefinder.utility.ImageDecoderHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddPhotoActivity extends AppCompatActivity implements View.OnClickListener, PhotoListView {

    private static final int REQUEST_LOAD_IMAGE = 5;
    private static final int REQUEST_CAMERA = 6;
    private static final int PERMISSION_UPPER = 900;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 901;
    private static final int PERMISSION_GALLERY_REQUEST_CODE = 902;
    private static final int PERMISSION_LOWER = 903;
    private String picturePath, pictureFilename;
    private ImageView photoView;
    private Typeface tf;
    private PhotoListPresenter presenter;
    private int userId, placeId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");
        initToolbar(getResources().getString(R.string.title_add_photo));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        presenter = new PhotoListPresenter(this);
        PhotoListModel model = new PhotoListModel(presenter);
        presenter.setModel(model);

        userId = CurrentUser.getId(this);
        placeId = getIntent().getIntExtra("restoId", -1);
        photoView = (ImageView) findViewById(R.id.photo);
        photoView.getAdjustViewBounds();
        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        TextView btnGallery = (TextView) findViewById(R.id.btn_gallery);

        btnGallery.setTypeface(tf);

        fabGallery.setOnClickListener(this);
        fabCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
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

    /**
     * Upload image from camera
     * from http://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity
     * Answer by jengelsma
     */
    @Override
    public void onClick(View view) {
        String[] permissions;
        switch (view.getId()) {
            case R.id.fab_camera:
                permissions = new String[]{Manifest.permission.CAMERA};
                if (hasPermissions(permissions)) {
                    getImageFromCamera();
                } else {
                    requestPerms(permissions, PERMISSION_CAMERA_REQUEST_CODE);
                }
                break;
            case R.id.fab_gallery:
                permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                if (hasPermissions(permissions)) {
                    getImageFromGallery();
                } else {
                    requestPerms(permissions, PERMISSION_GALLERY_REQUEST_CODE);
                }
                break;
            case R.id.btn_gallery:
                progressBar.setVisibility(View.VISIBLE);
                presenter.addPhoto(userId, placeId, pictureFilename, getImageCode());
                break;
        }
    }

    private void getImageFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private void getImageFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_LOAD_IMAGE);
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
                    photoView.setImageBitmap(
                            ImageDecoderHelper.decodeSampledBitmapFromFile(
                                    picturePath,
                                    photoView.getWidth(),
                                    photoView.getHeight()
                            )
                    );
                } else {
                    Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // CHECK PERMISSION FOR MARSHMALLOW
    private boolean hasPermissions(String[] permissions) {
        int res;
        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    private void requestPerms(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isAllowed = false;
        if (requestCode > PERMISSION_UPPER && requestCode < PERMISSION_LOWER) {
            for (int res : grantResults) {
                isAllowed = (res == PackageManager.PERMISSION_GRANTED);
                if (isAllowed) {
                    break;
                }
            }
        }
        if (isAllowed) {
            switch (requestCode) {
                case PERMISSION_CAMERA_REQUEST_CODE:
                    getImageFromCamera();
                    break;
                case PERMISSION_GALLERY_REQUEST_CODE:
                    getImageFromGallery();
                    break;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(AddPhotoActivity.this, "Read from file permission denied.", Toast.LENGTH_SHORT).show();
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Toast.makeText(AddPhotoActivity.this, "Camera permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getImageCode() {
        Bitmap bm =
                ImageDecoderHelper.decodeSampledBitmapFromFile(picturePath, 170, 170);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @Override
    public void successGetRestoImages(ArrayList<String> list, String title) {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void failedGetRestoImages() {
        Toast.makeText(AddPhotoActivity.this, "Failed getting images", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void successAddPhoto(String message) {
        Toast.makeText(AddPhotoActivity.this, message, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void failedAddPhoto(String message) {
        Toast.makeText(AddPhotoActivity.this, message, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
