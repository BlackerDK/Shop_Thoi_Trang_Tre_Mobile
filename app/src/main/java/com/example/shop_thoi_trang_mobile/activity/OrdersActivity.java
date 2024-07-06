// OrdersActivity.java
package com.example.shop_thoi_trang_mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.OrderAdapter;
import com.example.shop_thoi_trang_mobile.model.Order;

import com.example.shop_thoi_trang_mobile.model.OrderResponse;
import com.example.shop_thoi_trang_mobile.networking.OrderService;

import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderService orderService;
    private List<Order> orders = new ArrayList<>();
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.recyclerView);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("ALL ORDER"));
        tabLayout.addTab(tabLayout.newTab().setText("PAY ON DELIVERY"));
        tabLayout.addTab(tabLayout.newTab().setText("PAY WITH ZALOPAY"));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Chuyển sang activity Home (ví dụ)
                    intent = new Intent(OrdersActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    intent = new Intent(OrdersActivity.this, CartActivity.class);
                } else if (item.getItemId() == R.id.nav_noti) {
                    // Chuyển sang activity Cart (ví dụ)
                    intent = new Intent(OrdersActivity.this, OrdersActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)
                    intent = new Intent(OrdersActivity.this, UserProfileActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        int userId = getUserIdFromPreferences();
        orderService = RetrofitClient.getRetrofitInstance().create(OrderService.class);


        orderAdapter = new OrderAdapter(this, orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

        loadInitialOrders(userId);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    loadMoreOrders(userId);
                }
            }
        });

    }

    private void loadInitialOrders(int userId) {
        isLoading = true; // Set loading flag to true
        fetchOrdersFromDataSource(userId, new OrdersCallback() {
            @Override
            public void onSuccess(List<Order> newOrders) {
                orders.clear();
                orders.addAll(newOrders);
                orderAdapter.notifyDataSetChanged();
                isLoading = false; // Set loading flag to false
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("API_ERROR", "Error fetching Orders", t);
                isLoading = false; // Set loading flag to false
            }
        });
    }

    private void loadMoreOrders(int userId) {
        isLoading = true; // Set loading flag to true
        fetchOrdersFromDataSource(userId, new OrdersCallback() {
            @Override
            public void onSuccess(List<Order> newOrders) {
                orderAdapter.addOrders(newOrders);
                isLoading = false; // Set loading flag to false
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("API_ERROR", "Error fetching Orders", t);
                isLoading = false; // Set loading flag to false
            }
        });
    }

    private void fetchOrdersFromDataSource(int userId, final OrdersCallback callback) {
        Call<OrderResponse> call = orderService.getOrdersByUserId(userId);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> orderList = response.body().getResult();
                    if (orderList != null) {
                        callback.onSuccess(orderList);
                    } else {
                        callback.onFailure(new Throwable("Order list is null"));
                    }
                } else {
                    Log.e("API_ERROR", "Response unsuccessful");
                    callback.onFailure(new Throwable("Response unsuccessful"));
                }
                Log.d("API_RESPONSE", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error fetching Orders", t);
                callback.onFailure(t);
            }
        });
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 7);
    }

    interface OrdersCallback {
        void onSuccess(List<Order> newOrders);

        void onFailure(Throwable t);
    }
}
