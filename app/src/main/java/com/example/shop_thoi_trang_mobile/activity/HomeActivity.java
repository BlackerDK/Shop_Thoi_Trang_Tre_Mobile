package com.example.shop_thoi_trang_mobile.activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.ProductAdapter;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private ProductAdapter productAdapter;
        private List<Product> productList;
        SearchView searchView;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            //Take recycleView
            recyclerView = findViewById(R.id.recycler_view_products);
            // Take List Product
            productList = getListProduct();
            // Set productAdapter
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(productAdapter);
            searchView = findViewById(R.id.search_product);
            searchView.clearFocus();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterList(newText);
                    return false;
                }
            });
            // Set up the toolbar
            androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

            // Set up bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Chuyển sang activity Home (ví dụ)
                    intent = new Intent(HomeActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    intent = new Intent(HomeActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_noti) {
                    // Chuyển sang activity Cart (ví dụ)
                    intent = new Intent(HomeActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)
                    intent = new Intent(HomeActivity.this, HomeActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }

    private void filterList(String newText) {
        List<Product> filterList = new ArrayList<>();
        for (Product itemProduct : productList){
            if (itemProduct.getProductName().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(itemProduct);
            }
        }
        if (filterList.isEmpty()){
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }else {
            productAdapter.setFilter(filterList);
        }
    }

    private List<Product> getListProduct() {
        List<Product> list  = new ArrayList<>();
        list.add(new Product(1, "Ao 1", "P001", "Category 1", "Brand 1", new BigDecimal("10.00"), 10, "Description 1", "sample_product", "Available"));
        list.add(new Product(2, "Ao tot 2", "P001", "Category 1", "Brand 1", new BigDecimal("10.00"), 10, "Description 1", "sample_product", "Available"));
        list.add(new Product(3, "Quan dep 3", "P001", "Category 1", "Brand 1", new BigDecimal("10.00"), 10, "Description 1", "sample_product", "Available"));
        list.add(new Product(4, "Quan xau 4", "P001", "Category 1", "Brand 1", new BigDecimal("10.00"), 10, "Description 1", "sample_product", "Available"));
    return list;
    }

}
