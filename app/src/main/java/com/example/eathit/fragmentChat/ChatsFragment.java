package com.example.eathit.fragmentChat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eathit.activities.ChatsDetailActivity;
import com.example.eathit.activities.Main2Activity;
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
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomChats = new ArrayList<>();
        ApiServices.apiServices.getRoomChats().enqueue(new Callback<List<RoomChat>>() {
            @Override
            public void onResponse(@NotNull Call<List<RoomChat>> call, @NotNull Response<List<RoomChat>> response) {
                roomChats = response.body();

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

                adapter = new UsersAdapter(users, getContext(), (user, socket, position) -> {
                    Intent intent = new Intent(getContext(),  ChatsDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    int roomId = 0;
                    for(RoomChat roomChat : roomChats){
                        if((roomChat.getSender().equals(currentUser.getUid()) &&
                                roomChat.getReceiver().equals(user.getUserId())) ||
                                (roomChat.getReceiver().equals(currentUser.getUid()) &&
                                        roomChat.getSender().equals(user.getUserId()))){
                            roomId = roomChat.getId();
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
                }, true);

                binding.chatRclView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<RoomChat>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
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

        currentUser = FirebaseAuth.getInstance().getCurrentUser();



        binding.btnHome.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });



        return binding.getRoot();
    }





    private void getListUser(){
        String url = "https://btl-spring-boot.herokuapp.com/api/accounts/";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            Set<String> set = new HashSet<>();
                            for(RoomChat roomChat : roomChats){
                                set.add(roomChat.getSender());
                                set.add(roomChat.getReceiver());
                            }

                            users.clear();
                            for(int i=0; i<array.length(); i++){
                                JSONObject jsonObject = (JSONObject) array.get(i);
                                //loại bỏ currentUser
//                                if(currentUser.getUid().equals(jsonObject.getString("id"))){
//                                    continue;
//                                }
                                //loại bỏ những ai chưa chat
                                for(String s : set) {
                                    for(String listUserId2 : listUserId){
                                        if(listUserId2.equals(s)){
                                            if(jsonObject.getString("idNew").equals(listUserId2)){
                                                User user = new User(
                                                        jsonObject.getString("idNew"),
                                                        jsonObject.getString("fullname"),
                                                        jsonObject.getString("linkAvt"),
                                                        "isOnline"
                                                );
                                                users.add(user);
                                            }
                                        }
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
//        database = FirebaseDatabase.getInstance();
//        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                Set<String> set = new HashSet<>();
//                for(RoomChat roomChat : roomChats){
//                    set.add(roomChat.getSender());
//                    set.add(roomChat.getReceiver());
//                }
//
//                users.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    User user = dataSnapshot.getValue(User.class);
//                    if (user != null) {
//                        user.setUserId(dataSnapshot.getKey());
//
//                        //loại bỏ current user from list
//                        if (user.getUserId().equals(FirebaseAuth.getInstance().getUid()))
//                            continue;
//
//                        //loại bỏ những ai chưa chat
//                        for(String s : set){
//                            for(String listUserId2 : listUserId){
//                                if(listUserId2.equals(s)){
//                                    if(user.getUserId().equals(listUserId2)){
//                                        users.add(user);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}