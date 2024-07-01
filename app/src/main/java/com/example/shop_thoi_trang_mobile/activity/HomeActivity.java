package com.example.shop_thoi_trang_mobile.activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.ProductAdapter;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.networking.ProductService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private ProductAdapter productAdapter;
        private List<Product> productList;
        private ProductService productService;
        SearchView searchView;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            //Take recycleView
            recyclerView = findViewById(R.id.recycler_view_products);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                intent.putExtra("productId", product.getProductId());
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
        productService = RetrofitClient.getRetrofitInstance().create(ProductService.class);
        fetchProducts();
    }
    private void fetchProducts() {
        Call<ProductResponse> call = productService.getAllProduct();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Product> products = response.body().getResult();
                        productList.clear(); // Xóa danh sách hiện tại
                        productList.addAll(products); // Thêm danh sách mới từ API
                        productAdapter.notifyDataSetChanged(); // Cập nhật giao diện
                    } else {
                        Log.e("API_ERROR", "Response unsuccessful");
                    }
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Xử lý lỗi, ví dụ: thông báo lỗi cho người dùng
                Log.e("API_ERROR", "Error fetching products", t);
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
}
