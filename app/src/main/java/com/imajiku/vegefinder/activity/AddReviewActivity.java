package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.MessageModel;
import com.imajiku.vegefinder.model.presenter.MessagePresenter;
import com.imajiku.vegefinder.model.view.MessageView;

public class AddReviewActivity extends AppCompatActivity implements View.OnClickListener, MessageView {

    private SeekBar seekBar;
    private TextView rate, title, comment;
    private Button submit;
    private MessagePresenter presenter;
    private int restoId, userId;
    private int currRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        restoId = getIntent().getIntExtra("restoId", -1);
        userId = getIntent().getIntExtra("userId", -1);
        if(restoId == -1 || userId == -1){
            throw new RuntimeException("send userid and placeid");
        }

        presenter = new MessagePresenter(this);
        MessageModel model = new MessageModel(presenter);
        presenter.setModel(model);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        rate = (TextView) findViewById(R.id.rate);
        title = (TextView) findViewById(R.id.title);
        comment = (TextView) findViewById(R.id.comment);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currRate = i;
                rate.setText(String.valueOf(currRate));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        hideKeyboard();
        switch (view.getId()) {
            case R.id.submit: {
                presenter.sendReview(
                        userId,
                        restoId,
                        currRate,
                        title.getText().toString(),
                        comment.getText().toString()
                );
            }
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
