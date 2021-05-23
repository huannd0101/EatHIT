package com.example.eathit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {

    @GET("api/roomchats")
    Call<List<RoomChat>> getRoomChats();

    @GET("api/messages")
    Call<List<Message>> getMessage();
}
