package com.example.eathit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.eathit.R;
import com.example.eathit.adapter.MessageAdapter;
import com.example.eathit.application.ChatApplication;
import com.example.eathit.databinding.ActivityChatsDetailBinding;
import com.example.eathit.modules.ChatTest;
import com.example.eathit.utilities.Constants;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ChatsDetailActivity extends AppCompatActivity {
    Socket mSocket;
    List<ChatTest> chatTests;
    MessageAdapter adapter;
    ActivityChatsDetailBinding binding;
    FirebaseAuth mAuth;
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
        ChatApplication chatApplication = (ChatApplication) getApplication();

        mSocket = chatApplication.getSocket();

        chatTests = new ArrayList<>();

        adapter = new MessageAdapter(this, chatTests);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.chatRclView.setAdapter(adapter);
        binding.chatRclView.setLayoutManager(linearLayoutManager);

        //lắng nghe từ server
        mSocket.on(Constants.SERVER_SEND_MESSAGE, serverSendMessageListener);

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
                mSocket.emit("client-send-message", jsonObject);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.emit(Constants.CLIENT_LEAVE_ROOM, "room");
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