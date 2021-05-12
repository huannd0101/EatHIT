package com.example.eathit.common.loginSignup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.eathit.R;
import com.example.eathit.common.fragment.FirstSignupFragment;
import com.example.eathit.databinding.ActivitySignupBinding;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getFragment(FirstSignupFragment.newInstance());

    }

    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment,fragment).addToBackStack(FirstSignupFragment.TAG).commit();
    }
}