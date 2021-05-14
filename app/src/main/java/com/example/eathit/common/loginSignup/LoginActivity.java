package com.example.eathit.common.loginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.eathit.activities.Main2Activity;
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