package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.networking.ProductService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.gson.Gson;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_updateproduct_admin extends AppCompatActivity {

    ImageView updateImage;
    Button updateButton;
    EditText updateProductName, updateProductCode, updateProductPrice, updateProductDescription, updateProductCategory, updateBrand, updateProductQuantity;
    Uri imageUri;
    private ProductService productService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_updateproduct_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        updateButton = findViewById(R.id.updateButton);
        updateImage = findViewById(R.id.updateImage);
        updateProductName = findViewById(R.id.updateProductName);
        updateProductCode = findViewById(R.id.updateProductCode);
        updateProductPrice = findViewById(R.id.updateProductPrice);
        updateProductDescription = findViewById(R.id.updateProductDescription);
        updateProductCategory = findViewById(R.id.updateProductCategory);
        updateBrand = findViewById(R.id.updateBrand);
        updateProductQuantity = findViewById(R.id.updateProductQuantity);
        productService = RetrofitClient.getRetrofitInstance().create(ProductService.class);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        imageUri = result.getData().getData();
                        updateImage.setImageURI(imageUri);
                    } else {
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Glide.with(activity_updateproduct_admin.this).load(bundle.getString("Image")).into(updateImage);
            updateProductName.setText(bundle.getString("Name"));
            updateProductCode.setText(bundle.getString("Code"));
            updateProductPrice.setText(bundle.getString("Price"));
            updateProductDescription.setText(bundle.getString("Description"));
            updateProductCategory.setText(bundle.getString("Category"));
            updateBrand.setText(bundle.getString("Brand"));
            updateProductQuantity.setText(bundle.getString("Quantity"));
        };
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    return;
                }
                Product updateProduct = new Product(Integer.parseInt(bundle.getString("Id")),
                        updateProductName.getText().toString(),
                        updateProductCode.getText().toString(),
                        updateProductCategory.getText().toString(),
                        updateBrand.getText().toString(),
                        BigDecimal.valueOf(Double.parseDouble(updateProductPrice.getText().toString())),
                        Integer.parseInt(updateProductQuantity.getText().toString()),
                        updateProductDescription.getText().toString(),
                        bundle.getString("Image")
                        );
                UpdateProduct(updateProduct);
            }
        });

    }
    private void UpdateProduct(Product product) {
        Call<ProductResponse> call = productService.updateProduct(product);
        Gson gson = new Gson();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Toast.makeText(activity_updateproduct_admin.this, "Update product successfully", Toast.LENGTH_SHORT).show();
                Log.e("res", response.body().getMessage().toString());
                Intent intent = new Intent(activity_updateproduct_admin.this, activity_product_admin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                Toast.makeText(activity_updateproduct_admin.this, "Update product failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean checkInput() {
        if (updateProductName.getText().toString().isEmpty()) {
            updateProductName.setError("Please enter product name");
            return false;
        }
        if (updateProductCode.getText().toString().isEmpty()) {
            updateProductCode.setError("Please enter product code");
            return false;
        }
        if (updateProductCategory.getText().toString().isEmpty()) {
            updateProductCategory.setError("Please enter product category");
            return false;
        }
        if (updateBrand.getText().toString().isEmpty()) {
            updateBrand.setError("Please enter product brand");
            return false;
        }
        if (updateProductPrice.getText().toString().isEmpty()) {
            updateProductPrice.setError("Please enter product price");
            return false;
        }
        if (updateProductQuantity.getText().toString().isEmpty()) {
            updateProductQuantity.setError("Please enter product quantity");
            return false;
        }
        if (updateProductDescription.getText().toString().isEmpty()) {
            updateProductDescription.setError("Please enter product description");
            return false;
        }
        return true;
    };
}
