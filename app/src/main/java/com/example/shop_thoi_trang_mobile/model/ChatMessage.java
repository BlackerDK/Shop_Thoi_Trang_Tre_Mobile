package com.example.shop_thoi_trang_mobile.model;

public class ChatMessage {
    private int userid;
    private String name;
    private String message;
    private long timestamp;

    public ChatMessage() {
        // Default constructor required for calls to DataSnapshot.getValue(ChatMessage.class)
    }

    public ChatMessage(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
    public ChatMessage(int userid, String message, long timestamp) {
        this.userid = userid;
        this.message = message;
        this.timestamp = timestamp;
    }
    public ChatMessage(int userid, String name, String message, long timestamp) {
        this.userid = userid;
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
