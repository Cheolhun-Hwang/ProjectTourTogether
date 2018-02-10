package com.hch.hooney.tourtogether;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hch.hooney.tourtogether.DAO.DAO;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";

    private CallbackManager callbackManager;
    private ImageButton facebookLogin;

    private DatabaseReference rootRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setEvent();

        if(DAO.fUser !=null){
            DAO.user.setUID(DAO.fUser.getUid());
            DAO.user.setUNAME(DAO.fUser.getDisplayName());
            DAO.user.setUPROFILEIMAGE(DAO.fUser.getPhotoUrl().toString());
            DAO.user.setUEMAIL(DAO.fUser.getEmail());
            DAO.user.setUPHONE(DAO.fUser.getPhoneNumber());

            userRef.child(DAO.user.getUID()).setValue(DAO.user);

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private void init(){
        facebookLogin = (ImageButton) findViewById(R.id.facebook_imageBTN);
        DAO.mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("user");
    }

    private void setEvent(){
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLoginOnClick();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void facebookLoginOnClick(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult result) {
                facebookLogin.setVisibility(View.GONE);
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

                            DAO.user.setUID(DAO.fUser.getUid());
                            DAO.user.setUNAME(DAO.fUser.getDisplayName());
                            DAO.user.setUPROFILEIMAGE(DAO.fUser.getPhotoUrl().toString());
                            DAO.user.setUEMAIL(DAO.fUser.getEmail());
                            DAO.user.setUPHONE(DAO.fUser.getPhoneNumber());
                            userRef.child(DAO.user.getUID()).setValue(DAO.user);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
