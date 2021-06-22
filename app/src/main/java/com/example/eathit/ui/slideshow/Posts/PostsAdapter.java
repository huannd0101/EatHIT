package com.example.eathit.ui.slideshow.Posts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.eathit.R;
import com.example.eathit.ui.slideshow.Comment.Comment;
import com.example.eathit.ui.slideshow.Friend;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    List<Posts1> listPosts;
    Context context;
    String url = "https://btl-spring-boot.herokuapp.com/api/";

    public PostsAdapter(List<Posts1> listPosts, Context context) {
        this.listPosts = listPosts;
        this.context = context;
    }

    public PostsAdapter() {
    }

    public List<Posts1> getListPosts() {
        return listPosts;
    }

    public void setListPosts(List<Posts1> listPosts) {
        this.listPosts = listPosts;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_posts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.img_status.bringToFront();
        holder.img_posts.setVisibility(View.GONE);
        Friend friend = listPosts.get(position).getAccount();

        if (friend.getLinkAvt() != null) {
            Glide.with(context).load(friend.getLinkAvt()).into(holder.img_avt);
        }

        holder.tv_fullname.setText(friend.getFullname());
        holder.tv_createAt.setText(listPosts.get(position).getCreateAt());
        holder.tv_content.setText(listPosts.get(position).getContent());
        if (listPosts.get(position).getImgLink() != null) {
            holder.img_posts.setVisibility(View.VISIBLE);
            Glide.with(context).load(listPosts.get(position).getImgLink()).into(holder.img_posts);
        }
        holder.tv_so_like.setText(listPosts.get(position).getLike() + "");

        List<Comment> listCmtOfPosts = getAllCommentOfPosts(listPosts.get(position));
            holder.tv_so_binh_luan.setText(listCmtOfPosts.size() + " Bình luận");
        holder.click_to_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.like));
                holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.like));
                holder.tv_so_like.setText(listPosts.get(position).getLike() + 1 + "");
                //api like here
            }
        });
    }

    private List<Comment> getAllCommentOfPosts(Posts1 posts1) {
        List<Comment> listCmt = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "comment/posts/" + posts1.getIdPosts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resultCmt = response;

                tr                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       