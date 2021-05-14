//package com.example.eathit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eathit.R;
import com.example.eathit.modules.Chat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
//    public static  final int MSG_TYPE_LEFT = 0;
//    public static  final int MSG_TYPE_RIGHT = 1;
//
//    private Context mContext;
//    private List<Chat> mChat;

//    FirebaseUser fuser;
//
//    public MessageAdapter(Context mContext, List<Chat> mChat){
//        this.mChat = mChat;
//        this.mContext = mContext;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == MSG_TYPE_RIGHT) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.sample_sender2, parent, false);
//            return new ViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.sample_reciver2, parent, false);
//            return new ViewHolder(view);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        Chat chat = mChat.get(position);
//
//        holder.show_message.setText(chat.getMessage());
//        if (position == mChat.size()-1){
//            if (chat.getIsSeen()){
//                holder.txt_seen.setText("Seen");
//            } else {
//                holder.txt_seen.setText("Delivered");
//            }
//        } else {
//            holder.txt_seen.setVisibility(View.GONE);
//        }
//        //timestamp
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//        holder.timestamp.setText(simpleDateFormat.format(new Date(chat.getTimestamp())));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mChat.size();
//    }
//
//    public  class ViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView show_message;
//        public ImageView profile_image;
//        public TextView txt_seen;
//        public TextView timestamp;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            show_message = itemView.findViewById(R.id.show_message);
//            profile_image = itemView.findViewById(R.id.profile_image);
//            txt_seen = itemView.findViewById(R.id.tvIsSeen);
//            timestamp = itemView.findViewById(R.id.timestamp);
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        fuser = FirebaseAuth.getInstance().getCurrentUser();
//        if (mChat.get(position).getSender().equals(fuser.getUid())){
//            return MSG_TYPE_RIGHT;
//        } else {
//            return MSG_TYPE_LEFT;
//        }
//    }
//}
