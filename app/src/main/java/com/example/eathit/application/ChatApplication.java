package com.example.eathit.application;

import android.app.Application;

import com.example.eathit.utilities.Constants;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class ChatApplication extends Application {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.URL_SOCKET_IO);
            mSocket.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Socket getSocket(){
        return mSocket;
    }
}
