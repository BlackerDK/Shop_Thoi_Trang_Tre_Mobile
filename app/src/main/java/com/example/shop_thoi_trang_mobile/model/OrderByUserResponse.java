package com.example.shop_thoi_trang_mobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderByUserResponse {
    private OrderResult result;
    private boolean isSuccess;
    private String message;

    public OrderResult getResult() {
        return result;
    }

    public void setResult(OrderResult result) {
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

    public static class OrderResult {
        @SerializedName("head")
        private Order head;

        @SerializedName("items")
        private List<OrderItem> items;

        public Order getHead() {
            return head;
        }

        public void setHead(Order head) {
            this.head = head;
        }

        public List<OrderItem> getItems() {
            return items;
        }

        public void setItems(List<OrderItem> items) {
            this.items = items;
        }
    }
}
