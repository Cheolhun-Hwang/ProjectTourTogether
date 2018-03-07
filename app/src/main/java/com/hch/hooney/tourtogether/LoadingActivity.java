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
import com.facebook.appevents.AppEventsLogger;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.USER;

import java.util.Arrays;

public class LoadingActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_SIGN_IN = 9001;
    private final String TAG = "LoadingActivity";
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser fUser;
    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
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
        String token = pref.getString("token", null);
        Log.d(TAG, "AUTO : " + auto);
        if(auto == 1){
            //구글 로그인
            //signIn();

            fUser = FirebaseAuth.getInstance().getCurrentUser();

            if(fUser != null && token != null){
                Log.d(TAG, "Firebase Auth : Not Null");

                mAuth.signInWithCustomToken(token);

                after_complete_login();
            }else {
                Log.d(TAG, "Firebase Auth : Null");
                switchLogin();
            }

        }else if (auto ==2){
            //페이스북 로그인

            fUser = FirebaseAuth.getInstance().getCurrentUser();

            if(fUser != null&& token != null){
                Log.d(TAG, "Firebase Auth : Not Null");
                mAuth.signInWithCustomToken(token);

                after_complete_login();
            }else {
                Log.d(TAG, "Firebase Auth : Null");
                switchLogin();
            }

        }else{
            //0은 기본
            switchLogin();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        loading.interrupt();
    }

    private void switchLogin(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void after_complete_login(){
        DAO.user.setUID(fUser.getUid());
        DAO.user.setUNAME(fUser.getDisplayName());
        DAO.user.setUPROFILEIMAGE(fUser.getPhotoUrl().toString());
        DAO.user.setUEMAIL(fUser.getEmail());
        DAO.user.setUPHONE(fUser.getPhoneNumber());
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
