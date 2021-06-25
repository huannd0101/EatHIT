package com.example.eathit.ui.slideshow;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.eathit.databinding.FragmentSlideshowBinding;

import com.example.eathit.ui.slideshow.Posts.Posts1;

import com.example.eathit.ui.slideshow.API.APIService;
import com.example.eathit.ui.slideshow.Comment.Comment;
import com.example.eathit.ui.slideshow.Posts.Posts;
import com.example.eathit.ui.slideshow.Posts.Posts1;
import com.example.eathit.ui.slideshow.Posts.PostsAdapter;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private com.example.eathit.ui.news.NewViewModel newViewModel;
    private FragmentSlideshowBinding binding;

    String result, resultAccount, resultCmt;
    String url = "https://btl-spring-boot.herokuapp.com/api/";
    int idPostsGetAc;

    List<Posts1> listPost;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        newViewModel = new ViewModelProvider(this).get(com.example.eathit.ui.news.NewViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        binding.imgStatusOnOff.bringToFront();
        //id
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "posts", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                result = response;
//                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                    listPost = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idPosts = jsonObject.getInt("idPosts");
                        idPostsGetAc = idPosts;
                        String content = jsonObject.getString("content");
                        String imgLink = jsonObject.getString("imgLink");
                        String createAt = jsonObject.getString("creaetAt");
                        String updateAt = jsonObject.getString("updateAt");
                        int likes = jsonObject.getInt("likes");

                        JSONObject account = jsonObject.getJSONObject("account");
                        String id = account.getString("id");
                        String username = account.getString("username");
                        String role = account.getString("role");
                        String fullname = account.getString("fullname");
                        String gender = account.getString("gender");
                        boolean status = account.getBoolean("status");
                        String linkAvt = account.getString("linkAvt");
                        String email = account.getString("email");
                        String creaetAt1 = account.getString("creaetAt");
                        String updateAt1 = account.getString("updateAt");
                        Friend account1 = new Friend(id, username, role, fullname, gender, status, linkAvt, email, creaetAt1, updateAt1);

                        Posts1 posts1 = new Posts1(idPosts, content, imgLink, createAt, updateAt, likes, account1);
                        listPost.add(posts1);

                    }
                    Collections.reverse(listPost);
                    PostsAdapter postsAdapter = new PostsAdapter(listPost, getContext());
                    binding.revPosts.setLayoutManager(linearLayoutManager);
                    binding.revPosts.setAdapter(postsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result = "error";
                Toast.makeText(getContext(), "call api with Volley fail", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}