package com.example.eathit.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.eathit.R;
import com.example.eathit.adapter.MessageAdapter;
import com.example.eathit.api.ApiServices;
import com.example.eathit.api.Message;
import com.example.eathit.application.SocketApplication;
import com.example.eathit.databinding.ActivityChatsDetailBinding;
import com.example.eathit.api.dto.MessageDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsDetailActivity extends AppCompatActivity {
    Socket mSocket;
    List<ChatTest> chatTests;
    List<Message> messages;
    MessageAdapter adapter;
    ActivityChatsDetailBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    String currentUserId;
    String receiverId;
    LinearLayoutManager linearLayoutManager;
    List<Message> messageList = new ArrayList<>();
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

        //đọc tin nhắn cũ
        readMessages();

//        chatTests = new ArrayList<>();

//        adapter = new MessageAdapter(this, messages);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        binding.chatRclView.setAdapter(adapter);
//        binding.chatRclView.setLayoutManager(linearLayoutManager);

        //chat tự động xuống dưới
//
//        linearLayoutManager.setReverseLayout(false);
//        linearLayoutManager.setSmoothScrollbarEnabled(true);
//        linearLayoutManager.setStackFromEnd(true);
//        adapter.notifyDataSetChanged();

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
                String content = binding.edtTextPersonName.getText().toString().trim();
                if(content.isEmpty()){
                    return;
                }
                jsonObject.put("sender", currentUserId);
                jsonObject.put("receiver", receiverId);
                jsonObject.put("message", binding.edtTextPersonName.getText().toString());

                int room = getIntent().getIntExtra("room", 0);

                jsonObject.put("room", room);
                mSocket.emit(Constants.Client_SEND_MESSAGE, jsonObject);

                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setContent(content);
                messageDTO.setSender(user.getUid());
                messageDTO.setReceiver(receiverId);
                messageDTO.setRoomChat(room);
                sendMessage(messageDTO);

                binding.edtTextPersonName.setText("");
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        //btn call audio
        binding.btnCallAudio.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Chưa phát triển", Toast.LENGTH_SHORT).show();
        });
        //btn call video
        binding.btnCallVideo.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Chưa phát triển", Toast.LENGTH_SHORT).show();
        });
        //btn more
        binding.btnMore.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Chưa phát triển", Toast.LENGTH_SHORT).show();
        });





        binding.backToMain.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    private void sendMessage(MessageDTO messageDTO){
        ApiServices.apiServices.postNewMessage(messageDTO).enqueue(new Callback<MessageDTO>() {
            @Override
            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {

            }

            @Override
            public void onFailure(Call<MessageDTO> call, Throwable t) {
                Toast.makeText(ChatsDetailActivity.this, "Send failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readMessages(){
        messages = new ArrayList<>();
        ApiServices.apiServices.getMessage().enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messages.clear();
                messages = response.body();

                messageList.clear();

                for(Message message : messages){
                    if((message.getSender().equals(user.getUid()) && message.getReceiver().equals(receiverId))
                        || (message.getSender().equals(receiverId) && message.getReceiver().equals(user.getUid()))) {
                        messageList.add(message);
                    }
                }

                adapter = new MessageAdapter(getApplicationContext(), messageList);
                linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                binding.chatRclView.setAdapter(adapter);
                binding.chatRclView.setLayoutManager(linearLayoutManager);

                linearLayoutManager.setReverseLayout(false);
                linearLayoutManager.setSmoothScrollbarEnabled(true);
                linearLayoutManager.setStackFromEnd(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(ChatsDetailActivity.this, "Lỗi call message", Toast.LENGTH_SHORT).show();
            }
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
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    System.out.println(args[0].toString());
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        String message = (String) jsonObject.get("message");

                        messageList.add(
                                new Message(
                                        message,
                                        (String) jsonObject.get("sender"),
                                        (String) jsonObject.get("receiver"),
                                        jsonObject.getInt("room")
                                        ));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    linearLayoutManager.setReverseLayout(false);
                    linearLayoutManager.setSmoothScrollbarEnabled(true);
                    linearLayoutManager.setStackFromEnd(true);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };




}