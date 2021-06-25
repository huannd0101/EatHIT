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
import com.example.eathit.ui.SQLite.SQLHelper;
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

    SQLHelper sqlHelper;


    public PostsAdapter(List<Posts1> listPosts, Context context) {
        this.listPosts = listPosts;
        this.context = context;
//        notifyDataSetChanged();
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
        sqlHelper = new SQLHelper(getContext());

        holder.img_status.bringToFront();
        Friend friend = listPosts.get(position).getAccount();

        if (friend.getLinkAvt() != "null") {
            Glide.with(context).load(friend.getLinkAvt()).into(holder.img_avt);
        } else {
            holder.img_avt.setImageResource(R.drawable.ic_baseline_person_24);
            holder.img_avt.setColorFilter(context.getResources().getColor(R.color.origin));
        }

        holder.tv_fullname.setText(friend.getFullname());

        holder.tv_createAt.setText(listPosts.get(position).getCreateAt());

        holder.tv_content.setText(listPosts.get(position).getContent());
        int line = holder.tv_content.getLineCount();
        if (line >= 3) {
            holder.tv_see_more.setVisibility(View.VISIBLE);
            holder.tv_see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_content.setMaxLines(10);
                    holder.tv_see_more.setVisibility(View.GONE);
                }
            });
        }


        if (listPosts.get(position).getImgLink() != "null") {
            holder.img_posts.setVisibility(View.VISIBLE);
            Glide.with(context).load(listPosts.get(position).getImgLink()).into(holder.img_posts);
        }

        holder.tv_so_like.setText(listPosts.get(position).getLike()+"");

        if (sqlHelper.checkExists(listPosts.get(position).getIdPosts())) {
            holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.like));
            holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.like));
        }else {
            holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.dis_Like));
            holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.dis_Like));
        }


        List<Comment> listCmtOfPosts = getAllCommentOfPosts(listPosts.get(position));
        holder.tv_so_binh_luan.setText(listCmtOfPosts.size() + " Bình luận");


        holder.click_to_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sqlHelper.checkExists(listPosts.get(position).getIdPosts())) {
                    holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.dis_Like));
                    holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.dis_Like));
                    sqlHelper.deletePostLiked(listPosts.get(position).getIdPosts());
                    String likeCount = apiDisLike(listPosts.get(position).getIdPosts());
                    listPosts.get(position).setLike(Integer.parseInt(likeCount));
                    notifyItemChanged(position);
                    holder.tv_so_like.setText(likeCount);
                } else {
                    holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.like));
                    holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.like));
                    sqlHelper.insertPostsLiked(listPosts.get(position).getIdPosts());
                    String likeCount = apiLike(listPosts.get(position).getIdPosts());
                    listPosts.get(position).setLike(Integer.parseInt(likeCount));
                    notifyItemChanged(position);
                    holder.tv_so_like.setText(likeCount);
                }
                //api like here
            }

            private String apiDisLike(int idP) {
                final String[] resul = new String[1];
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url + "posts/" + String.valueOf(idP) + "/dislike", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "dis like succes", Toast.LENGTH_SHORT).show();
                        resul[0] = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "dis like fail", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);
                return resul[0];
            }

            private String apiLike(int idP) {
                final String[] resul = new String[1];
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url + "posts/" + String.valueOf(idP) + "/like", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resul[0] =response;
                        Toast.makeText(getContext(), "like succes", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "like fail", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);
                return resul[0];
            }
        });
    }


    @Override
    public int getItemCount() {
        return listPosts.size();
    }

    private List<Comment> getAllCommentOfPosts(Posts1 posts1) {
        List<Comment> listCmt = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "comment/posts/" + String.valueOf(posts1.getIdPosts()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resultCmt = response;
                try {
                    JSONArray jsonArray = new JSONArray(resultCmt);
                    int size = jsonArray.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("idCmt");
                        String content = jsonObject.getString("content");
                        String linkImgCmt = jsonObject.getString("linkImgCmt");
                        String timeCmt = jsonObject.getString("timeCmt");
                        String updateAt = jsonObject.getString("updateAt");
                        JSONObject accountCmt = jsonObject.getJSONObject("account");
                        Friend friend = convertOBToAccount(accountCmt);

                        Comment comment = new Comment(id, content, linkImgCmt, timeCmt, updateAt, null, friend, posts1, null);
                        listCmt.add(comment);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private Friend convertOBToAccount(JSONObject accountCmt) {
                Friend friend = null;
                try {
                    String id = accountCmt.getString("id");
                    String username = accountCmt.getString("username");
                    String role = accountCmt.getString("role");
                    String fullname = accountCmt.getString("fullname");
                    String gender = accountCmt.getString("gender");
                    boolean status = accountCmt.getBoolean("status");
                    String linkAvt = accountCmt.getString("linkAvt");
                    String email = accountCmt.getString("email");
                    String creaetAt = accountCmt.getString("creaetAt");
                    String updateAt = accountCmt.getString("updateAt");
                    friend = new Friend(id, username, role, fullname, gender, status, linkAvt, email, creaetAt, updateAt);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return friend;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "get all comment fail", Toast.LENGTH_SHORT).show();
            }
        });
        return listCmt;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avt, img_status, img_posts, img_like_click;
        TextView tv_fullname, tv_createAt, tv_content, tv_so_like, tv_so_binh_luan, tv_like_click, tv_see_more;
        LinearLayout click_to_like;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img_avt = itemView.findViewById(R.id.img_avt);
            img_status = itemView.findViewById(R.id.img_status);
            img_posts = itemView.findViewById(R.id.imgPosts);
            img_like_click = itemView.findViewById(R.id.img_like_Click);

            tv_fullname = itemView.findViewById(R.id.tv_Fullname);
            tv_createAt = itemView.findViewById(R.id.tv_createAt);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_so_like = itemView.findViewById(R.id.tv_so_like);
            tv_so_binh_luan = itemView.findViewById(R.id.tv_so_binhluan);
            tv_like_click = itemView.findViewById(R.id.tv_like_Click);

            click_to_like = itemView.findViewById(R.id.click_to_like);

            tv_see_more = itemView.findViewById(R.id.tv_see_more);
        }
    }
}
