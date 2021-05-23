package com.example.eathit.fragmentChat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eathit.adapter.UsersAdapter;
import com.example.eathit.api.JsonPlaceHolder;
import com.example.eathit.api.Message;
import com.example.eathit.api.RoomChat;
import com.example.eathit.application.SocketApplication;
import com.example.eathit.databinding.FragmentChatsBinding;
import com.example.eathit.modules.User;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsFragment extends Fragment {
    private FragmentChatsBinding binding;
    Socket mSocket;
    List<User> users;
    UsersAdapter adapter;
    FirebaseDatabase database;

    List<RoomChat> roomChats;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment

        SocketApplication socketApplication = (SocketApplication) requireActivity().getApplication();
        mSocket = socketApplication.getSocket();

        roomChats = new ArrayList<>();

        //retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<List<RoomChat>> call = jsonPlaceHolder.getRoomChats();
        call.enqueue(new Callback<List<RoomChat>>() {
            @Override
            public void onResponse(Call<List<RoomChat>> call, retrofit2.Response<List<RoomChat>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Call api lõi", Toast.LENGTH_SHORT).show();
                    return;
                }
                roomChats = response.body();
                assert roomChats != null;
                users = new ArrayList<>();


                if(roomChats.size() > 0){
                    getListUser();
                }else {
                    Toast.makeText(getContext(), "Khoong xu li", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), roomChats.toString(), Toast.LENGTH_SHORT).show();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                binding.chatRclView.setLayoutManager(linearLayoutManager);

                adapter = new UsersAdapter(users, getContext(), mSocket, true);
                binding.chatRclView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RoomChat>> call, Throwable t) {
                Toast.makeText(getContext(), "Không thể call api", Toast.LENGTH_SHORT).show();
            }
        });
        //end retrogit

        //khởi tạo list




        return binding.getRoot();
    }

    private void getListUser() {
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
                            if(user.getUserId().equals(s)){
                                users.add(user);
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
}