package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LoginActivity extends Activity implements IMyActivity{
    public static final String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]{2,}";
    private static String TAG_FACEBOOK_LOGIN = "Facebook Login";
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView txtValidate;
    //Validation
    private Boolean isValidEmail = false;

    //Firebase and Facebook login
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CallbackManager fbCallbackManager;
    private LoginButton btnLoginFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI();
        setupActions();
        setupLoginFacebook();
        //checkLogin();
    }

    @Override
    public void setupUI() {
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtValidate = (TextView)findViewById(R.id.txtValidate);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLoginFacebook = (LoginButton)findViewById(R.id.btnLoginFacebook);
    }

    @Override
    public void setupActions() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if(!isValidEmail) {
                    Toast.makeText(getApplicationContext(),"Validate email failed",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                */
                //Call loginApi here !
                navigateToPlacesActivity();
            }
        });

        //validation
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Validate here
                txtValidate.setText("");
                String email = txtEmail.getText().toString().trim();
                isValidEmail = (email.matches(emailPattern) && s.length() > 0);
                if(!isValidEmail) {
                    txtValidate.setTextColor(Color.rgb(255,0,0));
                    txtValidate.setText("Invalid email address");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            Log.d(TAG_FACEBOOK_LOGIN, "signInWithCredential success");
                            saveUserInfor(firebaseUser);
                            navigateToPlacesActivity();
                        } else {
                            Log.d(TAG_FACEBOOK_LOGIN, "signInWithCredential failed");
                            saveUserInfor(null);
                        }
                    }
                });

    }
    private void navigateToPlacesActivity() {
        //Do something like "Navigate to second Activity"
        Intent intent = new Intent(LoginActivity.this, PlacesActivity.class);
        startActivity(intent);
    }
    private void saveUserInfor(FirebaseUser facebookUser) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(facebookUser != null) {
            String userId = facebookUser.getUid();
            String email = facebookUser.getEmail();
            editor.putString("userId", userId);
            editor.putString("email", email);
            editor.putString("userType", "facebook");
            editor.putInt("isLoggedIn", 1);
        } else {
            editor.putString("userId", "");
            editor.putString("email", "");
            editor.putString("userType", "");
            editor.putInt("isLoggedIn", 0);
        }
        editor.commit();
    }
    private void checkLogin() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        if(sharedPreferences.getInt("isLoggedIn", 0) == 1) {
            navigateToPlacesActivity();
        }
    }
    private void setupLoginFacebook() {
        fbCallbackManager = CallbackManager.Factory.create();
        btnLoginFacebook.setPermissions(Arrays.asList("email", "public_profile"));
        btnLoginFacebook.registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG_FACEBOOK_LOGIN, "Login success. Result = "+loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG_FACEBOOK_LOGIN, "Login cancelled");
                saveUserInfor(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG_FACEBOOK_LOGIN, "Login failed. Result = "+error);
                saveUserInfor(null);
            }
        });
    }
}
