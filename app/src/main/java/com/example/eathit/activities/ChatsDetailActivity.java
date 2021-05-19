package com.example.eathit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eathit.R;
import com.example.eathit.adapter.MessageAdapter;
import com.example.eathit.application.SocketApplication;
import com.example.eathit.databinding.ActivityChatsDetailBinding;
import com.example.eathit.modules.ChatTest;
import com.example.eathit.utilities.Constants;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatsDetailActivity extends AppCompatActivity {
    Socket mSocket;
    List<ChatTest> chatTests;
    MessageAdapter adapter;
    ActivityChatsDetailBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    String currentUserId;
    String receiverId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();


//        binding.profileImage.setImageResource(Integer.parseInt(getIntent().getStringExtra("profilePic")));
        binding.tvUserName.setText(getIntent().getStringExtra("userName"));
        receiverId = getIntent().getStringExtra("userId");

        mAuth = FirebaseAuth.getInstance();
        currentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        database = FirebaseDatabase.getInstance();
        SocketApplication socketApplication = (SocketApplication) getApplication();

        mSocket = socketApplication.getSocket();

        chatTests = new ArrayList<>();

        adapter = new MessageAdapter(this, chatTests);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.chatRclView.setAdapter(adapter);
        binding.chatRclView.setLayoutManager(linearLayoutManager);

        //chat tự động xuống dưới
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);
        adapter.notifyDataSetChanged();

        //lắng nghe từ server
        mSocket.on(Constants.SERVER_SEND_MESSAGE, serverSendMessageListener);

        //
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");
        String isOnline = getIntent().getStringExtra("isOnline");

        binding.tvUserName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_person_24).into(binding.profileImage);

        if(isOnline != null){
            if(isOnline.compareToIgnoreCase("online") == 0){
                binding.imgOnline.setVisibility(View.VISIBLE);
                binding.imgOffline.setVisibility(View.GONE);
            }else {
                binding.imgOnline.setVisibility(View.GONE);
                binding.imgOffline.setVisibility(View.VISIBLE);
            }
        }else {
            binding.imgOnline.setVisibility(View.GONE);
            binding.imgOffline.setVisibility(View.GONE);
        }


        //xử lý btn send
        binding.btnSend.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                Date date = new Date();
                jsonObject.put("sender", currentUserId);
                jsonObject.put("timestamp", date.getTime());
                jsonObject.put("message", binding.edtTextPersonName.getText().toString());

                String room = getIntent().getStringExtra("room");

                jsonObject.put("room", room);
                mSocket.emit(Constants.Client_SEND_MESSAGE, jsonObject);

                binding.edtTextPersonName.setText("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        binding.backToMain.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(intent);
            finish();
        });

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
        mSocket.emit(Constants.CLIENT_LEAVE_ROOM, "room");
//        isOnline("offline");
    }


    private final Emitter.Listener serverSendMessageListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(args[0].toString());
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        String message = (String) jsonObject.get("message");
                        long timestamp = (long) jsonObject.get("timestamp");

                        chatTests.add(new ChatTest(message, (String) jsonObject.get("sender"), timestamp));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };




}