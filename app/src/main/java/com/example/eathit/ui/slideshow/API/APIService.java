package com.example.eathit.ui.slideshow.API;

import com.example.eathit.ui.slideshow.Comment.Comment;
import com.example.eathit.ui.slideshow.Posts.Posts1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {
    public static final String DOMAIN="https://btl-spring-boot.herokuapp.com/api/";

    Gson gson =new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    APIService apiService = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);
    @Multipart
    @PATCH("posts/{id}/create_with_photo")
    Call<Posts1> createPostsWhitePhoto(@Path(value = "id", encoded = true) String id, @Part("content") RequestBody content,
                                       @Part MultipartBody.Part img);

    @Multipart
    @PATCH("posts/{id}/create_only_Content")
    Call<Posts1> create_only_content(@Path(value = "id", encoded = true) String id, @Part("content") RequestBody content);


    @Multipart
    @POST("comment/posts_img/{idNew}/{idPosts}")
    Call<Comment> createCmt_Posts_Img(@Path(value = "idNew", encoded = true) String idNew, @Path(value = "idPosts", encoded = true) int idPosts,
                                  @Part("content") RequestBody content, @Part MultipartBody.Part img);

    @Multipart
    @POST("comment/posts/{idNew}/{idPosts}")
    Call<Comment> createCmt_Posts_No_Image(@Path(value = "idNew", encoded = true) String idNew, @Path(value = "idPosts", encoded = true) int idPosts,
                                      @Part("content") RequestBody content);



    //@Path(value = "id", encoded = true) String id,


}
