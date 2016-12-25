package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.RegisterModel;
import com.imajiku.vegefinder.model.presenter.RegisterPresenter;
import com.imajiku.vegefinder.model.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterView {

    private EditText username, pass, confirmPass, email;
    private Button regis;
    private RegisterPresenter presenter;
    private String TAG = "exc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this);
        RegisterModel model = new RegisterModel(presenter);
        presenter.setModel(model);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        TextView title = (TextView) findViewById(R.id.title);
        username = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        confirmPass = (EditText)findViewById(R.id.confirm_pass);
        email = (EditText)findViewById(R.id.email);
        regis = (Button)findViewById(R.id.regis_button);

        title.setTypeface(tf);
        username.setTypeface(tf);
        pass.setTypeface(tf);
        confirmPass.setTypeface(tf);
        email.setTypeface(tf);
        regis.setTypeface(tf);

        regis.setOnClickListener(this);
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
        if(username.getText().toString().length()==0){
            toast("Username must be filled");
        }else if(pass.getText().toString().length()==0){
            toast("Password must be filled");
        }else if(confirmPass.getText().toString().length()==0){
            toast("Password must be confirmed");
        }else if(email.getText().toString().length()==0){
            toast("E-mail must be filled");
        }else if(!confirmPass.getText().toString().equals(confirmPass.getText().toString())){
            toast("Password confirmation incorrect");
        }else{
            presenter.register(
                    username.getText().toString(),
                    email.getText().toString(),
                    pass.getText().toString()
            );
        }
    }

    private void toast(String message){
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, EditProfileActivity.class);
        i.putExtra("userId", id);
        i.putExtra("type", EditProfileActivity.REGISTER);
        startActivity(i);
    }

    @Override
    public void failedRegister() {
        Log.e(TAG, "failedRegister: ");
    }
}
