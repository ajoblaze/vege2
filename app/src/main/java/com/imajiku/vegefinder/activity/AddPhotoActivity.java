package com.imajiku.vegefinder.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
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
    private String picturePath, pictureFilename;
    private ImageView photoView;
    private Typeface tf;
    private PhotoListPresenter presenter;
    private int userId, placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");
        initToolbar(getResources().getString(R.string.title_add_photo));

        presenter = new PhotoListPresenter(this);
        PhotoListModel model = new PhotoListModel(presenter);
        presenter.setModel(model);

        userId = CurrentUser.getId(this);
        placeId = getIntent().getIntExtra("placeId", -1);
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
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    /**
     * Upload image from camera
     * from http://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity
     * Answer by jengelsma
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_gallery:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_LOAD_IMAGE);
                break;
            case R.id.fab_camera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
                break;
            case R.id.btn_gallery:
                presenter.addPhoto(userId, placeId, pictureFilename, getImageCode());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                pictureFilename = picturePath.substring(picturePath.lastIndexOf("/")+1);
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
        } else if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoView.setImageBitmap(photo);
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

    }

    @Override
    public void failedGetRestoImages() {

    }

    @Override
    public void successAddPhoto(String message) {
        Toast.makeText(AddPhotoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedAddPhoto(String message) {
        Toast.makeText(AddPhotoActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
