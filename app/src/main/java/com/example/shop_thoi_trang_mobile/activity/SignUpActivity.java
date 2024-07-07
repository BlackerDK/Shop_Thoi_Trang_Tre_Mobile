package com.example.shop_thoi_trang_mobile.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.AppData;
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
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            btnSignUp.setOnClickListener(v -> {
                try {
                    signUp();
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        }

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
    private void signUp() throws MessagingException {
        if(!checkInput()){
            return;
        }
        UserRequest userRequest = new UserRequest(
                etUsername.getText().toString(),
                etEmail.getText().toString(),
                etPhone.getText().toString(),
                etAddress.getText().toString(),
                etPassword.getText().toString()
        );
        String otp = generateRandomString(6);
        sendMail(otp,etEmail.getText().toString());
        Intent intent = new Intent(getApplicationContext(), VerifyOtpActivity.class);
        intent.putExtra("userRequest", userRequest);
        intent.putExtra("otp", otp);
        startActivity(intent);
        finish();
    }

    public static String generateRandomString(int length) {
        String chars = "0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            randomString.append(chars.charAt(randomIndex));
        }
        return randomString.toString();
    }
    private void signInForm(){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public static void sendMail(String otp, String receiverEmail) throws MessagingException {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AppData.Sender_Email, AppData.Sender_Password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(AppData.Sender_Email));
        message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(receiverEmail)});
        message.setSubject("OTP Verification");
        message.setText("Your OTP is " + otp);

        Transport.send(message);
    }
}
