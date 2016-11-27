package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.AccountModel;
import com.imajiku.vegefinder.model.presenter.AccountPresenter;
import com.imajiku.vegefinder.model.view.AccountView;
import com.imajiku.vegefinder.pojo.UserProfile;
import com.imajiku.vegefinder.utility.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class AccountActivity extends AppCompatActivity implements AccountView, View.OnClickListener {

    private static final int LAYOUT_QTY = 4;
    private AccountPresenter presenter;
    private LinearLayout[] layouts = new LinearLayout[LAYOUT_QTY];
    private Button edit, googlePlay, logout;
    private UserProfile userProfile;
    private CircularImageView profPic;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        presenter = new AccountPresenter(this);
        AccountModel model = new AccountModel(presenter);
        presenter.setModel(model);

        profPic = (CircularImageView) findViewById(R.id.profPic);
        name = (TextView) findViewById(R.id.name);
        edit = (Button) findViewById(R.id.edit_button);
        layouts[0] = (LinearLayout) findViewById(R.id.bookmark_layout);
        layouts[1] = (LinearLayout) findViewById(R.id.been_here_layout);
        layouts[2] = (LinearLayout) findViewById(R.id.feedback_layout);
        layouts[3] = (LinearLayout) findViewById(R.id.about_layout);
        googlePlay = (Button) findViewById(R.id.google_play);
        logout = (Button) findViewById(R.id.logout);

        edit.setOnClickListener(this);
        for(int i=0;i<LAYOUT_QTY;i++){
            layouts[i].setOnClickListener(this);
        }
        googlePlay.setOnClickListener(this);
        logout.setOnClickListener(this);

        presenter.getProfile(1);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.edit_button:
                i = new Intent(AccountActivity.this, EditProfileActivity.class);
                i.putExtra("type", EditProfileActivity.ACCOUNT);
                i.putExtra("profile", userProfile);
                startActivity(i);
                break;
            case R.id.bookmark_layout:
//                i = new Intent(AccountActivity.this, RestoListActivity.class);
//                i.putExtra("page", RestoListActivity.PAGE_BOOKMARK);
//                startActivity(i);
                break;
            case R.id.been_here_layout:
//                i = new Intent(AccountActivity.this, RestoListActivity.class);
//                i.putExtra("page", RestoListActivity.PAGE_BEENHERE);
//                startActivity(i);
                break;
            case R.id.feedback_layout:
                i = new Intent(AccountActivity.this, SendReportActivity.class);
                i.putExtra("type", SendReportActivity.FEEDBACK);
                i.putExtra("userid", 1);
                i.putExtra("placeId", userProfile.getId());
                startActivity(i);
//                break;
            case R.id.about_layout:
                i = new Intent(AccountActivity.this, AboutActivity.class);
                startActivity(i);
                break;
            case R.id.google_play:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.kompas.ttskompas"));
                startActivity(i);
                break;
            case R.id.logout:
//                presenter.logout();
                i = new Intent(AccountActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }
    }

    @Override
    public void successGetProfile(UserProfile profile) {
        userProfile = profile;
        if(!profile.getImage().isEmpty()) {
            Picasso.with(this)
                    .load(profile.getImage())
                    .noFade()
                    .fit()
                    .centerCrop()
                    .into(profPic);
        }
        name.setText(profile.getName());
    }

    @Override
    public void failedGetProfile() {

    }
}
