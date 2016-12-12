package com.imajiku.vegefinder.activity;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.CheckInModel;
import com.imajiku.vegefinder.model.presenter.CheckInPresenter;
import com.imajiku.vegefinder.model.view.CheckInView;
import com.imajiku.vegefinder.utility.CurrentUser;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener, CheckInView {

    private Typeface tf;
    private EditText comment;
    private AppCompatCheckBox fb, twitter;
    private Button checkInBtn;
    private CheckInPresenter presenter;
    private int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        presenter = new CheckInPresenter(this);
        CheckInModel model = new CheckInModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");
        initToolbar("Check In");

        placeId = getIntent().getIntExtra("placeId", -1);
        comment = (EditText) findViewById(R.id.comment);
        fb = (AppCompatCheckBox) findViewById(R.id.checkin_fb);
        twitter = (AppCompatCheckBox) findViewById(R.id.checkin_twit);
        checkInBtn = (Button) findViewById(R.id.btn_check_in);

        fb.setSupportButtonTintList(setColorStateList(
                ContextCompat.getColor(this, R.color.com_facebook_blue)
        ));
        twitter.setSupportButtonTintList(setColorStateList(
                ContextCompat.getColor(this, R.color.twitterBlue)
        ));

        comment.setTypeface(tf);
        fb.setTypeface(tf);
        twitter.setTypeface(tf);
        checkInBtn.setTypeface(tf);

        checkInBtn.setOnClickListener(this);
    }

    private ColorStateList setColorStateList(int color){
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled} //enabled
                },new int[] {color}
        );
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_check_in:
                presenter.checkIn(CurrentUser.getId(), placeId, comment.getText().toString());
                break;
        }
    }

    @Override
    public void successCheckIn() {
        Log.e("exc", "successCheckIn!");
    }

    @Override
    public void failedCheckIn(String message) {
        Toast.makeText(CheckInActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
