package com.example.shop_thoi_trang_mobile.model;

import java.util.ArrayList;

public class OrderRequest {
    private int userId;
    private double total;
    private String paymentType;
    private ArrayList<CartItemObjRequest> items;

    public OrderRequest(int userId, double total, String paymentType, ArrayList<CartItemObjRequest> items) {
        this.userId = userId;
        this.total = total;
        this.paymentType = paymentType;
        this.items = items;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public ArrayList<CartItemObjRequest> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItemObjRequest> items) {
        this.items = items;
    }
}
