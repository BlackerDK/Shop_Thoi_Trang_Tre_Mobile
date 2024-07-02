package com.example.shop_thoi_trang_mobile.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.UserRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity{
    private EditText etUsername;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etAddress;
    private EditText etPassword;
    private EditText etConfirmPassword;

    private TextView tvAlreadyAccount;
    private Button btnSignUp;
    private ProgressBar progressBar;



    private  final String REQUIRE="Require";
    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activitiy_sign_up);
        etUsername = (EditText) findViewById(R.id.txtUseName);
        etPhone =(EditText) findViewById(R.id.txtPhone);
        etEmail =(EditText) findViewById(R.id.txtEmail);
        etAddress =(EditText) findViewById(R.id.txtAddress);
        etPassword =(EditText) findViewById(R.id.txtPassword);
        etConfirmPassword =(EditText) findViewById(R.id.txtConfirmPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvAlreadyAccount = (TextView) findViewById(R.id.tvAccountAlready);
        tvAlreadyAccount.setOnClickListener(v -> signInForm());
        btnSignUp.setOnClickListener(v -> signUp());
    }
    private boolean checkInput(){
        if(TextUtils.isEmpty(etUsername.getText().toString())){
            etUsername.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString())){
            etPhone.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())){
            etEmail.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etConfirmPassword.getText().toString())){
            etConfirmPassword.setError(REQUIRE);
            return false;
        }
        if (!TextUtils.equals(etPassword.getText().toString()
                ,etConfirmPassword.getText().toString())){
            Toast.makeText(this,"Password are not match",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void signUp(){
        if(!checkInput()){
            return;
        }
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    etPhone.getText().toString(), 60, TimeUnit.SECONDS, SignUpActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            progressBar.setVisibility(View.GONE);
                            btnSignUp.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            progressBar.setVisibility(View.GONE);
                            btnSignUp.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            UserRequest userRequest = new UserRequest(
                                    etUsername.getText().toString(),
                                    etEmail.getText().toString(),
                                    etPhone.getText().toString(),
                                    etAddress.getText().toString(),
                                    etPassword.getText().toString()
                            );
                            progressBar.setVisibility(View.GONE);
                            btnSignUp.setVisibility(View.VISIBLE);
                            //Toast.makeText(this,"Sign up success",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), VerifyOtpActivity.class);
                            intent.putExtra("userRequest", userRequest);
                            intent.putExtra("verificationId", s);
                            startActivity(intent);
                        }
                    }
            );
            finish();

    }
    private void signInForm(){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
        finish();
    }
    /*@Override
    public void onClick(View v){
        if (v.getId() == R.id.btnSignUp) {
            signUp();
        } else if (v.getId() == R.id.tvAccountAlready) {
            signInForm();
        }
    }*/
}
