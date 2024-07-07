package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.AuthResponse;
import com.example.shop_thoi_trang_mobile.model.User;
import com.example.shop_thoi_trang_mobile.model.UserRequest;
import com.example.shop_thoi_trang_mobile.networking.AuthService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity {
    private EditText ipCode1, ipCode2, ipCode3, ipCode4, ipCode5, ipCode6;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.verify_phone);
        Intent receivedIntent = getIntent();
        UserRequest receivedUserRequest = receivedIntent.getParcelableExtra("userRequest");
        String otpSent = receivedIntent.getStringExtra("otp");
        ipCode1 = findViewById(R.id.ipCode1);
        ipCode2 = findViewById(R.id.ipCode2);
        ipCode3 = findViewById(R.id.ipCode3);
        ipCode4 = findViewById(R.id.ipCode4);
        ipCode5 = findViewById(R.id.ipCode5);
        ipCode6 = findViewById(R.id.ipCode6);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button btnSubmitOTP = (Button) findViewById(R.id.btnSubmitOTP);
        if (receivedUserRequest != null) {
            setupOTPInput();
            btnSubmitOTP.setOnClickListener(v -> {
                if (ipCode1.getText().toString().trim().isEmpty() || ipCode2.getText().toString().trim().isEmpty() || ipCode3.getText().toString().trim().isEmpty() || ipCode4.getText().toString().trim().isEmpty()
                        || ipCode5.getText().toString().trim().isEmpty() || ipCode6.getText().toString().trim().isEmpty())
                    Toast.makeText(VerifyOtpActivity.this, "Please enter all the OTP", Toast.LENGTH_SHORT).show();
                String code = ipCode1.getText().toString() + ipCode2.getText().toString() + ipCode3.getText().toString() + ipCode4.getText().toString() + ipCode5.getText().toString() + ipCode6.getText().toString();
                if (otpSent != null) {
                    if(otpSent.equals(code)){
                        authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
                        Call<AuthResponse> call = authService.register(receivedUserRequest);
                        call.enqueue(new Callback<AuthResponse>() {
                            @Override
                            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        User user = response.body().getResult();
                                        Toast.makeText(VerifyOtpActivity.this, "Registered Success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(VerifyOtpActivity.this, SignInActivity.class));
                                        finish();
                                    } else {
                                        Log.e("API_ERROR", "Response unsuccessful");
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<AuthResponse> call, Throwable t) {
                                // Xử lý lỗi, ví dụ: thông báo lỗi cho người dùng
                                Log.e("API_ERROR", "Error register", t);
                            }
                        });
                        finish();
                    }
                }
            });

        }
    }

    private void setupOTPInput() {
        ipCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ipCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ipCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ipCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ipCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ipCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ipCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ipCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ipCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ipCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
