package com.example.eathit.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eathit.onBoardingScreen.BlankFragment1;
import com.example.eathit.onBoardingScreen.BlankFragment2;
import com.example.eathit.onBoardingScreen.BlankFragment3;

import org.jetbrains.annotations.NotNull;

public class FragmentOnBoardingAdapter extends FragmentStatePagerAdapter {

    public FragmentOnBoardingAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1: return new BlankFragment2();
            case 2: return new BlankFragment3();
            case 0:
            default: return new BlankFragment1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
