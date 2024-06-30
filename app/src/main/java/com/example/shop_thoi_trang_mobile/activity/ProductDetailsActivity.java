package com.example.shop_thoi_trang_mobile.activity;

// ProductDetailsActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.networking.ProductService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView productImage;
    private TextView productName;
    private TextView productCategory;
    private TextView productBrand;
    private TextView productPrice;
    private ProductService productService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productCategory = findViewById(R.id.product_category);
        productBrand = findViewById(R.id.product_brand);
        productPrice = findViewById(R.id.product_price);

        Intent intent = getIntent();
        int productId = intent.getIntExtra("productId", -1);

        if (productId != -1) {
            productService = RetrofitClient.getRetrofitInstance().create(ProductService.class);
            fetchProductDetails(productId);
        }
    }

    private void fetchProductDetails(int productId) {
        Call<ProductResponse> call = productService.getAllProduct(); // Assuming the API returns all products
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getResult();
                    for (Product product : products) {
                        if (product.getProductId() == productId) {
                            // Found the product, update UI
                            Picasso.get().load(product.getProductImage()).into(productImage);
                            productName.setText(product.getProductName());
                            productCategory.setText("Category: " + product.getProductCategory());
                            productBrand.setText("Brand: " + product.getProductBrand());
                            productPrice.setText("$" + product.getProductPrice());
                            break;
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
