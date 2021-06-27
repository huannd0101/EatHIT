package com.example.eathit.ui.slideshow.Comment;

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
import com.example.eathit.ui.slideshow.Friend;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    List<Comment> listComment;
    Context context;

    public CommentAdapter(List<Comment> listComment, Context context) {
        this.listComment = listComment;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Friend friend= listComment.get(position).getAccount();
        if (friend.getLinkAvt() != "null") {
            Glide.with(context).load(friend.getLinkAvt()).into(holder.img_avt_cmt);
        } else {
            holder.img_avt_cmt.setImageResource(R.drawable.ic_baseline_person_24);
            holder.img_avt_cmt.setColorFilter(context.getResources().getColor(R.color.origin));
        }

        holder.tv_fullname_cmt.setText(friend.getFullname());
        holder.tv_content_cmt.setText(listComment.get(position).getContent());

        if (listComment.get(position).getLinkImgCmt()!="null"){
            Glide.with(context).load(listComment.get(position).getLinkImgCmt()).into(holder.img_content_cmt);
        }else{
            holder.img_content_cmt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avt_cmt, img_content_cmt;
        TextView tv_fullname_cmt, tv_content_cmt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img_avt_cmt= itemView.findViewById(R.id.img_avt_cmt);
            img_content_cmt= itemView.findViewById(R.id.img_content_cmt);
            tv_fullname_cmt= itemView.findViewById(R.id.tv_fullnam_cmt);
            tv_content_cmt= itemView.findViewById(R.id.tv_content_cmt);
        }
    }
}
