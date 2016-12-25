package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.CheckInModel;
import com.imajiku.vegefinder.model.presenter.CheckInPresenter;
import com.imajiku.vegefinder.model.view.CheckInView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Arrays;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener, CheckInView {

    private static final String TAG = "exc";
    private static final String FBTAG = "FBexc";
    private Typeface tf;
    private EditText comment;
    private AppCompatCheckBox fb, twitter;
    private Button checkInBtn;
    private CheckInPresenter presenter;
    private int placeId;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private TwitterAuthClient twitterAuthClient;
    private TwitterFactory twitterFactory;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterKey, twitterSecret);
//        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
        setContentView(R.layout.activity_check_in);

        presenter = new CheckInPresenter(this);
        CheckInModel model = new CheckInModel(presenter);
        presenter.setModel(model);

        setupTwitter();

        tf = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");
        initToolbar("Check In");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        placeId = getIntent().getIntExtra("placeId", -1);
        comment = (EditText) findViewById(R.id.comment);
        fb = (AppCompatCheckBox) findViewById(R.id.checkin_fb);
        twitter = (AppCompatCheckBox) findViewById(R.id.checkin_twit);
        checkInBtn = (Button) findViewById(R.id.btn_check_in);

        fb.setSupportButtonTintList(setColorStateList(
                ContextCompat.getColor(this, R.color.com_facebook_blue)
        ));
        twitter.setSupportButtonTintList(setColorStateList(
                ContextCompat.getColor(this, R.color.twitterBlue)
        ));

        comment.setTypeface(tf);
        fb.setTypeface(tf);
        twitter.setTypeface(tf);
        checkInBtn.setTypeface(tf);

        checkInBtn.setOnClickListener(this);
    }

    private ColorStateList setColorStateList(int color) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled} //enabled
                }, new int[]{color}
        );
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
        hideKeyboard();
        switch (view.getId()) {
            case R.id.btn_check_in:
                presenter.checkIn(CurrentUser.getId(this), placeId, comment.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                if (fb.isChecked()) {
                    if (AccessToken.getCurrentAccessToken() == null) { // not logged in
                        fbLogin();
                    } else { // logged in
                        postToFb(AccessToken.getCurrentAccessToken());
                    }
                } else if (twitter.isChecked()) {
                    new TweetTask().execute();
                }
                break;
        }
    }

    @Override
    public void successCheckIn() {
        Log.e("exc", "successCheckIn!");
    }

    @Override
    public void failedCheckIn(String message) {
        Toast.makeText(CheckInActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
//        if (twitterAuthClient != null) {
//            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
//        }

    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * =========== Facebook ===========
     * from http://stackoverflow.com/questions/29333335/any-example-showing-how-to-login-using-facebook-sdk-4-0-in-android-either-by-usi
     * answer by ManishSB
     * <p/>
     * also from https://developers.facebook.com/docs/graph-api/reference/v2.8/user/feed
     */
    private void fbLogin() {
        callbackManager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager loginManager = LoginManager.getInstance();
//        loginManager.logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        loginManager.logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(TAG, "Success");
                        postToFb(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, error.toString());
                    }
                });
    }

    private void postToFb(AccessToken accessToken) {
        Bundle params = new Bundle();
        params.putString("message", comment.getText().toString());

        new GraphRequest(
                accessToken,
                "/me/feed",
                params,
                HttpMethod.POST, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.e(TAG, "Response: " + response.getRawResponse());
                Toast.makeText(CheckInActivity.this, "Successfully shared on Facebook", Toast.LENGTH_SHORT).show();
                //post to twitter if selected
                if (twitter.isChecked()) {
                    new TweetTask().execute();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        }).executeAsync();
    }

    /**
     * =========== Twitter ===========
     * Using Twitter4J lib
     */
    private void setupTwitter() {
        String twitterKey = getResources().getString(R.string.twitter_api_key);
        String twitterSecret = getResources().getString(R.string.twitter_secret_key);
        String accessToken = getResources().getString(R.string.twitter_access_token_key);
        String accessSecret = getResources().getString(R.string.twitter_access_secret_key);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterKey)
                .setOAuthConsumerSecret(twitterSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessSecret);
        twitterFactory = new TwitterFactory(cb.build());
    }

    /**
     * from http://stackoverflow.com/questions/27267809/using-custom-login-button-with-twitter-fabric
     * answer by Jaydipsinh Zala
     */
    private void twitterLogin() {
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize(this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                Log.e(TAG, "success login twitter");
                TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                Intent intent = new ComposerActivity.Builder(CheckInActivity.this)
                        .session(session)
                        .createIntent();
                startActivity(intent);
            }

            @Override
            public void failure(TwitterException e) {
                Log.e(TAG, "failed login twitter: " + Arrays.toString(e.getStackTrace()));
            }
        });
    }

    /**
     * using Twitter4J lib
     */
    private void sendTweet() {
        Twitter twitter = twitterFactory.getInstance();
        try {
            Status status = twitter.updateStatus(comment.getText().toString());
        } catch (twitter4j.TwitterException e) {
            Log.e(TAG, "failed send tweet: " + Arrays.toString(e.getStackTrace()));
        }
    }

    class TweetTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            sendTweet();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(CheckInActivity.this, "Successfully shared on Twitter", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}
