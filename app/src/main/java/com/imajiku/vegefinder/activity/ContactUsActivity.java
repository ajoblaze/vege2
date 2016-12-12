package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, MessageView {

    private EditText name, email, phone, subject, message;
    private Button submit;
    private MessagePresenter presenter;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        presenter = new MessagePresenter(this);
        MessageModel model = new MessageModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");
        initToolbar(getResources().getString(R.string.title_contact_us));

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        submit = (Button) findViewById(R.id.submit);

        name.setTypeface(tf);
        email.setTypeface(tf);
        phone.setTypeface(tf);
        subject.setTypeface(tf);
        message.setTypeface(tf);
        submit.setTypeface(tf);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.submit:
                if(getString(name).length() == 0){
                    toast("Please input your name");
                }else if(getString(email).length() == 0){
                    toast("Please input your email");
                }else if(getString(phone).length() == 0){
                    toast("Please input your phone number");
                }else if(getString(subject).length() == 0){
                    toast("Please fill in your subject");
                }else if(getString(message).length() == 0){
                    toast("Please write your message");
                }else{
                    presenter.sendContactUs(
                            getString(name),
                            getString(email),
                            getString(phone),
                            getString(subject),
                            getString(message)
                    );
                }
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

    private String getString(EditText et){
        return et.getText().toString();
    }

    private void toast(String s){
        Toast.makeText(ContactUsActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successSendContactUs() {
        finish();
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
    public void failedSendContactUs(String s) {

    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
