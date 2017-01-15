package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.MessageModel;
import com.imajiku.vegefinder.model.presenter.MessagePresenter;
import com.imajiku.vegefinder.model.view.MessageView;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, MessageView {

    private static final int EDIT_TEXT_QTY = 5;
    private EditText[] editText = new EditText[EDIT_TEXT_QTY];
    private Button submit;
    private MessagePresenter presenter;
    private Typeface tf;
    private ProgressBar progressBar;
    private boolean isSubmitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        presenter = new MessagePresenter(this);
        MessageModel model = new MessageModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");
        initToolbar(getResources().getString(R.string.title_contact_us));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        editText[0] = (EditText) findViewById(R.id.name);
        editText[1] = (EditText) findViewById(R.id.email);
        editText[2] = (EditText) findViewById(R.id.phone);
        editText[3] = (EditText) findViewById(R.id.subject);
        editText[4] = (EditText) findViewById(R.id.message);
        submit = (Button) findViewById(R.id.submit);

        for (int i = 0; i < EDIT_TEXT_QTY; i++) {
            editText[i].setTypeface(tf);
        }
        submit.setTypeface(tf);
        submit.setOnClickListener(this);
        isSubmitting = false;
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        for (int i = 0; i < EDIT_TEXT_QTY; i++) {
            changeBorder(i, false);
        }
        switch (v.getId()) {
            case R.id.submit:
                if (getString(editText[0]).length() == 0) {
                    toast("Please input your name");
                    changeBorder(0, true);
                } else if (getString(editText[1]).length() == 0) {
                    toast("Please input your email");
                    changeBorder(1, true);
                } else if (getString(editText[2]).length() == 0) {
                    toast("Please input your phone number");
                    changeBorder(2, true);
                } else if (getString(editText[3]).length() == 0) {
                    toast("Please fill in your subject");
                    changeBorder(3, true);
                } else if (getString(editText[4]).length() == 0) {
                    toast("Please write your message");
                    changeBorder(4, true);
                } else {
                    if (!isSubmitting) {
                        isSubmitting = true;
                        progressBar.setVisibility(View.VISIBLE);
                        presenter.sendContactUs(
                                getString(editText[0]),
                                getString(editText[1]),
                                getString(editText[2]),
                                getString(editText[3]),
                                getString(editText[4])
                        );
                    }
                }
        }
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

    private void changeBorder(int idx, boolean isError) {
        int id;
        EditText et = editText[idx];
        if (isError) {
            id = R.drawable.rect_error;
        } else {
            id = R.drawable.selector_edittext;
        }
        et.setBackground(ContextCompat.getDrawable(this, id));
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

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private String getString(EditText et) {
        return et.getText().toString();
    }

    private void toast(String s) {
        Toast.makeText(ContactUsActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successSendContactUs() {
        toast("Sent successfully");
        isSubmitting = false;
        progressBar.setVisibility(View.INVISIBLE);
        finish();
    }

    @Override
    public void failedSendContactUs(String s) {
        toast("Failed sending contact us");
        isSubmitting = false;
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void successSendReview(String s) {

    }

    @Override
    public void failedSendReview(String s) {

    }

    @Override
    public void successSendReport() {

    }

    @Override
    public void failedSendReport(String s) {

    }
}
