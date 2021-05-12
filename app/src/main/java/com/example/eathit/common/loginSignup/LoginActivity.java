package com.example.eathit.common.loginSignup;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.eathit.Main2Activity;
import com.example.eathit.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        //animation
        initAnimate();

        //btn-signup
        binding.tvToSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });

        //xu lý btn login
        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
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

        binding.fabFb.setTranslationX(800);
        binding.btnSignUpWithGoogle.setTranslationX(800);
        binding.fabTwitter.setTranslationX(800);

        binding.fabFb.setAlpha((float) 0);
        binding.btnSignUpWithGoogle.setAlpha((float) 0);
        binding.fabTwitter.setAlpha((float) 0);

        binding.fabFb.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(1400).start();
        binding.btnSignUpWithGoogle.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(1600).start();
        binding.fabTwitter.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(1800).start();
    }
}