package com.hch.hooney.tourtogether;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.USER;

import java.util.Arrays;

public class LoadingActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_SIGN_IN = 9001;
    private final String TAG = "LoadingActivity";
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private ProgressBar progressBar;
    private Thread loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        init();
        event();

    }

    private void init(){
        progressBar = (ProgressBar) findViewById(R.id.loading_progress);
        progressBar.setVisibility(View.VISIBLE);
        DAO.mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void event(){

    }

    private Thread init_loading_thread(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.adsmode_id));

                    DAO.setCountryAndLanguage(getApplicationContext());
                    DAO.init_static();
                    DAO.init_handler(getApplicationContext());
                    DAO.load_bookmarkSpot();
                    DAO.load_bookmarkCourse();
                    getIsPushPreferences();

                    PackageInfo info = getApplication().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
                    DAO.nowVersion=info.versionName;

                    Thread.sleep(1000);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            checkAutoLogin();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loading = init_loading_thread();
        loading.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void checkAutoLogin(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        int auto = pref.getInt("autologin", 0);
        if(auto == 1){
            //구글 로그인
            signIn();
        }else if (auto ==2){
            //페이스북 로그인
            facebookLoginOnClick();
        }else{
            //0은 기본
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        loading.interrupt();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        this.startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Must be handled in Activity. When intent is started from fragment, requestCode is modified.
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            firebaseAuthWithGoogle(result.getSignInAccount());
        } else {
            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        DAO.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            DAO.fUser = DAO.mAuth.getCurrentUser();
                            after_complete_login();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                    }
                });
    }



    private void facebookLoginOnClick(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(LoadingActivity.this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult result) {
                handleFacebookAccessToken(result.getAccessToken());
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        DAO.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            DAO.fUser = DAO.mAuth.getCurrentUser();
                            after_complete_login();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoadingActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void after_complete_login(){
        DAO.user.setUID(DAO.fUser.getUid());
        DAO.user.setUNAME(DAO.fUser.getDisplayName());
        DAO.user.setUPROFILEIMAGE(DAO.fUser.getPhotoUrl().toString());
        DAO.user.setUEMAIL(DAO.fUser.getEmail());
        DAO.user.setUPHONE(DAO.fUser.getPhoneNumber());
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    // 값 불러오기
    private void getIsPushPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        DAO.isPush = pref.getBoolean("ispush", true);
    }
}
