package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.VerifyModel;
import com.imajiku.vegefinder.model.presenter.VerifyPresenter;
import com.imajiku.vegefinder.model.presenter.view.VerifyView;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener, VerifyView {
    private EditText editCode;
    private TextView error;
    private VerifyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        presenter = new VerifyPresenter(this);
        VerifyModel model = new VerifyModel(presenter);
        presenter.setModel(model);
        editCode = (EditText) findViewById(R.id.verify_code);
        Button submit = (Button) findViewById(R.id.verify_btn);
        error = (TextView) findViewById(R.id.error_text);
        error.setVisibility(View.GONE);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        if(editCode.getText().toString().equals("")){
            Toast.makeText(VerifyActivity.this, "Code must be filled", Toast.LENGTH_SHORT).show();
        }else{
            presenter.submit(editCode.getText().toString());
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
    public void showError(String message) {
        error.setText(message);
        error.setVisibility(View.VISIBLE);
    }

    @Override
    public void successVerify() {
        error.setVisibility(View.GONE);
        Toast.makeText(VerifyActivity.this, "Registration complete.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(VerifyActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
