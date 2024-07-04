package com.example.shop_thoi_trang_mobile.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.networking.ProductService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.github.clans.fab.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_detailproduct_admin extends AppCompatActivity {

    ImageView detailImage;
    TextView  detailDescription, detailProductName, detailBrand, detailPrice, detailQuantity;
    FloatingActionButton deleteButton, editButton;
    Dialog dialog;
    Button noDelete, yesDelete;
    ProductService productService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailproduct_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        detailImage = findViewById(R.id.detailImage);
        detailDescription = findViewById(R.id.detailDescription);
        detailProductName = findViewById(R.id.detailProductName);
        detailBrand = findViewById(R.id.detailBrand);
        detailPrice = findViewById(R.id.detailPrice);
        deleteButton = findViewById(R.id.deleteButton);
        detailQuantity = findViewById(R.id.detailQuantity);
        editButton = findViewById(R.id.editButton);
        productService = RetrofitClient.getRetrofitInstance().create(ProductService.class);
        Bundle bundle = getIntent().getExtras();


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_product_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        noDelete = dialog.findViewById(R.id.noDelete);
        yesDelete = dialog.findViewById(R.id.yesDelete);
        noDelete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

        yesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = Integer.parseInt(bundle.getString("Id"));
                DeleteProduct(productId);
            }

            private void DeleteProduct(int productId) {
                Call<ProductResponse> call = productService.deleteProduct(productId);
                call.enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        dialog.dismiss();
                        Intent intent = new Intent(activity_detailproduct_admin.this, activity_product_admin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ProductResponse> call, Throwable throwable) {

                    }
                });
            }
        });

        if(bundle != null){
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
            detailProductName.setText(bundle.getString("Name") + " - " + bundle.getString("Code"));
            detailBrand.setText("by "+bundle.getString("Brand"));
            detailPrice.setText(bundle.getString("Price") + " VND");
            detailDescription.setText(bundle.getString("Description"));
            detailQuantity.setText(bundle.getString("Quantity"));
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(activity_detailproduct_admin.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity_detailproduct_admin.this, activity_product_admin.class));
                finish();*/
                dialog.show();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_detailproduct_admin.this, activity_updateproduct_admin.class);
                intent.putExtra("Id", bundle.getString("Id"));
                intent.putExtra("Image", bundle.getString("Image"));
                intent.putExtra("Price", bundle.getString("Price"));
                intent.putExtra("Brand", bundle.getString("Brand"));
                intent.putExtra("Code", bundle.getString("Code"));
                intent.putExtra("Name", bundle.getString("Name"));
                intent.putExtra("Description", bundle.getString("Description"));
                intent.putExtra("Quantity", bundle.getString("Quantity"));
                intent.putExtra("Category", bundle.getString("Category"));
                startActivity(intent);

            };
        });
    }
}