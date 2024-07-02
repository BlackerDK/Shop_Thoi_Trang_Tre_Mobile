package com.example.shop_thoi_trang_mobile.model;

public class CartItem {
    private String name;
    private int quantity;
    private String image;
    private double price;

    public CartItem(String name, int quantity, double price, String image) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}