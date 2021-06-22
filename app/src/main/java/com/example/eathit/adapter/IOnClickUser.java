package com.example.eathit.adapter;

import com.example.eathit.modules.User;
import com.github.nkzawa.socketio.client.Socket;

public interface IOnClickUser {
    void clickUser(User user, Socket socket, int position);
}
