package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.github.clans.fab.FloatingActionButton;

public class activity_detailproduct_admin extends AppCompatActivity {

    ImageView detailImage;
    TextView  detailDescription;
    FloatingActionButton deleteButton, editButton;

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
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
            detailDescription.setText(bundle.getString("Description"));
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_detailproduct_admin.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity_detailproduct_admin.this, activity_product_admin.class));
                finish();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_detailproduct_admin.this, activity_updateproduct_admin.class)
                        .putExtra("Image", bundle.getString("Image"))
                        .putExtra("Description", bundle.getString("Description"))
                        .putExtra("Name", bundle.getString("Name"));;
                startActivity(intent);

            };
        });
    }
}