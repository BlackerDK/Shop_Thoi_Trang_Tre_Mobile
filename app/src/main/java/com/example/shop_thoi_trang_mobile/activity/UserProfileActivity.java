package com.example.shop_thoi_trang_mobile.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.AuthResponse;
import com.example.shop_thoi_trang_mobile.model.ChangePass;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderResponse;
import com.example.shop_thoi_trang_mobile.networking.AuthService;
import com.example.shop_thoi_trang_mobile.networking.OrderService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
    private TextView txtUserName, txtId, txtEmail, txtAddress, txtPhone, txtChange ;
    private Button btnEditProfile, btnLogout;
    private LinearLayout orderHistory;
    private ImageView imgChat;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
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
        txtChange = findViewById(R.id.txt_change);

        txtChange.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassPopup();
            }
        });

        orderHistory.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, OrdersActivity.class);
            startActivity(intent);
        });
        imgChat = findViewById(R.id.chat);
        imgChat.setOnClickListener(v -> {
            startActivity(new Intent(UserProfileActivity.this, ChatBoxActivity.class));
            finish();
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
                    if(roleId == 3) intent = new Intent(UserProfileActivity.this, HomeActivity.class);
                    else intent = new Intent(UserProfileActivity.this, activity_product_admin.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    if(roleId == 3) intent = new Intent(UserProfileActivity.this, CartActivity.class);
                    else intent = new Intent(UserProfileActivity.this, activity_listorder_admin.class);
                } else if (item.getItemId() == R.id.nav_noti) {
                    // Chuyển sang activity Cart (ví dụ)
                    if(roleId == 3) intent = new Intent(UserProfileActivity.this, activity_notification.class);
                    else intent = new Intent(UserProfileActivity.this, ChatActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)

                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
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
    private void ChangePassPopup() {
        final Dialog dialog = new Dialog(UserProfileActivity.this);
        dialog.setContentView(R.layout.activity_changepass);
        TextView buttonCancel = dialog.findViewById(R.id.buttonCancel);
        TextView buttonChanges = dialog.findViewById(R.id.buttonChanges);
        EditText txtOldPass = dialog.findViewById(R.id.edOldPass);
        EditText txtNewPass = dialog.findViewById(R.id.edNewPass);

        String txtMailUser = txtEmail.getText().toString();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtOldPassUser = txtOldPass.getText().toString();
                String txtNewPassUser = txtNewPass.getText().toString();

                AuthService authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
                ChangePass changePass = new ChangePass(txtMailUser, txtOldPassUser, txtNewPassUser);
                Call<AuthResponse> call = authService.changePass(changePass);
                call.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.body() != null && response.body().isSuccess()) {
                            Toast.makeText(UserProfileActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(UserProfileActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable throwable) {
                        Toast.makeText(UserProfileActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}