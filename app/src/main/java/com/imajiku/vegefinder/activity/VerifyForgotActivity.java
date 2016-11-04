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
import com.imajiku.vegefinder.model.model.VerifyForgotModel;
import com.imajiku.vegefinder.model.presenter.VerifyForgotPresenter;
import com.imajiku.vegefinder.model.view.VerifyForgotView;

public class VerifyForgotActivity extends AppCompatActivity implements View.OnClickListener, VerifyForgotView {

    private EditText codeField, passField, confirmPassField;
    private Button changePass;
    private VerifyForgotPresenter presenter;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_forgot);
        presenter = new VerifyForgotPresenter(this);
        VerifyForgotModel model = new VerifyForgotModel(presenter);
        presenter.setModel(model);
        codeField = (EditText) findViewById(R.id.code);
        passField = (EditText) findViewById(R.id.password);
        confirmPassField = (EditText) findViewById(R.id.confirm_pass);
        changePass = (Button) findViewById(R.id.change_pass_button);
        error = (TextView) findViewById(R.id.error_text);
        error.setVisibility(View.GONE);

        changePass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()){
            case R.id.change_pass_button:
                if(getString(codeField).length() == 0){
                    toast("Please fill confirmation code");
                }else if(getString(passField).length() == 0){
                    toast("Please fill new password");
                }else if(getString(confirmPassField).length() == 0){
                    toast("Please confirm new password");
                }else if(getString(passField).equals(getString(confirmPassField))){
                    toast("Password does not match confirm password, please try again");
                }else{
                    presenter.resetPassword(getString(codeField), getString(passField));
                }
                break;
        }
    }

    private String getString(EditText et){
        return et.getText().toString();
    }

    private void toast(String s){
        Toast.makeText(VerifyForgotActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successResetPassword() {
        error.setVisibility(View.GONE);
        Toast.makeText(VerifyForgotActivity.this, "Your password has been reset", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(VerifyForgotActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void failedResetPassword(String message) {
        error.setText(message);
        error.setVisibility(View.VISIBLE);
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
