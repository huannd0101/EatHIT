package com.example.eathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eathit.R;
import com.example.eathit.adapter.FragmentChatAdapter;
import com.example.eathit.databinding.ActivityChatBinding;

import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        //set up tablayout1
        
        binding.viewPager.setAdapter(new FragmentChatAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);


    }
}