package com.example.eathit.common.loginSignup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.eathit.R;
import com.example.eathit.activities.Main2Activity;
import com.example.eathit.databinding.ActivityLoginBinding;
import com.example.eathit.modules.User;
import com.example.eathit.utilities.Constants;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.FacebookSignatureValidator;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //sign up with google
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        //animation
        initAnimate();


        ///////////////////////////////////////Facebook
        //tạo mã hash key cho fb
//        printHashKey(LoginActivity.this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
//        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
//                        Toast.makeText(LoginActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        firebaseAuthWithFacebook(loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LoginActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(LoginActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));


        //
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //btn-signup
        binding.tvToSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });

        //btn-forgot password
        binding.tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng chưa phát triển :v", Toast.LENGTH_SHORT).show();
        });

        //xu lý btn login
        binding.btnLogin.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.edtUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.edtPassword.getText()).toString();
            if (username.isEmpty() && password.isEmpty()) {
                binding.tilUsername.setError("You have not entered username");
                binding.tilPassword.setError("You have not entered password");
                return;
            } else {
                binding.tilUsername.setError(null);
                binding.tilPassword.setError(null);
            }

            if (username.isEmpty()) {
                binding.tilUsername.setError("You have not entered username");
                binding.tilPassword.setError(null);
                return;
            } else {
                binding.tilUsername.setError(null);
            }

            if (password.isEmpty()) {
                binding.tilPassword.setError("You have not entered password");
                return;
            } else {
                binding.tilUsername.setError(null);
            }

            //bật dialog
            progressDialog.show();

            auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(task -> {
                        //tắt dialog
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        //ktra nếu đã đăng nhập rồi thì chuyển đến main activity
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
            startActivity(intent);
        }

        //xử lý button click sign in with google
        binding.btnSignUpWithGoogle.setOnClickListener(v -> signInWithGoogle());

    }
    ////////////////////////login facebook
    //tự động tạo ra mã hash key
    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }

    private void firebaseAuthWithFacebook(String idToken){
        AuthCredential credential = FacebookAuthProvider.getCredential(idToken);
        auth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                // Sign in success, update UI with the signed-in user's information
                FirebaseUser user = auth.getCurrentUser();
                //lưu vào database
                User users = new User();
                if(user != null){
                    users.setUserId(user.getUid());
                    users.setFullName(user.getDisplayName());
                    users.setProfilePic(Objects.requireNonNull(user.getPhotoUrl()).toString());
                    database.getReference().child("Users").child(user.getUid()).setValue(users);
                }
                //kết thúc lưu vào database
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent);

                Toast.makeText(LoginActivity.this, "Sign in with Facebook", Toast.LENGTH_SHORT).show();
            }else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "signInWithCredential:failure", task.getException());

                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("fbbbbb", Objects.requireNonNull(task.getException()).getMessage());
                Snackbar.make(binding.getRoot(), "Authentication failed.", Snackbar.LENGTH_LONG).show();

            }
        });




    }


    ///////////////////////log in with google
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }



    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();

                        //lưu vào database
                        User users = new User();
                        if(user != null){
                            users.setUserId(user.getUid());
                            users.setFullName(user.getDisplayName());
                            users.setProfilePic(Objects.requireNonNull(user.getPhotoUrl()).toString());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);
                        }
                        //kết thúc lưu vào database

                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                        startActivity(intent);

                        Toast.makeText(LoginActivity.this, "Sign in with Google", Toast.LENGTH_SHORT).show();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCredential:failure", task.getException());

                        Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                        Snackbar.make(binding.getRoot(), "Authentication failed.", Snackbar.LENGTH_LONG).show();

                    }
                });
    }

    private void initAnimate() {
        binding.tvAppName.setTranslationY(-600);
        binding.imgApp.setTranslationY(-600);
        binding.tilUsername.setTranslationY(800);
        binding.tilPassword.setTranslationY(800);
        binding.linearLayout2.setTranslationY(800);
        binding.btnLogin.setTranslationY(800);

        binding.tvAppName.setAlpha(0);
        binding.imgApp.setAlpha(0);
        binding.tilUsername.setAlpha(0);
        binding.tilPassword.setAlpha(0);
        binding.linearLayout2.setAlpha(0);
        binding.btnLogin.setAlpha(0);

        binding.tvAppName.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(600).start();
        binding.imgApp.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(800).start();
        binding.tilUsername.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1100).start();
        binding.tilPassword.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1300).start();
        binding.linearLayout2.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1500).start();
        binding.btnLogin.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1800).start();

//        binding.fabFb.setTranslationX(800);
        binding.btnSignUpWithGoogle.setTranslationX(800);
        binding.fabTwitter.setTranslationX(800);

//        binding.fabFb.setAlpha((float) 0);
        binding.btnSignUpWithGoogle.setAlpha((float) 0);
        binding.fabTwitter.setAlpha((float) 0);

//        binding.fabFb.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(1400).start();
        binding.btnSignUpWithGoogle.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(1600).start();
        binding.fabTwitter.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(1800).start();
    }
}