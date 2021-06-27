package com.example.eathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eathit.adapter.FragmentChatAdapter;
import com.example.eathit.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    FirebaseUser user;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        database = FirebaseDatabase.getInstance();

        //set up tablayout1
        binding.viewPager.setAdapter(new FragmentChatAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void isOnline(String isOnline){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("isOnline", isOnline);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null)
            database.getReference().child("Users")
                    .child(user.getUid())
                    .updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnline("online");
    }

    @Override
    protected void onStop() {
        super.onStop();
//        isOnline("offline");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        isOnline("offline");
    }


}