package com.example.shop_thoi_trang_mobile.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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

import java.util.ArrayList;
import java.util.List;

public class activity_notification extends AppCompatActivity {

    List<Notice> notices;
    RecyclerView notificationRecycler;
    NotificationAdapter adapter;
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        notificationRecycler.setLayoutManager(gridLayoutManager);
        SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(5);
        notificationRecycler.addItemDecoration(spacingItemDecoration);
        notificationRecycler.setHasFixedSize(true);
        notices = new ArrayList<>();
        notices.add(new Notice(1, "Don hang cua ban dang giao", "Ao T size L", 1, "Dilivering"));
        notices.add(new Notice(1, "Don hang cua ban dang giao", "Ao T size L", 1, "Dilivering"));
        notices.add(new Notice(1, "Don hang cua ban dang giao", "Ao T size L", 1, "Dilivering"));
        notices.add(new Notice(1, "Don hang cua ban dang giao", "Ao T size L", 1, "Dilivering"));
        adapter = new NotificationAdapter(this, (ArrayList<Notice>) notices);
        notificationRecycler.setAdapter(adapter);
    }
}