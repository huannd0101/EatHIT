package com.example.eathit.api.dto;


public class MessageDTO {
    private String content;
    private String sender;
    private String receiver;
    private Integer roomChat;

    public MessageDTO(String content, String sender, String receiver, Integer roomChat) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.roomChat = roomChat;
    }

    public MessageDTO() {
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

    @Override
    public String toString() {
        return "MessageDTO{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", roomChat=" + roomChat +
                '}';
    }
}
