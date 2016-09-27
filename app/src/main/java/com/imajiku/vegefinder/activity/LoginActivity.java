package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imajiku.vegefinder.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login, skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login_button);
        skip = (Button) findViewById(R.id.skip_button);
        login.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        switch(v.getId()){
            case R.id.login_button:
                i.putExtra("isLogin", true);
                break;
            case R.id.skip_button:
                i.putExtra("isLogin", false);
                break;
        }
        startActivity(i);
    }
}
