package com.example.shop_thoi_trang_mobile.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.AuthResponse;
import com.example.shop_thoi_trang_mobile.model.UserUpdateRequest;
import com.example.shop_thoi_trang_mobile.networking.AuthService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;

import retrofit2.Call;

public class UserEditProfileActivity extends AppCompatActivity {
    private EditText txtEditUserName, txtEditEmail, txtEditPhone, txtEditAddress, txtEditPassword;
    private Button btnSaveChanged;
    private SharedPreferences sharedPreferences;
    private String userName, userPhone, userAddress, userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);


        txtEditUserName = findViewById(R.id.edit_username);
        txtEditEmail = findViewById(R.id.edit_email);
        txtEditPhone = findViewById(R.id.edit_phone_number);
        txtEditAddress = findViewById(R.id.edit_address);
        btnSaveChanged = findViewById(R.id.btn_save_changed);

        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);

        userName = sharedPreferences.getString("userName", null);
        userPhone = sharedPreferences.getString("userPhone", null);
        userAddress = sharedPreferences.getString("userAddress", null);
        userEmail = sharedPreferences.getString("userEmail", null);

        txtEditUserName.setText(userName);
        txtEditEmail.setText(userEmail);
        txtEditPhone.setText(userPhone);
        txtEditAddress.setText(userAddress);

        btnSaveChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUpdate();
            }
        });
    }

    private void confirmUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update User");
        builder.setMessage("Are you sure you want to update user?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            editUser();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void editUser() {
        int id = sharedPreferences.getInt("userId", 0);
        String updatedUserName = txtEditUserName.getText().toString();
        String updatedUserPhone = txtEditPhone.getText().toString();
        String updatedUserAddress = txtEditAddress.getText().toString();

        if (updatedUserName.isEmpty() || updatedUserPhone.isEmpty() || updatedUserAddress.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthService authService = RetrofitClient.getRetrofitInstance().create(
                AuthService.class);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(id, updatedUserName, updatedUserPhone, updatedUserAddress);
        Call<AuthResponse> call = authService.updateUser(userUpdateRequest);
        call.enqueue(new retrofit2.Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, retrofit2.Response<AuthResponse> response) {
                if (response.body().isSuccess()) {
                    Toast.makeText(UserEditProfileActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName", updatedUserName);
                    editor.putString("userPhone", updatedUserPhone);
                    editor.putString("userAddress", updatedUserAddress);

                    txtEditUserName.setText(updatedUserName);
                    txtEditPhone.setText(updatedUserPhone);
                    txtEditAddress.setText(updatedUserAddress);
                    editor.apply();
                    finish();
                } else {
                    Toast.makeText(UserEditProfileActivity.this, "Failed to update user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(UserEditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
