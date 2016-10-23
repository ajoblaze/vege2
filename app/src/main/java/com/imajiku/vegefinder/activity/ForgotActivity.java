package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.ForgotModel;
import com.imajiku.vegefinder.model.presenter.ForgotPresenter;
import com.imajiku.vegefinder.model.presenter.view.ForgotView;
import com.imajiku.vegefinder.utility.Utility;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener, ForgotView {

    private static final String TAG = "exc";
    private EditText email;
    private Button reset;
    private TextView register, contactUs;
    private ForgotPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        presenter = new ForgotPresenter(this);
        ForgotModel model = new ForgotModel(presenter);
        presenter.setModel(model);
        email = (EditText) findViewById(R.id.username_email);
        reset = (Button) findViewById(R.id.reset_btn);
        register = (TextView) findViewById(R.id.register);
        contactUs = (TextView) findViewById(R.id.contact_us);

        reset.setOnClickListener(this);
        register.setOnClickListener(this);
        contactUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_btn:
                String emailContent = email.getText().toString();
                if(emailContent.length()==0){
                    Toast.makeText(ForgotActivity.this, "Please insert email or username", Toast.LENGTH_SHORT).show();
                }else{
                    new SendForgetCodeTask(emailContent).execute();
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

    private Context self(){
        return ForgotActivity.this;
    }

    class SendForgetCodeTask extends AsyncTask<Void, Void, Void> {

        private String emailTo, code;

        public SendForgetCodeTask(String emailTo) {
            this.emailTo = emailTo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            code = Utility.generateCode();
            Utility.sendEmail(self(), emailTo, "Subject des", "Code des: "+code);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(ForgotActivity.this, VerifyForgotActivity.class);
            i.putExtra("code", code);
            startActivity(i);
        }
    }
}
