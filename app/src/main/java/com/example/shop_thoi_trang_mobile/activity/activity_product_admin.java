package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class activity_product_admin extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<Product> products;
    SearchView search;
    ProductAdminAdapter adapter;

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

        recyclerView = findViewById(R.id.recyclerViewProduct);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(5);
        recyclerView.addItemDecoration(spacingItemDecoration);
        recyclerView.setHasFixedSize(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        products = new ArrayList<Product>();
        products.add(new Product(1,"haha", "100000","haha", "hqhqh", BigDecimal.ONE, 100, "hahah","https://beyono.vn/wp-content/uploads/2023/08/ao-polo-thoi-trang-the-thao-beyono-finn-nau-2.webp" ));
        products.add(new Product(1,"haha", "100000","haha", "hqhqh", BigDecimal.ONE, 100, "hahah","https://beyono.vn/wp-content/uploads/2023/08/ao-polo-thoi-trang-the-thao-beyono-finn-nau-2.webp" ));
        products.add(new Product(1,"haha", "100000","haha", "hqhqh", BigDecimal.ONE, 100, "hahah","https://beyono.vn/wp-content/uploads/2023/08/ao-polo-thoi-trang-the-thao-beyono-finn-nau-2.webp" ));
        adapter = new ProductAdminAdapter(this, products);
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
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