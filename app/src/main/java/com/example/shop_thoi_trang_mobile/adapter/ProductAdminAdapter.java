package com.example.shop_thoi_trang_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.activity.activity_detailproduct_admin;
import com.example.shop_thoi_trang_mobile.model.Product;

import java.util.List;

public class ProductAdminAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductAdminAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @androidx.annotation.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);
        Glide.with(context).load(product.getProductImage()).into(holder.cardImage);
        holder.cardTitle.setText(product.getProductName()+ " - " + product.getProductCode());
        holder.cardDescription.setText(product.getProductBrand());
        holder.cardPrice.setText(product.getProductPrice().toString().replace(".00", "") + " VND");
        holder.cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_detailproduct_admin.class);
                intent.putExtra("Id", product.getProductId()+"");
                intent.putExtra("Image", product.getProductImage());
                intent.putExtra("Price", product.getProductPrice().toString().replace(".00", ""));
                intent.putExtra("Brand", product.getProductBrand());
                intent.putExtra("Code", product.getProductCode());
                intent.putExtra("Name", product.getProductName());
                intent.putExtra("Description", product.getProductDescription());
                intent.putExtra("Quantity", product.getProductQuantity()+"");
                intent.putExtra("Category", product.getProductCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void searchData(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }
    public void loadData(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView cardImage;
    TextView cardTitle;
    TextView cardDescription;
    TextView cardPrice;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        cardImage = itemView.findViewById(R.id.cardImage);
        cardTitle = itemView.findViewById(R.id.cardTitle);
        cardDescription = itemView.findViewById(R.id.cardDescription);
        cardPrice = itemView.findViewById(R.id.cardPrice);
    }
}

