package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.imajiku.vegefinder.R;

import java.io.IOException;
import java.io.InputStream;

import pl.droidsonroids.gif.GifDrawable;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Bitmap bitmap;
    private ImageView bg;
    private int duration, dpHeight, dpWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        try {
            GifDrawable anim = new GifDrawable( getResources(), R.drawable.splash3);
            duration = anim.getDuration();
        } catch (IOException e) {
            duration = 2000;
        }
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // code for check first time is in LoginPresenter
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, duration);
    }

    private void getScreenDim(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        dpHeight = (int) (displayMetrics.heightPixels / displayMetrics.density);
        dpWidth = (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

//    class ProductImageLoaderTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            bitmap = ImageDecoderHelper.decodeSampledBitmapFromFile(
//                    path.replace("thumb_", ""),
//                    (int) ProductDetailActivity.this.getResources().getDimension(R.dimen.product_main_img_image_width),
//                    (int) ProductDetailActivity.this.getResources().getDimension(R.dimen.product_main_img_image_height));
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Picasso.with(this)
//                    .load(profile.getImageUrl())
//                    .noFade()
//                    .fit()
//                    .centerCrop()
//                    .placeholder(R.drawable.empty_image)
//                    .into(bg);
//        }
//    }
}
