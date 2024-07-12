package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.ViewPagerAdapter;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderItem;
import com.example.shop_thoi_trang_mobile.model.ResponseBase;
import com.example.shop_thoi_trang_mobile.networking.AdminBaseService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_listorder_admin extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager2 viewpager;
    ViewPagerAdapter viewPagerAdapter;
    List<Order> orderItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listorder_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        bottomNavigationView.setPadding(0, 0, 0, 0);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Chuyển sang activity Home (ví dụ)
                    intent = new Intent(activity_listorder_admin.this, activity_product_admin.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    intent = new Intent(activity_listorder_admin.this, activity_listorder_admin.class);
                } else if (item.getItemId() == R.id.nav_noti) {
                    // Chuyển sang activity Cart (ví dụ)
                    intent = new Intent(activity_listorder_admin.this, ChatActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)
                    intent = new Intent(activity_listorder_admin.this, UserProfileActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        orderItems = new ArrayList<Order>();
/*        orderItems.add(new Order(1, (new Date()), 1, BigDecimal.valueOf(10000), "Dang giao hang"));
        orderItems.add(new Order(1, (new Date()), 1, BigDecimal.valueOf(10000), "Dang giao hang"));
        orderItems.add(new Order(1, (new Date()), 1, BigDecimal.valueOf(10000), "Dang giao hang"));
        orderItems.add(new Order(1, (new Date()), 1, BigDecimal.valueOf(10000), "Dang giao hang"));*/
        viewPagerAdapter = new ViewPagerAdapter(this, orderItems);
        viewpager.setAdapter(viewPagerAdapter);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tablayout.getTabAt(position).select();
            }
        });
    }


}