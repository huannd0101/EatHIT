package com.example.eathit.fragmentChat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eathit.R;
import com.example.eathit.activities.ChatsDetailActivity;
import com.example.eathit.adapter.UsersAdapter;
import com.example.eathit.application.ChatApplication;
import com.example.eathit.databinding.FragmentChatsBinding;
import com.example.eathit.modules.User;
import com.example.eathit.utilities.Constants;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatsFragment extends Fragment {
    private FragmentChatsBinding binding;
    Socket mSocket;
    List<User> users;
    UsersAdapter adapter;
    FirebaseDatabase database;

    public static ChatsFragment newInstance(String param1, String param2) {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment

        ChatApplication chatApplication = (ChatApplication) requireActivity().getApplication();
        mSocket = chatApplication.getSocket();


        database = FirebaseDatabase.getInstance();
        users = new ArrayList<>();
        getListUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.chatRclView.setLayoutManager(linearLayoutManager);

        adapter = new UsersAdapter(users, getContext(), mSocket);
        binding.chatRclView.setAdapter(adapter);


        return binding.getRoot();
    }

    private void getListUser(){
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if(user != null) {
                        user.setUserId(dataSnapshot.getKey());

                        //loại bỏ current user from list
                        if(user.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                            continue;
                        users.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}