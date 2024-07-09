package com.example.shop_thoi_trang_mobile.model;

public class ChatList {
    private int id;
    private String name, lastMsg;
    private int unreadMsg;
    public ChatList(int id, String name, String lastMsg, int unreadMsg) {
        this.id = id;
        this.name = name;
        this.lastMsg = lastMsg;
        this.unreadMsg = unreadMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public void setUnreadMsg(int unreadMsg) {
        this.unreadMsg = unreadMsg;
    }

    public String getName() {
        return name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public int getUnreadMsg() {
        return unreadMsg;
    }
}
