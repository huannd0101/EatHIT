package com.example.eathit.common.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.eathit.R;

import com.example.eathit.common.loginSignup.LoginActivity;
import com.example.eathit.databinding.FragmentFirstSignupBinding;
import java.util.ArrayList;
import java.util.Objects;


public class FirstSignupFragment extends Fragment {

    public static final String TAG = FirstSignupFragment.class.getName();
    FragmentFirstSignupBinding binding;




    public static FirstSignupFragment newInstance() {
        FirstSignupFragment fragment = new FirstSignupFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFirstSignupBinding.inflate(inflater, container, false);

        //animation
        initAnimate();
        //chuyển về login
        binding.btnBackSignup.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });

        binding.btnLoginOfSignup.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });

        //chuyển sang second signup
        binding.btnNextToSecondSignUp.setOnClickListener(v -> {
            moveToSecondFragment();
        });


        return binding.getRoot();
    }

    private void moveToSecondFragment() {
        String fullName = Objects.requireNonNull(binding.edtFullName.getText()).toString().trim();
        String email = Objects.requireNonNull(binding.edtEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.edtPassword.getText()).toString().trim();
        String passwordAgain = Objects.requireNonNull(binding.edtPasswordAgain.getText()).toString().trim();

        if(fullName.isEmpty()){
            binding.tilFullName.setError("You have not entered fullName");
            return;
        }else if(email.isEmpty()){
            binding.tilFullName.setError(null);
            binding.tilEmail.setError("You have not entered email");
            return;
        }else if(password.isEmpty()){
            binding.tilEmail.setError(null);
            binding.tilPassword.setError("You have not entered password");
            return;
        }else if(passwordAgain.isEmpty()){
            binding.tilPassword.setError(null);
            binding.tilPasswordAgain.setError("You have not entered password again");
            return;
        }else if(!password.equals(passwordAgain)){
            binding.tilPasswordAgain.setError("Password is not correct");
            return;
        }else {
            binding.tilPasswordAgain.setError(null);
        }

        //send data to activity
        ArrayList<String> dataSignUp = new ArrayList<>();
        dataSignUp.add(fullName);
        dataSignUp.add(email);
        dataSignUp.add(password);
        dataSignUp.add(passwordAgain);


        Fragment fragment = SecondSignupFragment.newInstance(dataSignUp);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layout_fragment, fragment).addToBackStack(SecondSignupFragment.TAG).commit();
    }























    private void initAnimate() {
        binding.tilFullName.setTranslationY(800);
        binding.tilEmail.setTranslationY(800);
        binding.tilPassword.setTranslationY(800);
        binding.tilPasswordAgain.setTranslationY(800);
        binding.btnNextToSecondSignUp.setTranslationY(800);
        binding.btnLoginOfSignup.setTranslationY(800);

        binding.tilFullName.setAlpha(0);
        binding.tilEmail.setAlpha(0);
        binding.tilPassword.setAlpha(0);
        binding.tilPasswordAgain.setAlpha(0);
        binding.btnNextToSecondSignUp.setAlpha(0);
        binding.btnLoginOfSignup.setAlpha(0);

        binding.tilFullName.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(600).start();
        binding.tilEmail.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(800).start();
        binding.tilPassword.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1100).start();
        binding.tilPasswordAgain.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1300).start();
        binding.btnNextToSecondSignUp.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1500).start();
        binding.btnLoginOfSignup.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1800).start();

        binding.titleCreateAccount.setTranslationX(-800);
        binding.titleCreateAccount.setAlpha(0);
        binding.titleCreateAccount.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(500).start();

        binding.tvProcess.setTranslationX(800);
        binding.tvProcess.setAlpha(0);
        binding.tvProcess.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(500).start();
    }

}