package com.example.shop_thoi_trang_mobile.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.recyclerView);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("ALL ORDER"));
        tabLayout.addTab(tabLayout.newTab().setText("PAY ON DELIVERY"));
        tabLayout.addTab(tabLayout.newTab().setText("PAY WITH ZALOPAY"));

        List<Order> orders = new ArrayList<>();
        OrderItem item1 = new OrderItem(1, 1, 1, 2, new BigDecimal(100), "Product A");
        OrderItem item2 = new OrderItem(2, 1, 2, 3, new BigDecimal(200), "Product B");
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(item1);
        orderItemList.add(item2);
        int totalAmount = (item1.getPrice().intValue() * item1.getQuantity()) + (item2.getPrice().intValue() * item2.getQuantity());
        orders.add(new Order(1, new Date(), 1, new BigDecimal(totalAmount), "Pending", 2, "Nguyen Van A", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        orders.add(new Order(2, new Date(), 1, new BigDecimal(totalAmount), "Pending", 3, "Nguyen Van B", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        orders.add(new Order(3, new Date(), 1, new BigDecimal(totalAmount), "Pending", 4, "Nguyen Van C", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        orders.add(new Order(4, new Date(), 1, new BigDecimal(totalAmount), "Pending", 5, "Nguyen Van D", "0123456789", "123 Nguyen  Van Tang", orderItemList));
        orders.add(new Order(5, new Date(), 1, new BigDecimal(totalAmount), "Pending", 6, "Nguyen Van E", "0123456789", "123 Nguyen  Van Tang", orderItemList));

        orderAdapter = new OrderAdapter(this, orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
    }
}