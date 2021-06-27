package com.example.eathit.fragmentChat;

import android.content.Intent;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eathit.activities.ChatActivity;
import com.example.eathit.activities.ChatsDetailActivity;
import com.example.eathit.activities.Main2Activity;
import com.example.eathit.adapter.IOnClickUser;
import com.example.eathit.adapter.UsersAdapter;
import com.example.eathit.api.ApiServices;
import com.example.eathit.api.RoomChat;
import com.example.eathit.api.dto.RoomChatDTO;
import com.example.eathit.application.SocketApplication;
import com.example.eathit.databinding.FragmentUsersBinding;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {
    FragmentUsersBinding binding;
    List<User> users;
    UsersAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Socket mSocket;
    FirebaseDatabase database;
    List<RoomChat> roomChats;
    List<Boolean> isHaveRoom;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SocketApplication socketApplication = (SocketApplication) requireActivity().getApplication();
        mSocket = socketApplication.getSocket();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        users = new ArrayList<>();
        isHaveRoom = new ArrayList<>();
        getListUser();
        linearLayoutManager = new LinearLayoutManager(getContext());


        adapter = new UsersAdapter(users, getContext(), (user, socket, position) -> {

            Intent intent = new Intent(getContext(),  ChatsDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if(!isHaveRoom.get(position)){
                RoomChatDTO roomChatDTO = new RoomChatDTO(currentUser.getUid(), user.getUserId());
                ApiServices.apiServices.postNewRoomChat(roomChatDTO).enqueue(new Callback<RoomChat>() {
                    @Override
                    public void onResponse(@NotNull Call<RoomChat> call, @NotNull Response<RoomChat> response) {

                        assert response.body() != null;
                        int roomId = response.body().getId();

                        intent.putExtra("userId", user.getUserId());
                        intent.putExtra("profilePic", user.getProfilePic());
                        intent.putExtra("userName", user.getFullName());
                        intent.putExtra("isOnline", user.getIsOnline());
                        intent.putExtra("room", roomId);

                        mSocket.emit(Constants.CLIENT_SEND_ROOM, roomId);

                        requireContext().startActivity(intent);
                    }

                    @Override
                    public void onFailure(@NotNull Call<RoomChat> call, @NotNull Throwable t) {

                    }
                });
            }else {
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
            }
        }, true);


        //call api
        roomChats = new ArrayList<>();


        ApiServices.apiServices.getRoomChats().enqueue(new Callback<List<RoomChat>>() {
            @Override
            public void onResponse(@NotNull Call<List<RoomChat>> call, @NotNull Response<List<RoomChat>> response) {
                roomChats.clear();
                roomChats = response.body();
                for (int i = 0; i < Objects.requireNonNull(roomChats).size(); i++) {
                    String s = roomChats.get(i).getSender();
                    String s2 = roomChats.get(i).getReceiver();
                    for (int j = 0; j < users.size(); j++) {
                        if (s.equals(users.get(j).getUserId()) || s2.equals(users.get(j).getUserId())) {
                            isHaveRoom.set(j, true);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<RoomChat>> call, @NotNull Throwable t) {

            }
        });

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);

        binding.rclAllUsers.setLayoutManager(linearLayoutManager);
        binding.rclAllUsers.setAdapter(adapter);


        return binding.getRoot();
    }


    private void getListUser() {
        users.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        String url = "https://btl-spring-boot.herokuapp.com/api/accounts/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            if(jsonObject.get("idNew").equals(FirebaseAuth.getInstance().getUid())) {
                                continue;
                            }
                            User u = new User(
                                    jsonObject.getString("idNew"),
                                    jsonObject.getString("fullname"),
                                    jsonObject.getString("linkAvt"),
                                    "isOnline"
                            );
                            users.add(u);
                            isHaveRoom.add(false);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            Toast.makeText(getContext(), "Call users error", Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(stringRequest);
    }
}