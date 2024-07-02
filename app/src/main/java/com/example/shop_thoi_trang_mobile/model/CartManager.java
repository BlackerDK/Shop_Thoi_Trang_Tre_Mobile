package com.example.shop_thoi_trang_mobile.model;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItemList;

    private CartManager() {
        cartItemList = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void addItemToCart(CartItem item) {
        cartItemList.add(item);
    }

    public void removeItemFromCart(CartItem item) {
        cartItemList.remove(item);
    }
}