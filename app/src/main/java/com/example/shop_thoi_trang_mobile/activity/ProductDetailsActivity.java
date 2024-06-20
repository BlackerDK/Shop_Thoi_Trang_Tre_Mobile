package com.example.shop_thoi_trang_mobile.activity;

// ProductDetailsActivity.java

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Product;

public class ProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Get the product data from the Intent
        Product product = (Product) getIntent().getSerializableExtra("product");

        // Initialize your views and display product data
    }
}

