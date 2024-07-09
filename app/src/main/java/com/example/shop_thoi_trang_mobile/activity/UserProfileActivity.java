package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderResponse;
import com.example.shop_thoi_trang_mobile.networking.OrderService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
    private TextView txtUserName, txtId, txtEmail, txtAddress, txtPhone, txt_total_order_paid, txt_total_order_value;
    private Button btnEditProfile, btnLogout;
    private LinearLayout orderHistory;
    private ImageView imgChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        int roleId = sharedPreferences.getInt("role", 0);
        String userName = sharedPreferences.getString("userName", null);
        String userEmail = sharedPreferences.getString("userEmail", null);
        String userPhone = sharedPreferences.getString("userPhone", null);
        String userAddress = sharedPreferences.getString("userAddress", null);

        txtUserName = findViewById(R.id.txt_username);
        txtId = findViewById(R.id.txt_id);
        txtEmail = findViewById(R.id.txt_email);
        txtAddress = findViewById(R.id.txt_address);
        txtPhone = findViewById(R.id.txt_phone);
        btnLogout = findViewById(R.id.btn_logout);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        orderHistory = findViewById(R.id.orderHistory);
        txt_total_order_paid = findViewById(R.id.txt_total_order_paid);
        txt_total_order_value = findViewById(R.id.txt_total_order_value);

        orderHistory.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, OrdersActivity.class);
            startActivity(intent);
        });
        imgChat = findViewById(R.id.chat);
        imgChat.setOnClickListener(v -> {
            Intent intent = null;
            if (roleId == 3) {
                intent = new Intent(UserProfileActivity.this, ChatBoxActivity.class);
            }
            else {
                intent = new Intent(UserProfileActivity.this, ChatActivity.class);
            }
            startActivity(intent);
        });
        if (userName != null && userEmail != null) {
            txtUserName.setText("Username : " + userName);
            txtId.setText("ID : " + userId);
            txtEmail.setText(userEmail);
            txtAddress.setText(userAddress);
            txtPhone.setText(userPhone);

        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Chuyển sang activity Home (ví dụ)
                    intent = new Intent(UserProfileActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    intent = new Intent(UserProfileActivity.this, CartActivity.class);
                } else if (item.getItemId() == R.id.nav_noti) {
                    // Chuyển sang activity Cart (ví dụ)
                    intent = new Intent(UserProfileActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)
                    intent = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("userId");
            editor.remove("userName");
            editor.remove("userEmail");
            editor.remove("userPhone");
            editor.remove("userAddress");
            editor.apply();
            startActivity(new Intent(UserProfileActivity.this, SignInActivity.class));
        });

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UserEditProfileActivity.class);
            startActivity(intent);
        });

        // fetch all orders of a specific user by id
        fetchOrdersFromDataSource(userId, new OrdersActivity.OrdersCallback() {
            @Override
            public void onSuccess(List<Order> orders) {
                int totalOrderValue = orders.size();
                txt_total_order_value.setText("Total Orders : " + totalOrderValue);
                Log.d("API_RESPONSE", "Orders fetched successfully");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("API_ERROR", "Error fetching Orders", t);
            }
        });
    }

    private void fetchOrdersFromDataSource(int userId, final OrdersActivity.OrdersCallback callback) {

        OrderService orderService = RetrofitClient.getRetrofitInstance().create(OrderService.class);
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

}