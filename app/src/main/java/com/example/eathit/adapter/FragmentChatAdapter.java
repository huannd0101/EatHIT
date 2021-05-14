package com.example.eathit.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eathit.fragmentChat.ChatsFragment;
import com.example.eathit.fragmentChat.UsersFragment;

import org.jetbrains.annotations.NotNull;

public class FragmentChatAdapter extends FragmentPagerAdapter {

    public FragmentChatAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new UsersFragment();
            case 0:
            default:
                return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position){
            case 1:
                title = "Users";
                break;
            case 0:
            default:
                title = "Chats";
        }
        return title;
    }

}
