package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.AccountModel;
import com.imajiku.vegefinder.model.presenter.AccountPresenter;
import com.imajiku.vegefinder.model.view.AccountView;
import com.imajiku.vegefinder.pojo.UserProfile;
import com.imajiku.vegefinder.utility.CircularImageView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.squareup.picasso.Picasso;

public class AccountActivity extends AppCompatActivity implements AccountView, View.OnClickListener {

    private static final int LAYOUT_QTY = 7;
    private static final String TAG = "exc";
    private AccountPresenter presenter;
    private LinearLayout[] layouts = new LinearLayout[LAYOUT_QTY];
    private ImageView[] icons = new ImageView[LAYOUT_QTY];
    private Button edit, googlePlay, logout;
    private UserProfile userProfile;
    private CircularImageView profPic;
    private TextView name, title;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        presenter = new AccountPresenter(this);
        AccountModel model = new AccountModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");

        initToolbar(getString(R.string.title_account));

        profPic = (CircularImageView) findViewById(R.id.profPic);
        name = (TextView) findViewById(R.id.name);
        title = (TextView) findViewById(R.id.user_title);
        edit = (Button) findViewById(R.id.edit_button);
        layouts[0] = (LinearLayout) findViewById(R.id.bookmark_layout);
        layouts[1] = (LinearLayout) findViewById(R.id.been_here_layout);
        layouts[2] = (LinearLayout) findViewById(R.id.booking_layout);
        layouts[3] = (LinearLayout) findViewById(R.id.history_layout);
        layouts[4] = (LinearLayout) findViewById(R.id.offer_layout);
        layouts[5] = (LinearLayout) findViewById(R.id.feedback_layout);
        layouts[6] = (LinearLayout) findViewById(R.id.about_layout);
        icons[0] = (ImageView) findViewById(R.id.icon_bookmark);
        icons[1] = (ImageView) findViewById(R.id.icon_beenhere);
        icons[2] = (ImageView) findViewById(R.id.icon_booking);
        icons[3] = (ImageView) findViewById(R.id.icon_history);
        icons[4] = (ImageView) findViewById(R.id.icon_offer);
        icons[5] = (ImageView) findViewById(R.id.icon_feedback);
        icons[6] = (ImageView) findViewById(R.id.icon_about);
        googlePlay = (Button) findViewById(R.id.google_play);
        logout = (Button) findViewById(R.id.logout);

        for(int i = 0; i< LAYOUT_QTY; i++){
            int color;
            if(i>1 && i<5){
                color = R.color.selectedGrayDarker;
            }else{
                color = R.color.accentGreenBtn;
            }
            icons[i].setColorFilter(ContextCompat.getColor(this, color));
        }

        name.setTypeface(tf);
        title.setTypeface(tf);
        edit.setTypeface(tf);
        googlePlay.setTypeface(tf);
        logout.setTypeface(tf);
        for(int i = 0; i < LAYOUT_QTY; i++){
            LinearLayout ll = (LinearLayout) layouts[i].getChildAt(0);
            for(int ii = 0; ii < ll.getChildCount(); ii++){
                if(ll.getChildAt(ii) instanceof TextView){
                    TextView tv = (TextView) ll.getChildAt(ii);
                    tv.setTypeface(tf);
                }
            }
        }

        edit.setOnClickListener(this);
        for(int i=0;i<LAYOUT_QTY;i++){
            layouts[i].setOnClickListener(this);
        }
        googlePlay.setOnClickListener(this);
        logout.setOnClickListener(this);

        presenter.getProfile(CurrentUser.getId());
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
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
                i = new Intent(AccountActivity.this, RestoListActivity.class);
                i.putExtra("page", RestoListActivity.PAGE_BOOKMARK);
                startActivity(i);
                break;
            case R.id.been_here_layout:
                i = new Intent(AccountActivity.this, RestoListActivity.class);
                i.putExtra("page", RestoListActivity.PAGE_BEENHERE);
                startActivity(i);
                break;
            case R.id.feedback_layout:
                i = new Intent(AccountActivity.this, SendReportActivity.class);
                i.putExtra("type", SendReportActivity.FEEDBACK);
                startActivity(i);
                break;
            case R.id.about_layout:
                i = new Intent(AccountActivity.this, AboutActivity.class);
                startActivity(i);
                break;
            case R.id.google_play:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.kompas.ttskompas"));
                startActivity(i);
                break;
            case R.id.logout:
                presenter.logout();
                break;
        }
    }

    @Override
    public void successGetProfile(UserProfile profile) {
        userProfile = profile;
        Log.e(TAG, profile.getImage());
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

    @Override
    public void successLogout() {
        Intent i = new Intent(AccountActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void failedLogout() {

    }
}
