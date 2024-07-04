package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.networking.ProductService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_addproduct_admin extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText productName, productDescription, productPrice, productQuantity, productCode, productCategory, productBrand;
    String imageUrl;
    Uri imageUri;
    private ProductService productService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addproduct_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        uploadImage = findViewById(R.id.uploadImage);
        saveButton = findViewById(R.id.saveButton);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productQuantity = findViewById(R.id.productQuantity);
        productCode = findViewById(R.id.productCode);
        productBrand = findViewById(R.id.productBrand);
        productCategory = findViewById(R.id.productCategory);
        productService = RetrofitClient.getRetrofitInstance().create(ProductService.class);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        imageUri = result.getData().getData();
                        uploadImage.setImageURI(imageUri);
                    }
                    else {
                        Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                    }
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    return;
                }
                Product product = new Product(
                        0,
                        productName.getText().toString(),
                        productCode.getText().toString(),
                        productCategory.getText().toString(),
                        productBrand.getText().toString(),
                        BigDecimal.valueOf(Double.parseDouble(productPrice.getText().toString())),
                        Integer.parseInt(productQuantity.getText().toString()),
                        productDescription.getText().toString(),
                        "https://i.pinimg.com/originals/cf/49/e8/cf49e8bf1626e2f4e02361ffdd5213d3.jpg"
                );
                UploadProduct(product);
            }

            private void UploadProduct(Product product) {
                Call<ProductResponse> call = productService.addProduct(product);
                call.enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        Toast.makeText(activity_addproduct_admin.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_addproduct_admin.this, activity_product_admin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                        Toast.makeText(activity_addproduct_admin.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_addproduct_admin.this, activity_product_admin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }
    private boolean checkInput() {
        if (productName.getText().toString().isEmpty()) {
            productName.setError("Please enter product name");
            return false;
        }
        if (productCode.getText().toString().isEmpty()) {
            productCode.setError("Please enter product code");
            return false;
        }
        if (productCategory.getText().toString().isEmpty()) {
            productCategory.setError("Please enter product category");
            return false;
        }
        if (productBrand.getText().toString().isEmpty()) {
            productBrand.setError("Please enter product brand");
            return false;
        }
        if (productPrice.getText().toString().isEmpty()) {
            productPrice.setError("Please enter product price");
            return false;
        }
        if (productQuantity.getText().toString().isEmpty()) {
            productQuantity.setError("Please enter product quantity");
            return false;
        }
        if (productDescription.getText().toString().isEmpty()) {
            productDescription.setError("Please enter product description");
            return false;
        }
        return true;
    };
}