package com.example.shop_thoi_trang_mobile.model;

import java.util.ArrayList;

public class AuthListResponse {
    private ArrayList<User> result;
    private boolean isSuccess;
    private String message;
    public ArrayList<User> getResult() {
        return result;
    }
    public void setResult(ArrayList<User> result) {
        this.result = result;
    }
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
