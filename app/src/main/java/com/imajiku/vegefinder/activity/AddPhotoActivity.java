package com.imajiku.vegefinder.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.utility.ImageDecoderHelper;

public class AddPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_LOAD_IMAGE = 5;
    private static final int REQUEST_CAMERA = 6;
    private String picturePath;
    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        int id = getIntent().getIntExtra("id", -1);
        photoView = (ImageView) findViewById(R.id.photo);
        photoView.getAdjustViewBounds();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_camera);
        Button btnGallery = (Button) findViewById(R.id.btn_gallery);

        fab.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
    }

    /**
     * Upload image from camera
     * from http://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity
     * Answer by jengelsma
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_camera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
                break;
            case R.id.btn_gallery:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_LOAD_IMAGE);
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
        else if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoView.setImageBitmap(photo);
        }
    }
}
