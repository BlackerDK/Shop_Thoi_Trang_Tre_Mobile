package com.example.shop_thoi_trang_mobile.activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import java.math.BigDecimal;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private ProductAdapter productAdapter;
        private DatabaseHelper databaseHelper;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            // Set up the toolbar
            androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Initialize RecyclerView
            recyclerView = findViewById(R.id.recycler_view_products);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

            // Initialize database helper
            databaseHelper = new DatabaseHelper(this);

            // Add some sample products to the database (this can be skipped if data already exists)
            addSampleProducts();

            // Load products from the database
            List<Product> productList = databaseHelper.getAllProducts();

            // Initialize product adapter
            productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Product product) {
                    Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                    intent.putExtra("product", product);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(productAdapter);

            // Set up bottom navigation
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
//                        case R.id.nav_home:
//                            // Handle home action
//                            return true;
//                        case R.id.nav_category:
//                            // Handle category action
//                            return true;
//                        case R.id.nav_cart:
//                            // Handle cart action
//                            return true;
//                        case R.id.nav_profile:
//                            // Handle profile action
//                            return true;
                    }
                    return false;
                }
            });
        }

        private void addSampleProducts() {
            if (databaseHelper.getAllProducts().isEmpty()) {
                databaseHelper.addProduct(new Product(0, "Product 1", "P001", "Category 1", "Brand 1", new BigDecimal("10.00"), 10, "Description 1", "sample_product", "Available"));
                databaseHelper.addProduct(new Product(0, "Product 2", "P002", "Category 2", "Brand 2", new BigDecimal("20.00"), 20, "Description 2", "sample_product", "Available"));
                databaseHelper.addProduct(new Product(0, "Product 3", "P003", "Category 3", "Brand 3", new BigDecimal("30.00"), 30, "Description 3", "sample_product", "Available"));
                databaseHelper.addProduct(new Product(0, "Product 4", "P004", "Category 4", "Brand 4", new BigDecimal("40.00"), 40, "Description 4", "sample_product", "Available"));
            }
        }
    }
