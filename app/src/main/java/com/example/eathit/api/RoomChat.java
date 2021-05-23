package com.example.eathit.api;

public class RoomChat {
    private Integer id;
    private String sender;
    private String receiver;

    public RoomChat(Integer id, String sender, String receiver) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
    }

    public RoomChat() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "RoomChat{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
