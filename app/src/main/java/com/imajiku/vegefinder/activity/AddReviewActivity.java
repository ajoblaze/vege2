package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.MessageModel;
import com.imajiku.vegefinder.model.presenter.MessagePresenter;
import com.imajiku.vegefinder.model.view.MessageView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.imajiku.vegefinder.utility.Utility;

public class AddReviewActivity extends AppCompatActivity implements View.OnClickListener, MessageView {

    private static final String TAG = "exc";
    private RatingBar ratingBar;
    private TextView label, rate, title, comment;
    private Button submit;
    private MessagePresenter presenter;
    private int restoId, userId;
    private float currRate;
    private LinearLayout rateLayout;
    private Typeface tf;
    private ProgressBar progressBar;
    private int apiCallCounter = 0;
    private boolean isSubmitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        tf = Typeface.createFromAsset(getAssets(), Utility.regFont);
        initToolbar(getResources().getString(R.string.title_add_review));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        restoId = getIntent().getIntExtra("restoId", -1);
        userId = CurrentUser.getId(this);
//        if(restoId == -1 || userId == -1){
//            throw new RuntimeException("send userid and placeid");
//        }

        presenter = new MessagePresenter(this);
        MessageModel model = new MessageModel(presenter);
        presenter.setModel(model);

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        rateLayout = (LinearLayout) findViewById(R.id.rate_layout);
        label = (TextView) findViewById(R.id.label);
        rate = (TextView) findViewById(R.id.rate);
        title = (TextView) findViewById(R.id.title);
        comment = (TextView) findViewById(R.id.comment);
        submit = (Button) findViewById(R.id.submit);

        for(int i=0;i<rateLayout.getChildCount();i++){
            ((TextView)rateLayout.getChildAt(i)).setTypeface(tf);
        }
        label.setTypeface(tf);
        title.setTypeface(tf);
        comment.setTypeface(tf);
        submit.setTypeface(tf);

        submit.setOnClickListener(this);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                currRate = v;
                rate.setText(String.valueOf((int)(currRate * 2)));
            }
        });
        isSubmitting = false;
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
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
    }

    @Override
    public void onClick(View view) {
        hideKeyboard();
        switch (view.getId()) {
            case R.id.submit: {
                if(!isSubmitting) {
                    isSubmitting = true;
                    addApiCounter(true);
                    presenter.sendReview(
                            userId,
                            restoId,
                            rate.getText().toString(),
                            title.getText().toString(),
                            comment.getText().toString()
                    );
                }
            }
                break;
        }
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
    public void successSendReview(String s) {
        addApiCounter(false);
        isSubmitting = false;
        Toast.makeText(AddReviewActivity.this, s, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void failedSendReview(String s) {
        addApiCounter(false);
        isSubmitting = false;
        Toast.makeText(AddReviewActivity.this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successSendContactUs() {

    }

    @Override
    public void failedSendContactUs(String s) {

    }


    @Override
    public void successSendReport() {

    }

    @Override
    public void failedSendReport(String s) {

    }
}
