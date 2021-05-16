package com.example.eathit.modules;

public class ChatTest {
    private String message;
    private String sender;
    private long timestamp;


    public ChatTest(String message, String sender, long timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ChatTest(){}

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
