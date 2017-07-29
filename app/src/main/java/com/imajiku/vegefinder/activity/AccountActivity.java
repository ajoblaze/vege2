package com.imajiku.vegefinder.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.AccountModel;
import com.imajiku.vegefinder.model.presenter.AccountPresenter;
import com.imajiku.vegefinder.model.request.SortFilterRequest;
import com.imajiku.vegefinder.model.view.AccountView;
import com.imajiku.vegefinder.pojo.Resto;
import com.imajiku.vegefinder.pojo.UserProfile;
import com.imajiku.vegefinder.utility.CircularImageView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.imajiku.vegefinder.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity implements AccountView, View.OnClickListener {

    private static final int LAYOUT_QTY = 7;
    private static final int COUNT_QTY = 2;
    private static final int PERMISSION_UPPER = 900;
    private static final int PERMISSION_NETWORK_REQUEST_CODE = 901;
    private static final int PERMISSION_LOWER = 902;
    private static final String TAG = "exc";
    private static final int EDIT_PROFILE = 11;
    private AccountPresenter presenter;
    private LinearLayout[] layouts = new LinearLayout[LAYOUT_QTY];
    private ImageView[] icons = new ImageView[LAYOUT_QTY];
    private Button edit, googlePlay, logout;
    private UserProfile userProfile;
    private CircularImageView profPic;
    private TextView name, title;
    private Typeface tf;
    private TextView[] counts = new TextView[COUNT_QTY];
    private ProgressBar progressBar;
    private int apiCallCounter = 0;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        presenter = new AccountPresenter(this);
        AccountModel model = new AccountModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), Utility.regFont);

        initToolbar(getString(R.string.title_account));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        profPic = (CircularImageView) findViewById(R.id.profPic);
        name = (TextView) findViewById(R.id.name);
        title = (TextView) findViewById(R.id.user_title);
        edit = (Button) findViewById(R.id.edit_btn);
        layouts[0] = (LinearLayout) findViewById(R.id.bookmark_layout);
        layouts[1] = (LinearLayout) findViewById(R.id.been_here_layout);
        layouts[2] = (LinearLayout) findViewById(R.id.booking_layout);
        layouts[3] = (LinearLayout) findViewById(R.id.history_layout);
        layouts[4] = (LinearLayout) findViewById(R.id.offer_layout);
        layouts[5] = (LinearLayout) findViewById(R.id.feedback_layout);
        layouts[6] = (LinearLayout) findViewById(R.id.about_layout);

        counts[0] = (TextView) findViewById(R.id.bookmark_count);
        counts[1] = (TextView) findViewById(R.id.been_here_count);

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
        for(int i=0;i<COUNT_QTY;i++){
            counts[i].setTypeface(tf);
        }

        for(int i=0;i<LAYOUT_QTY;i++){
            layouts[i].setOnClickListener(this);
        }
        edit.setOnClickListener(this);
        googlePlay.setOnClickListener(this);
        logout.setOnClickListener(this);

        userId = CurrentUser.getId(this);
        presenter.getProfile(userId);
        addApiCounter(true);
        presenter.getBookmarks(new SortFilterRequest(userId, 99));
        addApiCounter(true);
        presenter.getBeenHere(new SortFilterRequest(userId, 99));
        addApiCounter(true);
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.getProfile(userId);
        addApiCounter(true);
        presenter.getBookmarks(new SortFilterRequest(userId, 99));
        addApiCounter(true);
        presenter.getBeenHere(new SortFilterRequest(userId, 99));
        addApiCounter(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addApiCounter(boolean isStart){
        if(isStart){
            apiCallCounter++;
        }else{
            if(apiCallCounter > 0) {
                apiCallCounter--;
            }
        }
        if(apiCallCounter == 1){
            progressBar.setVisibility(View.VISIBLE);
        }else if(apiCallCounter == 0){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.edit_btn:
                i = new Intent(AccountActivity.this, EditProfileActivity.class);
                i.putExtra("type", EditProfileActivity.ACCOUNT);
                i.putExtra("profile", userProfile);
                startActivityForResult(i, EDIT_PROFILE);
                break;
            case R.id.bookmark_layout:
                if(!counts[0].getText().toString().equals("0")) {
                    if(checkLocationAndPermission()) {
                        i = new Intent(AccountActivity.this, RestoListActivity.class);
                        i.putExtra("page", RestoListActivity.PAGE_BOOKMARK);
                        startActivity(i);
                    }

                }
                break;
            case R.id.been_here_layout:
                if(!counts[1].getText().toString().equals("0")) {
                    if(checkLocationAndPermission()) {
                        i = new Intent(AccountActivity.this, RestoListActivity.class);
                        i.putExtra("page", RestoListActivity.PAGE_BEENHERE);
                        startActivity(i);
                    }
                }
                break;
            case R.id.feedback_layout:
                i = new Intent(AccountActivity.this, SendMessageActivity.class);
                i.putExtra("type", SendMessageActivity.FEEDBACK);
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
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Log Out")
                        .setMessage("Do you really want to log out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){
                                presenter.logout();
                                addApiCounter(true);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
        }
    }

    public boolean checkLocationAndPermission() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET
        };
        if (MainActivity.isLocationEnabled(this)) {
            if (hasPermissions(permissions)) {
                return true;
            } else {
                requestPerms(permissions);
            }
        } else {
            //show dialog
            Toast.makeText(AccountActivity.this, "Please turn on location to use this function", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    // CHECK PERMISSION FOR MARSHMALLOW
    private boolean hasPermissions(String[] permissions) {
        int res;
        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    private void requestPerms(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_NETWORK_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isAllowed = false;
        if (requestCode > PERMISSION_UPPER && requestCode < PERMISSION_LOWER) {
            for (int res : grantResults) {
                isAllowed = (res == PackageManager.PERMISSION_GRANTED);
                if (isAllowed) {
                    break;
                }
            }
        }
        if (isAllowed) {
            switch (requestCode) {
                case PERMISSION_NETWORK_REQUEST_CODE:
                    Toast.makeText(AccountActivity.this, "Location permission enabled.", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Toast.makeText(AccountActivity.this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void successGetProfile(UserProfile profile) {
        addApiCounter(false);
        userProfile = profile;
        if(!profile.getImageUrl().isEmpty()) {
            Picasso.with(this)
                    .load(profile.getImageUrl())
                    .noFade()
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.empty_image)
                    .into(profPic);
        }
        name.setText(profile.getFirstName());
    }

    @Override
    public void failedGetProfile() {
        addApiCounter(false);
        Toast.makeText(AccountActivity.this, "Failed getting profile", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLogout() {
        addApiCounter(false);
        Intent i = new Intent(AccountActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Toast.makeText(AccountActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedLogout() {
        addApiCounter(false);
        Toast.makeText(AccountActivity.this, "Failed logout", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successGetBookmarks(ArrayList<Resto> data) {
        addApiCounter(false);
        if(data == null){
            counts[0].setText("0");
        } else {
            counts[0].setText(String.valueOf(data.size()));
        }
    }

    @Override
    public void failedGetBookmarks() {
        addApiCounter(false);
//        Toast.makeText(AccountActivity.this, "Failed getting bookmarks", Toast.LENGTH_SHORT).show();
        counts[0].setText("0");
    }

    @Override
    public void successGetBeenHere(ArrayList<Resto> data) {
        addApiCounter(false);
        if(data == null){
            counts[1].setText("0");
        } else {
            counts[1].setText(String.valueOf(data.size()));
        }
    }

    @Override
    public void failedGetBeenHere() {
        addApiCounter(false);
//        Toast.makeText(AccountActivity.this, "Failed getting been here", Toast.LENGTH_SHORT).show();
        counts[1].setText("0");
    }
}
