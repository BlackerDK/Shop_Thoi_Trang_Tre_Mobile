package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.ProductAdminAdapter;
import com.example.shop_thoi_trang_mobile.adapter.SpacingItemDecoration;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.networking.ProductService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_product_admin extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<Product> products;
    SearchView search;
    ProductAdminAdapter adapter;
    private ProductService productService;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fab = findViewById(R.id.fab);
        search = findViewById(R.id.search);
        search.clearFocus();

        productService = RetrofitClient.getRetrofitInstance().create(ProductService.class);


        recyclerView = findViewById(R.id.recyclerViewProduct);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(5);
        recyclerView.addItemDecoration(spacingItemDecoration);
        recyclerView.setHasFixedSize(true);
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();
        products = new ArrayList<>();
        /*products.add(new Product(1,"haha", "100000","haha", "hqhqh", BigDecimal.ONE, 100, "hahah","https://beyono.vn/wp-content/uploads/2023/08/ao-polo-thoi-trang-the-thao-beyono-finn-nau-2.webp" ));
        products.add(new Product(1,"haha", "100000","haha", "hqhqh", BigDecimal.ONE, 100, "hahah","https://beyono.vn/wp-content/uploads/2023/08/ao-polo-thoi-trang-the-thao-beyono-finn-nau-2.webp" ));
        products.add(new Product(1,"haha", "100000","haha", "hqhqh", BigDecimal.ONE, 100, "hahah","https://beyono.vn/wp-content/uploads/2023/08/ao-polo-thoi-trang-the-thao-beyono-finn-nau-2.webp" ));*/
        adapter = new ProductAdminAdapter(this, products);
        recyclerView.setAdapter(adapter);
        LoadProducts();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_product_admin.this, activity_addproduct_admin.class);
                startActivity(intent);
            }
        });
    }

    private void LoadProducts() {
        Call<ProductResponse> call = productService.getAllProduct();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    products.addAll(response.body().getResult());
                    adapter.loadData(response.body().getResult());
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                dialog.dismiss();
                throwable.printStackTrace();

            }
        });
    };

    public void searchList(String text){
        ArrayList<Product> searchList = new ArrayList<>();
        for(Product item : products){
            if(item.getProductName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(item);
            }
        };
        adapter.searchData(searchList);
    }
}