package com.example.eathit.api;

import java.sql.Timestamp;

public class Message {
    private Integer messageId;
    private String content;
    private String sender;
    private String receiver;
    private Integer roomChat;
    private Timestamp createAt;

    public Message(Integer messageId, String content, String sender, String receiver, Integer roomChat, Timestamp createAt) {
        this.messageId = messageId;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.roomChat = roomChat;
        this.createAt = createAt;
    }

    public Message() {
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getRoomChat() {
        return roomChat;
    }

    public void setRoomChat(Integer roomChat) {
        this.roomChat = roomChat;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}
