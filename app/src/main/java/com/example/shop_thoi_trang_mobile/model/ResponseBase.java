package com.example.shop_thoi_trang_mobile.model;

import java.util.List;

public class ResponseBase<T> {
    private List<T> result;
    private boolean isSuccess;
    private String message;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
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
