package com.example.eathit.ui.slideshow.API;

import com.example.eathit.ui.slideshow.Comment.Comment;
import com.example.eathit.ui.slideshow.Friend;
import com.example.eathit.ui.slideshow.Posts.Posts;
import com.example.eathit.ui.slideshow.Posts.Posts1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {
    static final String DOMAIN="https://btl-spring-boot.herokuapp.com/api/";

    Gson gson =new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    APIService apiService = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("posts")
    Call<List<Posts1>> getAllPosts();

    @GET("posts/getAccount/{idPosts}")
    Call<Friend> getAccountOfPosts(@Path(value = "idPosts", encoded = true) Integer idPosst);

    @GET("comment/posts/{idPosts}")
    Call<Comment[]> getAllCommentOfPosts(@Path(value = "idPosts", encoded = true) Integer idPosst);

}
