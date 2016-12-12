package com.imajiku.vegefinder.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.adapter.PhotoDetailAdapter;
import com.imajiku.vegefinder.fragment.PhotoDetailFragment;
import com.imajiku.vegefinder.model.model.PhotoListModel;
import com.imajiku.vegefinder.model.presenter.PhotoListPresenter;
import com.imajiku.vegefinder.model.view.PhotoListView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class PhotoDetailActivity extends AppCompatActivity implements PhotoListView, View.OnClickListener {

    private PhotoListPresenter presenter;
    private ViewPager viewPager;
    private ImageView share;
    private int restoId, userId, position;
    private PhotoDetailAdapter adapter;
    private ArrayList<String> list;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        restoId = getIntent().getIntExtra("placeId", -1);
        userId = CurrentUser.getId();
        position = getIntent().getIntExtra("position", -1);

        presenter = new PhotoListPresenter(this);
        PhotoListModel model = new PhotoListModel(presenter);
        presenter.setModel(model);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(this);

        presenter.getRestoImages(restoId, userId);
    }

    @Override
    public void successGetRestoImages(ArrayList<String> imageList, String title) {
        initToolbar(title);
        this.list = imageList;
        adapter = new PhotoDetailAdapter(getSupportFragmentManager(), imageList.size());
        adapter.setData(imageList);
        viewPager.setAdapter(adapter);
        if(position != -1) {
            viewPager.setCurrentItem(position);
        }
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    @Override
    public void failedGetRestoImages() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                sharePhoto2();
                break;
        }
    }

    /**
     * from http://stackoverflow.com/questions/7661875/how-to-use-share-image-using-sharing-intent-to-share-images-in-android
     * answer by Sebastian Hojas
     */
    private void sharePhoto() {
        Bitmap icon = ((PhotoDetailFragment) adapter.getItem(viewPager.getCurrentItem())).getCurrentPhoto();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                values);
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), icon, "title", null);
        Uri uri = Uri.parse(bitmapPath);

        OutputStream outstream;
        try {
            outstream = getContentResolver().openOutputStream(uri);
            icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch (Exception e) {
            Log.e("exc", e.getMessage());
        }

        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));
    }

    /**
     * from http://stackoverflow.com/questions/9049143/android-share-intent-for-a-bitmap-is-it-possible-not-to-save-it-prior-sharing
     * answer by Suragch
     */
    private void sharePhoto2(){
        int curPos = viewPager.getCurrentItem();
        Picasso.with(this).load(list.get(curPos)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File cachePath = new File(getCacheDir(), "images");
                    cachePath.mkdirs(); // don't forget to make the directory
                    FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File imagePath = new File(getCacheDir(), "images");
                File newFile = new File(imagePath, "image.png");
                Uri contentUri = FileProvider.getUriForFile(PhotoDetailActivity.this, "com.imajiku.vegefinder.fileprovider", newFile);

                if (contentUri != null) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                    shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                    startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}
