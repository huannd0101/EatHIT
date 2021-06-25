package com.example.eathit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eathit.R;
import com.example.eathit.adapter.FragmentOnBoardingAdapter;
import com.example.eathit.common.loginSignup.LoginActivity;
import com.example.eathit.databinding.ActivityOnBoardingBinding;
import com.example.eathit.utilities.Constants;
import com.example.eathit.utilities.PreferenceManager;

import java.util.Objects;

public class OnBoardingActivity extends AppCompatActivity {
    ActivityOnBoardingBinding binding;
    private FragmentOnBoardingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();




        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Constants.ON_BOARDING_SCREEN)){
            startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
        }

        adapter = new FragmentOnBoardingAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(adapter);
        binding.circleIndicator.setViewPager(binding.viewPager);

        binding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    binding.tvSkip.setVisibility(View.GONE);
                    binding.llNext.setVisibility(View.GONE);
                }else {
                    binding.tvSkip.setVisibility(View.VISIBLE);
                    binding.llNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.tvSkip.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(2);
        });

        binding.llNext.setOnClickListener(v -> {

            if(binding.viewPager.getCurrentItem() < 2){
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
            }

        });
    }
}