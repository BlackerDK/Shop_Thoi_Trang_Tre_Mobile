package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.NotificationAdapter;
import com.example.shop_thoi_trang_mobile.adapter.SpacingItemDecoration;
import com.example.shop_thoi_trang_mobile.model.Notice;
import com.example.shop_thoi_trang_mobile.model.ResponseBase;
import com.example.shop_thoi_trang_mobile.networking.AdminBaseService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_notification extends AppCompatActivity {

    List<Notice> notices;
    RecyclerView notificationRecycler;
    NotificationAdapter adapter;
    AdminBaseService notificationGetService;

    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        notificationRecycler = findViewById(R.id.notificationRecycler);
        notificationGetService = RetrofitClient.getRetrofitInstance().create(AdminBaseService.class);
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_noti);
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        bottomNavigationView.setPadding(0, 0, 0, 0);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Chuyển sang activity Home (ví dụ)
                    intent = new Intent(activity_notification.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    intent = new Intent(activity_notification.this, CartActivity.class);
                } else if (item.getItemId() == R.id.nav_noti) {
                    // Chuyển sang activity Cart (ví dụ)
                    //intent = new Intent(activity_notification.this, activity_notification.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)
                    intent = new Intent(activity_notification.this, UserProfileActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        notificationRecycler.setLayoutManager(gridLayoutManager);
        SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(5);
        notificationRecycler.addItemDecoration(spacingItemDecoration);
        notificationRecycler.setHasFixedSize(true);
        notices = new ArrayList<>();
       /* notices.add(new Notice(1, "Don hang cua ban dang giao", "Ao T size L", 1, "Dilivering"));
        notices.add(new Notice(1, "Don hang cua ban dang giao", "Ao T size L", 1, "Dilivering"));
        notices.add(new Notice(1, "Don hang cua ban dang giao", "Ao T size L", 1, "Dilivering"));
        notices.add(new Notice(1, "Don hang cua ban dang giao", "Ao T size L", 1, "Dilivering"));*/
        adapter = new NotificationAdapter(this, (ArrayList<Notice>) notices);
        notificationRecycler.setAdapter(adapter);
        LoadNotification();
    }

    private void LoadNotification() {
        Call<ResponseBase<Notice>> call = notificationGetService.getNotifications();
        call.enqueue(new Callback<ResponseBase<Notice>>() {
            @Override
            public void onResponse(Call<ResponseBase<Notice>> call, Response<ResponseBase<Notice>> response) {
                if(response.isSuccessful() && response.body() != null){
                    adapter.setList(response.body().getResult().stream().filter(s -> s.getUserId() == userId).collect(Collectors.toList()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBase<Notice>> call, Throwable throwable) {

            }
        });
    }
}