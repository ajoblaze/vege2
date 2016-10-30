package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imajiku.vegefinder.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, pass, confirmPass, email;
    private Button regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        confirmPass = (EditText)findViewById(R.id.confirm_pass);
        email = (EditText)findViewById(R.id.email);
        regis = (Button)findViewById(R.id.regis_button);

        regis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.regis_button:
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
            Intent i = new Intent(this, RegisterProfileActivity.class);
            i.putExtra("username", username.getText().toString());
            i.putExtra("email", email.getText().toString());
            i.putExtra("password", pass.getText().toString());
            startActivity(i);
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
}
