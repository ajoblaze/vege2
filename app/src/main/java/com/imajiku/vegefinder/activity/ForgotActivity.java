package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.ForgotModel;
import com.imajiku.vegefinder.model.presenter.ForgotPresenter;
import com.imajiku.vegefinder.model.view.ForgotView;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener, ForgotView {

    private static final String TAG = "exc";
    private EditText email;
    private Button reset;
    private TextView title, subtitle, register, contactUs;
    private ForgotPresenter presenter;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        presenter = new ForgotPresenter(this);
        ForgotModel model = new ForgotModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        title = (TextView) findViewById(R.id.forget_title);
        subtitle = (TextView) findViewById(R.id.forget_subtitle);
        email = (EditText) findViewById(R.id.username_email);
        reset = (Button) findViewById(R.id.reset_btn);
        register = (TextView) findViewById(R.id.register);
        contactUs = (TextView) findViewById(R.id.contact_us);

        title.setTypeface(tf);
        subtitle.setTypeface(tf);
        email.setTypeface(tf);
        reset.setTypeface(tf);
        register.setTypeface(tf);
        contactUs.setTypeface(tf);

        reset.setOnClickListener(this);
        register.setOnClickListener(this);
        contactUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.reset_btn:
//                successForgot();
                String emailContent = email.getText().toString();
                if (emailContent.length() == 0) {
                    Toast.makeText(ForgotActivity.this, "Please insert email or username", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.forget(emailContent);
                }
                break;
            case R.id.register:
                startActivity(new Intent(ForgotActivity.this, RegisterActivity.class));
                break;
            case R.id.contact_us:
                startActivity(new Intent(ForgotActivity.this, ContactUsActivity.class));
                break;
        }
    }

    @Override
    public void successForgot() {
        Intent i = new Intent(ForgotActivity.this, VerifyForgotActivity.class);
        startActivity(i);
    }

    @Override
    public void failedForgot() {
        Toast.makeText(ForgotActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
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
