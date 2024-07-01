package com.example.shop_thoi_trang_mobile.activity;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shop_thoi_trang_mobile.R;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rv_cart_items;
    private TextView address, subtotal, shipping_fee, total_price;
    private RadioGroup payment_method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rv_cart_items = findViewById(R.id.rv_cart_items);
        address = findViewById(R.id.address);
    }
}
