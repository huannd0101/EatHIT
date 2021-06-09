package com.example.eathit.api;

import com.example.eathit.api.dto.MessageDTO;
import com.example.eathit.api.dto.RoomChatDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {
    String url = "https://api-mess.herokuapp.com/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiServices apiServices = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices.class);

    @GET("api/roomchats")
    Call<List<RoomChat>> getRoomChats();

    @GET("api/messages")
    Call<List<Message>> getMessage();

    @POST("api/roomchats")
    Call<RoomChat> postNewRoomChat(@Body RoomChatDTO roomChatDTO);

    @POST("api/messages")
    Call<MessageDTO> postNewMessage(@Body MessageDTO messageDTO);

}
