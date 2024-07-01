package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.CartAdapter;
import com.example.shop_thoi_trang_mobile.model.CartItem;
import com.example.shop_thoi_trang_mobile.model.CartManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecylerView;
    private CartAdapter cartAdapter;
    private CartManager cartManager;
    private TextView address, subtotal, shipping_fee, total_price;
    private RadioGroup payment_method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartRecylerView = findViewById(R.id.rv_cart_items);

        cartManager = CartManager.getInstance();

        List<CartItem> cartItemList = cartManager.getCartItemList();
        cartAdapter = new CartAdapter(cartItemList, this);
        cartRecylerView.setAdapter(cartAdapter);
        cartRecylerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Chuyển sang activity Home (ví dụ)
                    intent = new Intent(CartActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    intent = new Intent(CartActivity.this, CartActivity.class);
                } else if (item.getItemId() == R.id.nav_noti) {
                    // Chuyển sang activity Cart (ví dụ)
                    intent = new Intent(CartActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)
                    intent = new Intent(CartActivity.this, HomeActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
