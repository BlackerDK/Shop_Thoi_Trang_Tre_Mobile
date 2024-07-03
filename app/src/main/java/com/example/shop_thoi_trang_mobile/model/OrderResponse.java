package com.example.shop_thoi_trang_mobile.model;

import java.util.List;

public class OrderResponse {
    private List<Order> result;
    private boolean isSuccess;
    private String message;

    public List<Order> getResult() {
        return result;
    }

    public void setResult(List<Order> result) {
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
