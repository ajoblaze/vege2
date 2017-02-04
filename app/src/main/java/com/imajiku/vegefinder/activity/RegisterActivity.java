package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.RegisterModel;
import com.imajiku.vegefinder.model.presenter.RegisterPresenter;
import com.imajiku.vegefinder.model.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterView {

    private static final int EDIT_TEXT_QTY = 4;
    private EditText[] editText = new EditText[EDIT_TEXT_QTY];
    private Button regis;
    private RegisterPresenter presenter;
    private String TAG = "exc";
    private ProgressBar progressBar;
    private boolean isSubmitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this);
        RegisterModel model = new RegisterModel(presenter);
        presenter.setModel(model);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/VDS_New.ttf");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        TextView title = (TextView) findViewById(R.id.title);
        editText[0] = (EditText)findViewById(R.id.username);
        editText[1] = (EditText)findViewById(R.id.email);
        editText[2] = (EditText)findViewById(R.id.password);
        editText[3] = (EditText)findViewById(R.id.confirm_pass);
        regis = (Button)findViewById(R.id.regis_button);

        title.setTypeface(tf);
        for (int i = 0; i < EDIT_TEXT_QTY; i++) {
            editText[i].setTypeface(tf);
        }
        regis.setTypeface(tf);

        regis.setOnClickListener(this);
        isSubmitting = false;
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.regis_button:
//                successRegister(64);
                validate();
                break;
        }
    }

    private void validate() {
        for (int i = 0; i < EDIT_TEXT_QTY; i++) {
            changeBorder(i, false);
        }
        if(editText[0].getText().toString().length()==0){
            toast("Username must be filled");
            changeBorder(0, true);
        }else if(editText[1].getText().toString().length()==0){
            toast("E-mail must be filled");
            changeBorder(1, true);
        }else if(editText[2].getText().toString().length()==0){
            toast("Password must be filled");
            changeBorder(2, true);
        }else if(editText[3].getText().toString().length()==0){
            toast("Password must be confirmed");
            changeBorder(3, true);
        }else if(!editText[2].getText().toString().equals(editText[3].getText().toString())){
            toast("Password confirmation incorrect");
            changeBorder(2, true);
            changeBorder(3, true);
        }else{
            if(!isSubmitting) {
                isSubmitting = true;
                progressBar.setVisibility(View.VISIBLE);
                presenter.register(
                        editText[0].getText().toString(), //username
                        editText[1].getText().toString(), //email
                        editText[2].getText().toString() //password
                );
            }
        }
    }

    private void toast(String message){
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
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

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void successRegister(int id) {
        progressBar.setVisibility(View.INVISIBLE);
        isSubmitting = false;
        Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, EditProfileActivity.class);
        i.putExtra("userId", id);
        i.putExtra("type", EditProfileActivity.REGISTER);
        startActivity(i);
        finish();
    }

    @Override
    public void failedRegister() {
        progressBar.setVisibility(View.INVISIBLE);
        isSubmitting = false;
        Toast.makeText(RegisterActivity.this, "Failed register", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "failedRegister: ");
    }
}
