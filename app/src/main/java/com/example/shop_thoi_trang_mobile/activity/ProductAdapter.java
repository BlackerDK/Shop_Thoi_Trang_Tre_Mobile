package com.example.shop_thoi_trang_mobile.activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Product;

import java.util.List;

//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
//
//    private List<Product> productList;
//    private OnItemClickListener listener;
//
//    public interface OnItemClickListener {
//        void onItemClick(Product product);
//    }
//    public ProductAdapter(List<Product> productList,OnItemClickListener listener) {
//        this.productList = productList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
//        return new ProductViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
//        Product product = productList.get(position);
//        holder.productName.setText(product.getProductName());
//        holder.productPrice.setText(product.getProductPrice().toString());
//        // Assuming productImage is the resource name of the image, use context.getResources().getIdentifier to get the drawable resource ID
//        int imageResId = holder.itemView.getContext().getResources().getIdentifier(product.getProductImage(), "drawable", holder.itemView.getContext().getPackageName());
//        holder.productImage.setImageResource(imageResId);
//    }
//
//    @Override
//    public int getItemCount() {
//        return productList.size();
//    }
//
//    static class ProductViewHolder extends RecyclerView.ViewHolder {
//        ImageView productImage;
//        TextView productName;
//        TextView productPrice;
//
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            productImage = itemView.findViewById(R.id.product_image);
//            productName = itemView.findViewById(R.id.product_name);
//            productPrice = itemView.findViewById(R.id.product_price);
//        }
//    }
//}
// ProductAdapter.java

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public ProductAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        // Your view references

        public ProductViewHolder(View itemView) {
            super(itemView);
            // Initialize your views
        }

        public void bind(final Product product, final OnItemClickListener listener) {
            // Bind data to the views

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(product);
                }
            });
        }
    }
}
