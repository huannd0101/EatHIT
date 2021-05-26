package com.example.eathit.fragmentChat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eathit.activities.ChatsDetailActivity;
import com.example.eathit.adapter.UsersAdapter;
import com.example.eathit.api.ApiServices;
import com.example.eathit.api.RoomChat;
import com.example.eathit.application.SocketApplication;
import com.example.eathit.databinding.FragmentChatsBinding;
import com.example.eathit.modules.User;
import com.example.eathit.utilities.Constants;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsFragment extends Fragment {
    private FragmentChatsBinding binding;
    Socket mSocket;
    List<User> users = new ArrayList<>();
    UsersAdapter adapter;
    FirebaseDatabase database;
    FirebaseUser currentUser;
    List<RoomChat> roomChats;
    Set<String> listUserId = new HashSet<>();
    String url = "https://api-mess.herokuapp.com/";


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
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        SocketApplication socketApplication = (SocketApplication) requireActivity().getApplication();
        mSocket = socketApplication.getSocket();

        roomChats = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //--------------retrofit
        ApiServices.apiServices.getRoomChats().enqueue(new Callback<List<RoomChat>>() {
            @Override
            public void onResponse(@NotNull Call<List<RoomChat>> call, @NotNull Response<List<RoomChat>> response) {
                roomChats = response.body();
                Toast.makeText(getContext(), roomChats.toString(), Toast.LENGTH_SHORT).show();
                for(RoomChat roomChat : roomChats){
                    if(roomChat.getSender().equals(currentUser.getUid()) ||
                        roomChat.getReceiver().equals(currentUser.getUid())){
                        if(roomChat.getSender().equals(currentUser.getUid())){
                            listUserId.add(roomChat.getReceiver());
                        }else {
                            listUserId.add(roomChat.getSender());
                        }
                    }
                }

                getListUser();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                binding.chatRclView.setLayoutManager(linearLayoutManager);

                adapter = new UsersAdapter(users, getContext(), (user, socket) -> {
                    Intent intent = new Intent(getContext(),  ChatsDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    String roomId = "";
                    for(RoomChat roomChat : roomChats){
                        if((roomChat.getSender().equals(currentUser.getUid()) &&
                                roomChat.getReceiver().equals(user.getUserId())) ||
                                (roomChat.getReceiver().equals(currentUser.getUid()) &&
                                        roomChat.getSender().equals(user.getUserId()))){
                            roomId = roomChat.getId()+"";
                            break;
                        }
                    }

                    intent.putExtra("userId", user.getUserId());
                    intent.putExtra("profilePic", user.getProfilePic());
                    intent.putExtra("userName", user.getFullName());
                    intent.putExtra("isOnline", user.getIsOnline());
                    intent.putExtra("room", roomId);

                    mSocket.emit(Constants.CLIENT_SEND_ROOM, roomId);

                    requireContext().startActivity(intent);

                    Toast.makeText(getContext(), user.getFullName(), Toast.LENGTH_SHORT).show();
                }, true);

                binding.chatRclView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RoomChat>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        //end retrofit





        return binding.getRoot();
    }

    private void getListUser(){
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Set<String> set = new HashSet<>();
                for(RoomChat roomChat : roomChats){
                    set.add(roomChat.getSender());
                    set.add(roomChat.getReceiver());
                }

                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        user.setUserId(dataSnapshot.getKey());

                        //loại bỏ current user from list
                        if (user.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                            continue;

                        //loại bỏ những ai chưa chat
                        for(String s : set){
                            for(String listUserId2 : listUserId){
                                if(listUserId2.equals(s)){
                                    if(user.getUserId().equals(listUserId2)){
                                        users.add(user);
                                    }
                                }
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getListUser2() {
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> set = new ArrayList<>();
                for(RoomChat roomChat : roomChats){
                    set.add(roomChat.getSender());
                    set.add(roomChat.getReceiver());
                }

                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        user.setUserId(dataSnapshot.getKey());

                        //loại bỏ current user from list
                        if (user.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                            continue;
                        //loại bỏ những ai chưa chat
                        for(String s : set){
//                            if(user.getUserId().equals(s)){
//                                users.add(user);
//                            }
                            for(String listUserId2 : listUserId){
                                if(listUserId2.equals(s)){
                                    users.add(user);
                                }
                            }
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}