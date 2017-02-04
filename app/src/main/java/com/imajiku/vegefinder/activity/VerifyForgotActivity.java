package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.VerifyForgotModel;
import com.imajiku.vegefinder.model.presenter.VerifyForgotPresenter;
import com.imajiku.vegefinder.model.view.VerifyForgotView;

public class VerifyForgotActivity extends AppCompatActivity implements View.OnClickListener, VerifyForgotView {

    private static final int EDIT_TEXT_QTY = 3;
    private EditText[] editText = new EditText[EDIT_TEXT_QTY];
    private Button changePass;
    private VerifyForgotPresenter presenter;
    private TextView title, subtitle, error;
    private Typeface tf;
    private ProgressBar progressBar;
    private boolean isSubmitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_forgot);
        presenter = new VerifyForgotPresenter(this);
        VerifyForgotModel model = new VerifyForgotModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        title = (TextView) findViewById(R.id.vf_title);
        subtitle = (TextView) findViewById(R.id.vf_subtitle);
        editText[0] = (EditText) findViewById(R.id.code);
        editText[1] = (EditText) findViewById(R.id.password);
        editText[2] = (EditText) findViewById(R.id.confirm_pass);
        changePass = (Button) findViewById(R.id.change_pass_button);

        title.setTypeface(tf);
        subtitle.setTypeface(tf);
        for (int i = 0; i < EDIT_TEXT_QTY; i++) {
            editText[i].setTypeface(tf);
        }
        changePass.setTypeface(tf);

        error = (TextView) findViewById(R.id.error_text);
        error.setVisibility(View.GONE);

        changePass.setOnClickListener(this);
        isSubmitting = false;
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        for (int i = 0; i < EDIT_TEXT_QTY; i++) {
            changeBorder(i, false);
        }
        switch (v.getId()){
            case R.id.change_pass_button:
                if(getString(editText[0]).length() == 0){
                    toast("Please fill confirmation code");
                    changeBorder(0, true);
                }else if(getString(editText[1]).length() == 0){
                    toast("Please fill new password");
                    changeBorder(1, true);
                }else if(getString(editText[2]).length() == 0){
                    toast("Please confirm new password");
                    changeBorder(2, true);
                }else if(!getString(editText[1]).equals(getString(editText[2]))){
                    toast("Password does not match confirm password, please try again");
                    changeBorder(1, true);
                    changeBorder(2, true);
                }else{
                    if(!isSubmitting) {
                        isSubmitting = true;
                        progressBar.setVisibility(View.VISIBLE);
                        presenter.resetPassword(getString(editText[0]), getString(editText[1]));
                    }
                }
                break;
        }
    }

    private void changeBorder(int idx, boolean isError){
        int id;
        EditText et = editText[idx];
        if(isError){
            id = R.drawable.rounded_error;
        }else{
            id = R.drawable.selector_rounded_edittext;
        }
        et.setBackground(ContextCompat.getDrawable(this, id));
    }

    private String getString(EditText et){
        return et.getText().toString();
    }

    private void toast(String s){
        Toast.makeText(VerifyForgotActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successResetPassword() {
        progressBar.setVisibility(View.INVISIBLE);
        error.setVisibility(View.GONE);
        isSubmitting = false;
        Toast.makeText(VerifyForgotActivity.this, "Your password has been reset", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(VerifyForgotActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void failedResetPassword(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        isSubmitting = false;
        Toast.makeText(VerifyForgotActivity.this, "Failed reset password", Toast.LENGTH_SHORT).show();
//        error.setText(message);
//        error.setVisibility(View.VISIBLE);
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
