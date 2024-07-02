package com.example.shop_thoi_trang_mobile.model;

public class AuthResponse {
    private User result;
    private boolean isSuccess;
    private String message;
    public User getResult() {
        return result;
    }
    public void setResult(User result) {
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
