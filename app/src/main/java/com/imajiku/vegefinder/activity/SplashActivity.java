package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.utility.ImageDecoderHelper;

import java.io.IOException;

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
        getScreenDim();
        bg = (ImageView) findViewById(R.id.image);
        bg.setImageBitmap(
                ImageDecoderHelper.decodeSampledBitmapFromResource(
                        getResources(),
                        R.drawable.splash,
                        dpWidth,
                        dpHeight
                )
        );
//        try {
//            GifDrawable anim = new GifDrawable( getResources(), R.drawable.splash3);
//            duration = anim.getDuration();
//        } catch (IOException e) {
//            duration = 2000;
//        }

        duration = 2000;
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
}
