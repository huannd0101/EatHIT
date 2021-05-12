package com.example.eathit.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.eathit.databinding.FragmentThirthSignupBinding;


public class ThirdSignupFragment extends Fragment {
    public static final String TAG = FirstSignupFragment.class.getName();
    FragmentThirthSignupBinding binding;



    public static ThirdSignupFragment newInstance() {
        ThirdSignupFragment fragment = new ThirdSignupFragment();
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
        binding = FragmentThirthSignupBinding.inflate(inflater, container, false);
        //animation
        initAnimate();

        //back to second signup fragment
        binding.btnBackSecondSignup.setOnClickListener(v -> {
            if(getFragmentManager() != null){
                getFragmentManager().popBackStack();
            }
        });

        return binding.getRoot();
    }
    private void initAnimate() {
        binding.cpp.setTranslationY(800);
        binding.tilPhoneNumber.setTranslationY(800);
        binding.btnSignUp.setTranslationY(800);
        binding.btnLogin.setTranslationY(800);

        binding.cpp.setAlpha(0);
        binding.tilPhoneNumber.setAlpha(0);
        binding.btnSignUp.setAlpha(0);
        binding.btnLogin.setAlpha(0);

        binding.cpp.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(600).start();
        binding.tilPhoneNumber.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(800).start();
        binding.btnSignUp.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1100).start();
        binding.btnLogin.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1300).start();

        binding.titleCreateAccount.setTranslationX(-800);
        binding.titleCreateAccount.setAlpha(0);
        binding.titleCreateAccount.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(500).start();

        binding.tvProcess.setTranslationX(800);
        binding.tvProcess.setAlpha(0);
        binding.tvProcess.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(500).start();
    }

}