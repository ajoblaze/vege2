package com.imajiku.vegefinder.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.BookModel;
import com.imajiku.vegefinder.model.presenter.BookPresenter;
import com.imajiku.vegefinder.model.view.BookView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class BookActivity extends AppCompatActivity implements View.OnClickListener, BookView {

    private ImageView banner;
    private TextView restoTitle, dateTitle, timeTitle;
    private EditText date, time, comment;
    private Button btnBook;
    private Typeface tf;
    private BookPresenter presenter;
    private int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        presenter = new BookPresenter(this);
        BookModel model = new BookModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        placeId = getIntent().getIntExtra("placeId", -1);
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");

        banner = (ImageView) findViewById(R.id.resto_img);
        restoTitle = (TextView) findViewById(R.id.resto_title);
        dateTitle = (TextView) findViewById(R.id.date_title);
        timeTitle = (TextView) findViewById(R.id.time_title);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        comment = (EditText) findViewById(R.id.comment);
        btnBook = (Button) findViewById(R.id.btn_book);

        restoTitle.setTypeface(tf);
        dateTitle.setTypeface(tf);
        timeTitle.setTypeface(tf);
        date.setTypeface(tf);
        time.setTypeface(tf);
        comment.setTypeface(tf);
        btnBook.setTypeface(tf);

        date.setOnClickListener(this);
        time.setOnClickListener(this);
        btnBook.setOnClickListener(this);

        restoTitle.setText(title);
        Picasso.with(this)
                .load(image)
                .noFade()
                .fit()
                .centerCrop()
                .into(banner);
    }

    @Override
    public void onClick(View view) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        switch (view.getId()) {
            case R.id.date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(year)
                                        .append("-");
                                if (monthOfYear < 9) {
                                    sb.append("0");
                                }
                                sb.append(monthOfYear + 1)
                                        .append("-");
                                if (dayOfMonth < 10) {
                                    sb.append("0");
                                }
                                sb.append(dayOfMonth);
                                date.setText(sb.toString());
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.time:
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                StringBuilder sb = new StringBuilder();
                                if (hourOfDay < 10) {
                                    sb.append("0");
                                }
                                sb.append(hourOfDay)
                                        .append(":");
                                if (minute < 9) {
                                    sb.append("0");
                                }
                                sb.append(minute + 1);
                                time.setText(sb.toString());
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.btn_book:
                presenter.book(
                        CurrentUser.getId(this),
                        placeId,
                        date.getText().toString(),
                        time.getText().toString(),
                        comment.getText().toString()
                );
                break;
        }
    }

    @Override
    public void successBook() {
        Toast.makeText(BookActivity.this, R.string.success_booking, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failedBook(String s) {
        Toast.makeText(BookActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}

