package com.example.eathit.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.eathit.R;
import com.example.eathit.databinding.FragmentSecondSignupBinding;

import org.jetbrains.annotations.NotNull;

public class SecondSignupFragment extends Fragment {
    FragmentSecondSignupBinding binding;
    String gender;
    public static final String TAG = SecondSignupFragment.class.getName();

    public static SecondSignupFragment newInstance() {
        SecondSignupFragment fragment = new SecondSignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondSignupBinding.inflate(inflater, container, false);

        //animation
        initAnimate();

        //back to first signup fragment
        binding.btnBackFirstSignup.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });

        binding.radioGender.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.radioMale.getId()){
                gender = binding.radioMale.getText().toString().toLowerCase();
            }else if(checkedId == binding.radioFemale.getId()){
                gender = binding.radioFemale.getText().toString().toLowerCase();
            }else {
                gender = binding.radioOther.getText().toString().toLowerCase();
            }
        });

        //next to third signup fragment
        binding.btnNextToThirdSignUp.setOnClickListener(v -> {
            //get data from edt

            String day = String.valueOf(binding.dpBirthday.getDayOfMonth());
            String month = String.valueOf(binding.dpBirthday.getMonth() + 1);
            String year = String.valueOf(binding.dpBirthday.getYear());
            String birthDay = day + "/" + month + "/" + year;


            Fragment fragment = ThirdSignupFragment.newInstance();

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_fragment, fragment).addToBackStack(ThirdSignupFragment.TAG).commit();
        });




        return binding.getRoot();
    }

    private void initAnimate() {
        binding.radioGender.setTranslationY(800);
        binding.viewView.setTranslationY(800);
        binding.tvSelectDate.setTranslationY(800);
        binding.dpBirthday.setTranslationY(800);
        binding.btnNextToThirdSignUp.setTranslationY(800);
        binding.btnLoginOfSignup.setTranslationY(800);

        binding.radioGender.setAlpha(0);
        binding.viewView.setAlpha(0);
        binding.tvSelectDate.setAlpha(0);
        binding.dpBirthday.setAlpha(0);
        binding.btnNextToThirdSignUp.setAlpha(0);
        binding.btnLoginOfSignup.setAlpha(0);

        binding.radioGender.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(600).start();
        binding.viewView.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(800).start();
        binding.tvSelectDate.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1100).start();
        binding.dpBirthday.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1300).start();
        binding.btnNextToThirdSignUp.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1500).start();
        binding.btnLoginOfSignup.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1800).start();

        binding.titleCreateAccount.setTranslationX(-800);
        binding.titleCreateAccount.setAlpha(0);
        binding.titleCreateAccount.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(500).start();

        binding.tvProcess.setTranslationX(800);
        binding.tvProcess.setAlpha(0);
        binding.tvProcess.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(500).start();
    }
}