package com.example.eathit.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eathit.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    List<Friend> list;
    Context context;

    public FriendAdapter(List<Friend> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public FriendAdapter() {
    }

    public List<Friend> getList() {
        return list;
    }

    public void setList(List<Friend> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FriendAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getLinkAvt()).into(holder.avt_friend);
        holder.tvFName.setText(list.get(position).getFullname().trim());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avt_friend;
        TextView tvFName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            avt_friend= itemView.findViewById(R.id.avt_friend);
            tvFName= itemView.findViewById(R.id.tvFname);
        }
    }
}
