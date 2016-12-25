package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.MessageModel;
import com.imajiku.vegefinder.model.presenter.MessagePresenter;
import com.imajiku.vegefinder.model.view.MessageView;
import com.imajiku.vegefinder.utility.CurrentUser;

public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener, MessageView {

    public static final int REPORT = 21;
    public static final int FEEDBACK = 22;
    private static final String TAG = "exc";
    private EditText subject, message;
    private Button submit;
    private int restoId, userId, type;
    private MessagePresenter presenter;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");
        initToolbar(getResources().getString(R.string.title_report));

        restoId = getIntent().getIntExtra("placeId", -1);
        userId = CurrentUser.getId(this);
        type = getIntent().getIntExtra("type", -1);

        presenter = new MessagePresenter(this);
        MessageModel model = new MessageModel(presenter);
        presenter.setModel(model);

        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        submit = (Button) findViewById(R.id.submit);

        subject.setTypeface(tf);
        message.setTypeface(tf);
        submit.setTypeface(tf);

        submit.setOnClickListener(this);
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
        hideKeyboard();
        switch (view.getId()) {
            case R.id.submit:
                if(type == FEEDBACK) {
                    presenter.sendFeedback(userId, subject.getText().toString(), message.getText().toString());
                }else if(type == REPORT) {
                    presenter.sendReport(userId, restoId, subject.getText().toString(), message.getText().toString());
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
    public void successSendContactUs() {

    }

    @Override
    public void successSendReview(String s) {

    }

    @Override
    public void failedSendReview(String s) {

    }

    @Override
    public void successSendReport() {
        Toast.makeText(SendMessageActivity.this, "Sent successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failedSendContactUs(String s) {

    }
}