package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imajiku.vegefinder.R;

public class VerifyForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText codeField, passField, confirmPassField;
    private Button changePass;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_forgot);
        code = getIntent().getStringExtra("code");
        codeField = (EditText) findViewById(R.id.code);
        passField = (EditText) findViewById(R.id.password);
        confirmPassField = (EditText) findViewById(R.id.confirm_pass);
        changePass = (Button) findViewById(R.id.change_pass_button);

        changePass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_pass_button:
                if(getString(codeField).length() == 0){
                    toast("Please fill confirmation code");
                }else if(!getString(codeField).equals(code)){
                    toast("Confirmation code incorrect, please try again");
                }else if(getString(passField).length() == 0){
                    toast("Please fill new password");
                }else if(getString(confirmPassField).length() == 0){
                    toast("Please confirm new password");
                }else if(getString(passField).equals(getString(confirmPassField))){
                    toast("Password does not match confirm password, please try again");
                }else{
                    // TODO: send to server (treat as success log in)
                    Intent intent = new Intent(VerifyForgotActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
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
}
