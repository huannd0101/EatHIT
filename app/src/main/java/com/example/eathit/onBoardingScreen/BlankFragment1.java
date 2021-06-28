package com.example.eathit.onBoardingScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.eathit.databinding.FragmentBlank1Binding;

import org.jetbrains.annotations.NotNull;

public class BlankFragment1 extends Fragment {
    FragmentBlank1Binding binding;

    public static BlankFragment1 newInstance(String param1, String param2) {
        BlankFragment1 fragment = new BlankFragment1();
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
        binding = FragmentBlank1Binding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}