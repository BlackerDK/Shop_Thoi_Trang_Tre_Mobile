package com.example.shop_thoi_trang_mobile.activity;

// ProductDetailsActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.squareup.picasso.Picasso;
public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName;
    private TextView productCategory;
    private TextView productBrand;
    private TextView productPrice;
    private TextView productDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        super.setContentView(R.layout.activity_product_details); // Gọi rõ ràng từ lớp cha

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handle navigation click event
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productCategory = findViewById(R.id.product_category);
        productBrand = findViewById(R.id.product_brand);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);

        // Nhận đối tượng Product từ Intent
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

        if (product != null) {
            int imageResId = getResources().getIdentifier(product.getProductImage(), "drawable", getPackageName());
            // Thiết lập hình ảnh cho ImageView
            productImage.setImageResource(imageResId);
            productName.setText(product.getProductName());
            productCategory.setText("Category: " + product.getProductCategory());
            productBrand.setText("Brand: " + product.getProductBrand());
            productPrice.setText("$" + product.getProductPrice());
            productDescription.setText(product.getProductDescription());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
