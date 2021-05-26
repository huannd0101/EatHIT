package com.example.eathit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eathit.R;
import com.example.eathit.activities.ChatsDetailActivity;
import com.example.eathit.modules.Chat;
import com.example.eathit.modules.User;
import com.example.eathit.utilities.Constants;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{
    boolean isOnline;
    String userId;
    List<User> list;
    Context context;
    Socket mSocket;
    String theLastMessage = "";
    FirebaseAuth auth;
    IOnClickUser iOnClickUser;

    public UsersAdapter(List<User> list, Context context, IOnClickUser iOnClickUser, boolean isOnline) {
        this.list = list;
        this.context = context;
        this.iOnClickUser = iOnClickUser;
        this.isOnline = isOnline;
    }

//    public UsersAdapter(List<User> list, Context context, Socket mSocket, boolean isOnline) {
//        this.list = list;
//        this.context = context;
//        this.mSocket = mSocket;
//        this.isOnline = isOnline;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User users = list.get(position);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.ic_baseline_person_24).into(holder.image);
        holder.username.setText(users.getFullName());

        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();



//        holder.lastMessage.setText(users.getLastMessage());

        //sét online
        if(isOnline){
            if(users.getIsOnline() != null){
                if(users.getIsOnline().compareToIgnoreCase("online") == 0){
                    holder.imgOnline.setVisibility(View.VISIBLE);
                    holder.imgOffline.setVisibility(View.GONE);
                }else {
                    holder.imgOnline.setVisibility(View.GONE);
                    holder.imgOffline.setVisibility(View.VISIBLE);
                }
            }
        }else {
            holder.imgOnline.setVisibility(View.GONE);
            holder.imgOffline.setVisibility(View.GONE);
        }

        //Show last message on user's list
//        if(isOnline) {
//            //nếu online thì show: k thì thôi :v
//            showLastMessage(users.getUseeId(), holder.lastMessage);
//        }

        String currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        holder.itemView.setOnClickListener(v -> {
            iOnClickUser.clickUser(users, mSocket);
        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,  ChatsDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                intent.putExtra("userId", users.getUserId());
//                intent.putExtra("profilePic", users.getProfilePic());
//                intent.putExtra("userName", users.getFullName());
//                intent.putExtra("isOnline", users.getIsOnline());
//                String room = currentUserId+"|"+users.getUserId();
//                intent.putExtra("room", "huan");
//
//                mSocket.emit(Constants.CLIENT_SEND_ROOM, "huan");
//
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if(list.size() == 0){
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView username, lastMessage;
        CircleImageView imgOnline, imgOffline;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.tvUserName);
            lastMessage = itemView.findViewById(R.id.tvLastMessage);
            imgOnline = itemView.findViewById(R.id.img_online);
            imgOffline = itemView.findViewById(R.id.img_offline);
        }
    }

    //show last message
    private void showLastMessage(String receiverId, TextView textView){
        theLastMessage = "default";
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Chat chat = snapshot1.getValue(Chat.class);
                    if(chat.getReceiver().equals(currentUser.getUid()) && chat.getSender().equals(receiverId) ||
                        chat.getSender().equals(currentUser.getUid()) && chat.getReceiver().equals(receiverId)){
                        theLastMessage = chat.getMessage();
                    }
                }
                switch (theLastMessage){
                    case "default":
                        textView.setText("No message");
                        break;
                    default:
                        textView.setText(theLastMessage);
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
