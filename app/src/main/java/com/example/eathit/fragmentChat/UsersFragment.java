package com.example.eathit.fragmentChat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eathit.activities.ChatActivity;
import com.example.eathit.adapter.UsersAdapter;
import com.example.eathit.application.SocketApplication;
import com.example.eathit.databinding.FragmentUsersBinding;
import com.example.eathit.modules.User;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    FragmentUsersBinding binding;
    List<User> users;
    UsersAdapter adapter;
    Socket mSocket;
    FirebaseDatabase database;
    public static UsersFragment newInstance(String param1, String param2) {
        UsersFragment fragment = new UsersFragment();
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
        binding = FragmentUsersBinding.inflate(inflater, container, false);

        ChatActivity chatActivity = (ChatActivity) getActivity();
        SocketApplication socketApplication = (SocketApplication) requireActivity().getApplication();
        mSocket = socketApplication.getSocket();
        database = FirebaseDatabase.getInstance();

        users = new ArrayList<>();
        getListUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rclAllUsers.setLayoutManager(linearLayoutManager);

        adapter = new UsersAdapter(users, getContext(), mSocket, true);
        binding.rclAllUsers.setAdapter(adapter);




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