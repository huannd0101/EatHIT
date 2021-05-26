package com.example.eathit.onBoardingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.eathit.common.loginSignup.LoginActivity;
import com.example.eathit.databinding.FragmentBlank1Binding;
import com.example.eathit.databinding.FragmentBlank3Binding;
import com.example.eathit.utilities.Constants;
import com.example.eathit.utilities.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BlankFragment3 extends Fragment {
    FragmentBlank3Binding binding;

    public static BlankFragment3 newInstance(String param1, String param2) {
        BlankFragment3 fragment = new BlankFragment3();
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
        binding = FragmentBlank3Binding.inflate(inflater, container, false);
        PreferenceManager preferenceManager = new PreferenceManager(requireContext());
        binding.btnGetStarted.setOnClickListener(v -> {
            preferenceManager.putBoolean(Constants.ON_BOARDING_SCREEN, true);
            requireActivity().startActivity(new Intent(getContext(), LoginActivity.class));
        });

        return binding.getRoot();
    }
}