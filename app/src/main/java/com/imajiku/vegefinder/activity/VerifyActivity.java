package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.presenter.VerifyPresenter;
import com.imajiku.vegefinder.model.presenter.view.VerifyView;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener, VerifyView {
    private EditText editCode;
    private String code;
    private TextView error;
    private VerifyPresenter presenter;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        presenter = new VerifyPresenter(this);
        editCode = (EditText) findViewById(R.id.verify_code);
        Button submit = (Button) findViewById(R.id.verify_btn);
        error = (TextView) findViewById(R.id.error_text);

        email = getIntent().getStringExtra("email");
        code = getIntent().getStringExtra("code");
        submit.setOnClickListener(this);

        // when flag is active, user with this email cannot login
        presenter.putFlag(email);
    }

    @Override
    public void onClick(View v) {
        if(editCode.getText().toString().equals(code)){
            error.setVisibility(View.GONE);

            presenter.removeFlag(email);

            Intent intent = new Intent(VerifyActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{
            error.setVisibility(View.VISIBLE);
        }
    }
}
