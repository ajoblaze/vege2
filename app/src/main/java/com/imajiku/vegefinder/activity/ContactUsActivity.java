package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.ContactUsModel;
import com.imajiku.vegefinder.model.presenter.ContactUsPresenter;
import com.imajiku.vegefinder.model.presenter.view.ContactUsView;
import com.imajiku.vegefinder.utility.Utility;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, ContactUsView {

    private EditText name, email, phone, subject, message;
    private Button submit;
    private ContactUsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        presenter = new ContactUsPresenter(this);
        ContactUsModel model = new ContactUsModel(presenter);
        presenter.setModel(model);
        initToolbar();
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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
                    presenter.sendMessage(
                            getString(name),
                            getString(email),
                            getString(phone),
                            getString(subject),
                            getString(message)
                    );
//                    new SendContactUsTask().execute();
                }
        }
    }

    public void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(getResources().getString(R.string.contact_us_title));
    }

    private String getString(EditText et){
        return et.getText().toString();
    }

    private void toast(String s){
        Toast.makeText(ContactUsActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private Context self(){
        return ContactUsActivity.this;
    }

    class SendContactUsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Utility.sendEmail(self(), getString(email), getString(subject), getString(message));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
