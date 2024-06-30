package com.example.shop_thoi_trang_mobile.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.OrderAdapter;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderItem;
import com.google.android.material.tabs.TabLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orders = new ArrayList<>();
    private boolean isLoading = false; // To prevent multiple loads at once

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.recyclerView);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("ALL ORDER"));
        tabLayout.addTab(tabLayout.newTab().setText("PAY ON DELIVERY"));
        tabLayout.addTab(tabLayout.newTab().setText("PAY WITH ZALOPAY"));

        orderAdapter = new OrderAdapter(this, orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

        loadInitialOrders();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    loadMoreOrders();
                }
            }
        });
    }

    private void loadInitialOrders() {
        // Load the initial set of orders
        List<Order> initialOrders = fetchOrdersFromDataSource();
        orders.addAll(initialOrders);
        orderAdapter.notifyDataSetChanged();
    }

    private List<Order> fetchOrdersFromDataSource() {
        // Replace with actual logic to fetch more orders
        // For example, this could be a network request to fetch additional orders
        // return new ArrayList<>();

        OrderItem item1 = new OrderItem(1, 1, 1, 2, new BigDecimal(100), "Product A");
        OrderItem item2 = new OrderItem(2, 1, 2, 3, new BigDecimal(200), "Product B");
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(item1);
        orderItemList.add(item2);

        List<Order> newOrders = new ArrayList<>();
        int totalAmount = (item1.getPrice().intValue() * item1.getQuantity()) + (item2.getPrice().intValue() * item2.getQuantity());
        newOrders.add(new Order(1, new Date(), 1, new BigDecimal(totalAmount), "Pending", 2, "Nguyen Van A", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        newOrders.add(new Order(2, new Date(), 1, new BigDecimal(totalAmount), "Pending", 3, "Nguyen Van B", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        newOrders.add(new Order(3, new Date(), 1, new BigDecimal(totalAmount), "Pending", 4, "Nguyen Van C", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        newOrders.add(new Order(4, new Date(), 1, new BigDecimal(totalAmount), "Pending", 5, "Nguyen Van D", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        newOrders.add(new Order(5, new Date(), 1, new BigDecimal(totalAmount), "Pending", 6, "Nguyen Van E", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        return newOrders;
    }

    private void loadMoreOrders() {
        isLoading = true; // Set loading flag to true
        // Fetch more orders from your data source
        List<Order> newOrders = fetchOrdersFromDataSource();

        if (newOrders != null && !newOrders.isEmpty()) {
            // Add the new orders to the existing list
            orderAdapter.addOrders(newOrders);
        }
        isLoading = false; // Set loading flag to false
    }
}
