package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.MessageModel;
import com.imajiku.vegefinder.model.presenter.MessagePresenter;
import com.imajiku.vegefinder.model.view.MessageView;

public class SendReportActivity extends AppCompatActivity implements View.OnClickListener, MessageView {

    private EditText subject, message;
    private Button submit;
    private int restoId, userId;
    private MessagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);

        restoId = getIntent().getIntExtra("restoId", -1);
        userId = getIntent().getIntExtra("userId", -1);
        if(restoId == -1 || userId == -1){
            throw new RuntimeException("send userid and placeid");
        }

        presenter = new MessagePresenter(this);
        MessageModel model = new MessageModel(presenter);
        presenter.setModel(model);

        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        hideKeyboard();
        switch (view.getId()) {
            case R.id.submit:
                break;
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
    public void successSendContactUs() {

    }

    @Override
    public void successSendReview() {

    }

    @Override
    public void successSendReport() {

    }
}
