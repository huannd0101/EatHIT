package com.example.eathit.api.dto;

public class RoomChatDTO {
    private String sender;
    private String receiver;

    public RoomChatDTO() {
    }

    public RoomChatDTO(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
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
}
