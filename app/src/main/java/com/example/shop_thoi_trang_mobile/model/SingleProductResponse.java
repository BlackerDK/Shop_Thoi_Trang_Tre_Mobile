package com.example.shop_thoi_trang_mobile.model;

import java.util.List;

public class SingleProductResponse {
    private Product result;
    private boolean isSuccess;
    private String message;

    public Product getResult() {
        return result;
    }

    public void setResult(Product result) {
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
