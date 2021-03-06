package com.imajiku.vegefinder.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.AccessToken;
//import com.facebook.AccessTokenTracker;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.model.model.LoginModel;
import com.imajiku.vegefinder.model.presenter.LoginPresenter;
import com.imajiku.vegefinder.model.view.LoginView;
import com.imajiku.vegefinder.utility.CurrentUser;
import com.imajiku.vegefinder.utility.Utility;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        LoginView,
        GoogleApiClient.OnConnectionFailedListener {

    public static final int NORMAL = 0;
    public static final int FB = 1;
    public static final int GPLUS = 2;
    private static final int RC_SIGN_IN = 7;
    private Button login;
    private EditText username, pass;
    private LinearLayout skip;
    private TextView register, forgot, skipText;
    private Button fbLogin, gplusLogin;
    private LoginPresenter presenter;
//    private AccessTokenTracker accessTokenTracker;
    private GoogleApiClient googleApiClient;
    private String TAG = "exc";
//    private CallbackManager callbackManager;
    private ProgressBar progressBar;
    private boolean isLoggingIn;
    private int apiCallCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.imajiku.vegefinder",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.e("excKeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

        setContentView(R.layout.activity_login);
        initToolbar();
        presenter = new LoginPresenter(this);
        LoginModel model = new LoginModel(presenter);
        presenter.setModel(model);

        Typeface tf = Typeface.createFromAsset(getAssets(), Utility.regFont);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if (presenter.getCurrentLogin() > -1 && CurrentUser.getId(this) > -1) {
            successLogin(CurrentUser.getId(this), "");
        }

//        setupFbLogin();
        gplusLogin();

        username = (EditText) findViewById(R.id.username_field);
        pass = (EditText) findViewById(R.id.password_field);

        login = (Button) findViewById(R.id.login_button);
        fbLogin = (Button) findViewById(R.id.fb_button);
        gplusLogin = (Button) findViewById(R.id.gplus_button);
        skip = (LinearLayout) findViewById(R.id.skip_login);
        skipText = (TextView) findViewById(R.id.skip_text);
        forgot = (TextView) findViewById(R.id.forgot_password);
        register = (TextView) findViewById(R.id.register);

        username.setTypeface(tf);
        pass.setTypeface(tf);

        login.setTypeface(tf);
        fbLogin.setTypeface(tf);
        gplusLogin.setTypeface(tf);
        skipText.setTypeface(tf);
        forgot.setTypeface(tf);
        register.setTypeface(tf);

        // TODO: remove this
        fbLogin.setVisibility(View.GONE);
        gplusLogin.setVisibility(View.GONE);

        login.setOnClickListener(this);
        fbLogin.setOnClickListener(this);
        gplusLogin.setOnClickListener(this);
        skip.setOnClickListener(this);
        forgot.setOnClickListener(this);
        register.setOnClickListener(this);
        isLoggingIn = false;
    }

    public void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
        }
        ImageView iv = (ImageView) mToolbar.findViewById(R.id.toolbar_image);
    }

    private String getString(EditText et) {
        return et.getText().toString();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Exit")
                .setMessage("Do you really want to exit application?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.login_button:
                if(!isLoggingIn) {
                    if (!getString(username).equals("") && !getString(pass).equals("")) {
                        isLoggingIn = true;
                        addApiCounter(true);
                        presenter.login(NORMAL, getString(username), getString(pass));
                    }
                }
                break;
            case R.id.skip_login: {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("isLogin", false);
                startActivity(i);
            }
            break;
            case R.id.forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.fb_button:
//                if (!checkIfFbLoggedIn()) {
//                    LoginManager.getInstance().logInWithReadPermissions(
//                            LoginActivity.this,
//                            Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
//                    );
//                } else {
//                    LoginManager.getInstance().logOut();
//                    presenter.logout();
//                }
                break;
            case R.id.gplus_button:
                signIn();
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
    public void successLogin(int userId, String message) {
        addApiCounter(false);
        if(!message.isEmpty()) {
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        }
        isLoggingIn = false;
        CurrentUser.setId(this, userId);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("isLogin", true);
        i.putExtra("loginMethod", "normal");
        startActivity(i);
        finish();
    }

    @Override
    public void failedLogin(String message) {
        addApiCounter(false);
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        isLoggingIn = false;
        Toast.makeText(LoginActivity.this, "Your email has not been verified", Toast.LENGTH_SHORT).show();
    }

    /*
    public boolean checkIfFbLoggedIn() {
        // check if user is logged in
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
//            Log.e(TAG, "User ID: "
//                    + accessToken.getUserId()
//                    + "\n" +
//                    "Auth Token: "
//                    + accessToken.getToken());
            return true;
        }
//        Log.e(TAG, "not logged in");
        return false;
    }

    public void setupFbLogin() {
        callbackManager = CallbackManager.Factory.create();

        // listen to login
//        LoginButton btn = (LoginButton) findViewById(R.id.fb_button);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String s = "User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken();
                accessTokenTracker.startTracking(); // start fb logout listener
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("isLogin", false);
                i.putExtra("loginMethod", "fb");
                startActivity(i);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

//         listen to logout
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                }
            }
        };
    }
    */

    public void gplusLogin() {// Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
//        presenter.login(GPLUS);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        presenter.logout();
//                        updateUI(false);
                    }
                });
        presenter.logout();
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            String s = "display name: " + acct.getDisplayName();

            String personName = acct.getDisplayName();
//            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            s += ", name: " + personName + ", email: " + email;

//            Glide.with(getApplicationContext()).load(personPhotoUrl)
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfilePic);

//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

//    private void updateUI(boolean isSignedIn) {
//        if (isSignedIn) {
//            signInButton.setVisibility(View.GONE);
//            signOutButton.setVisibility(View.VISIBLE);
//            revokeAccessButton.setVisibility(View.VISIBLE);
////            llProfileLayout.setVisibility(View.VISIBLE);
//        } else {
//            signInButton.setVisibility(View.VISIBLE);
//            signOutButton.setVisibility(View.GONE);
//            revokeAccessButton.setVisibility(View.GONE);
////            llProfileLayout.setVisibility(View.GONE);
//        }
//    }
}
