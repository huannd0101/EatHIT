package com.example.eathit.ui.slideshow.Posts;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.eathit.ui.slideshow.Comment.CommentAdapter;
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
    int soLike;

    String resultCmt;



    public PostsAdapter(List<Posts1> listPosts, Context context) {
        this.listPosts = listPosts;
        this.context = context;
        sqlHelper = new SQLHelper(getContext());
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
        holder.tv_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
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
        } else {
            holder.tv_see_more.setVisibility(View.GONE);
        }


        if (listPosts.get(position).getImgLink() != "null") {
            holder.img_posts.setVisibility(View.VISIBLE);
            Glide.with(context).load(listPosts.get(position).getImgLink()).into(holder.img_posts);
        }
        soLike = listPosts.get(position).getLike();
        holder.tv_so_like.setText(soLike + "");

        if (sqlHelper.checkExists(listPosts.get(position).getIdPosts())) {
            holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.like));
            holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.like));
        } else {
            holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.dis_Like));
            holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.dis_Like));
        }


        List<Comment> listCmtOfPosts = getAllCommentOfPosts(listPosts.get(position));
        holder.tv_so_binh_luan.setText(listCmtOfPosts.size() + " Bình luận");

        //like
        holder.click_to_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sqlHelper.checkExists(listPosts.get(position).getIdPosts())) {
                    holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.dis_Like));
                    holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.dis_Like));
                    sqlHelper.deletePostLiked(listPosts.get(position).getIdPosts());
                    String likeCount = apiDisLike(listPosts.get(position).getIdPosts());
                    int sl = Integer.parseInt(holder.tv_so_like.getText().toString());
//                    listPosts.get(position).setLike(Integer.parseInt(likeCount));
//                    notifyItemChanged(position);
                    if (sl > 0)
                        holder.tv_so_like.setText(sl - 1 + "");
                } else {
                    holder.img_like_click.setColorFilter(context.getResources().getColor(R.color.like));
                    holder.tv_like_click.setTextColor(context.getResources().getColor(R.color.like));
                    sqlHelper.insertPostsLiked(listPosts.get(position).getIdPosts());
                    String likeCount = apiLike(listPosts.get(position).getIdPosts());
                    int sl = Integer.parseInt(holder.tv_so_like.getText().toString());
//                    listPosts.get(position).setLike(Integer.parseInt(likeCount));
//                    notifyItemChanged(position);
                    holder.tv_so_like.setText(sl + 1 + "");
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
                        resul[0] = response;
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
//        //comment

        holder.click_to_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_cmt);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtributes = window.getAttributes();
                windowAtributes.gravity = Gravity.BOTTOM;
                window.setAttributes(windowAtributes);

                dialog.setCancelable(true);


                RecyclerView revComment = dialog.findViewById(R.id.revComment);
                ImageView img_choose_img_to_cmt = dialog.findViewById(R.id.img_choose_img_to_cmt);
                ImageView img_btn_send_cmt = dialog.findViewById(R.id.img_btn_send_cmt);
                EditText edt_type_cmt = dialog.findViewById(R.id.edt_type_to_cmt);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                CommentAdapter commentAdapter = new CommentAdapter(listCmtOfPosts, context);
                revComment.setLayoutManager(linearLayoutManager);
                revComment.setAdapter(commentAdapter);

                dialog.show();


                img_choose_img_to_cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "chọn ảnh", Toast.LENGTH_SHORT).show();
                    }
                });
                img_btn_send_cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "gửi comment: " + edt_type_cmt.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(context, "cmt", Toast.LENGTH_SHORT).show();
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "comment/posts/" + posts1.getIdPosts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                resultCmt = response;
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
                    System.out.println("lỗi tạo list comment");
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "get all comment fail", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        return listCmt;
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
            System.out.println("lỗi convert account comment");
        }
        return friend;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avt, img_status, img_posts, img_like_click;
        TextView tv_fullname, tv_createAt, tv_content, tv_so_like, tv_so_binh_luan, tv_like_click, tv_see_more;
        LinearLayout click_to_like, click_to_cmt;

        RecyclerView revComment;
        ImageView img_choose_img_to_cmt, img_btn_send_cmt;
        EditText edt_type_cmt;


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
            click_to_cmt = itemView.findViewById(R.id.click_to_comment);

            tv_see_more = itemView.findViewById(R.id.tv_see_more);

//            cmt
//            revComment = itemView.findViewById(R.id.revComment);
//            img_choose_img_to_cmt = itemView.findViewById(R.id.img_choose_img_to_cmt);
//            img_btn_send_cmt = itemView.findViewById(R.id.img_btn_send_cmt);
//            edt_type_cmt = itemView.findViewById(R.id.edt_type_to_cmt);

        }
    }
}
