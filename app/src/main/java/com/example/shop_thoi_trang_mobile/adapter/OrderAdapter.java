package com.example.shop_thoi_trang_mobile.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderByUserResponse;
import com.example.shop_thoi_trang_mobile.model.OrderByUserResponse.OrderResult;
import com.example.shop_thoi_trang_mobile.model.OrderItem;
import com.example.shop_thoi_trang_mobile.model.OrderResponse;
import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.model.SingleProductResponse;
import com.example.shop_thoi_trang_mobile.networking.OrderService;
import com.example.shop_thoi_trang_mobile.networking.ProductService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orders;
    private ProductService productService;
    private OrderService orderService;
    private Product product;
    private SharedPreferences sharedPreferences;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.orderService = RetrofitClient.getRetrofitInstance().create(OrderService.class);
        this.productService = RetrofitClient.getRetrofitInstance().create(ProductService.class);
        this.sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        Order order = orders.get(position);

        holder.tvOrderId.setText("Order#" + position + " - ID: " + order.getOrderId());
        holder.tvOrderDate.setText("Date: " + DateToString(order.getOrderDate()));
        holder.tvOrderQuantity.setText("Quantity: " + 1);
        holder.tvOrderTotal.setText("Total Amount: " + order.getOrderTotalAmount() + "VND");
        holder.tvOrderStatus.setText("Status: " + order.getOrderStatus());
        holder.tvOrderStatus.setTextColor(getStatusColor(order.getOrderStatus()));

        // todo
        // handle = cách lấy user từ session maybe
        holder.tvFullName.setText("Full Name: " + sharedPreferences.getString("userName", ""));
        holder.tvPhoneNumber.setText("Phone Number: " + sharedPreferences.getString("userPhone", ""));
        holder.tvShippingAddress.setText("Shipping Address: " + sharedPreferences.getString("userAddress", ""));

        holder.itemContainer.removeAllViews();
        holder.orderHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.orderDetails.getVisibility() == View.GONE) {
                    fetchOrderItems(order.getOrderId(), holder.itemContainer);
                    holder.orderDetails.setVisibility(View.VISIBLE);
                    holder.orderDetails.startAnimation(AnimationUtils.loadAnimation(context, R.anim.expand_collapse));
                    holder.btnToggle.setImageResource(R.drawable.collapse);
                } else {
                    holder.orderDetails.setVisibility(View.GONE);
                    holder.orderDetails.startAnimation(AnimationUtils.loadAnimation(context, R.anim.expand_collapse));
                    holder.btnToggle.setImageResource(R.drawable.expand);
                    holder.itemContainer.removeAllViews();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderId, tvOrderDate, tvOrderQuantity, tvOrderTotal, tvOrderStatus;
        TextView tvFullName, tvPhoneNumber, tvShippingAddress;
        ImageView btnToggle;
        LinearLayout itemContainer;
        LinearLayout orderDetails;
        LinearLayout orderHeader;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            orderHeader = itemView.findViewById(R.id.orderHeader);
            orderDetails = itemView.findViewById(R.id.orderDetails);
            btnToggle = itemView.findViewById(R.id.btnToggle);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);

            itemContainer = itemView.findViewById(R.id.itemContainer);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvShippingAddress = itemView.findViewById(R.id.tvShippingAddress);
        }
    }

    private String DateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    private int getStatusColor(String status) {
        switch (status) {
            case "Delivered":
                return context.getResources().getColor(R.color.green);
            case "In Transit":
                return context.getResources().getColor(R.color.orange);
            case "Canceled":
                return context.getResources().getColor(R.color.red);
            default:
                return context.getResources().getColor(R.color.black);
        }
    }

    public void addOrders(List<Order> newOrders) {
        orders.addAll(newOrders);
        notifyItemRangeInserted(orders.size() - newOrders.size(), newOrders.size());
    }

    private void fetchProductDetails(int productId, ProductDetailsCallback callback) {
        Call<SingleProductResponse> call = productService.getSingleProductById(productId);
        call.enqueue(new Callback<SingleProductResponse>() {
            @Override
            public void onResponse(Call<SingleProductResponse> call, Response<SingleProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body().getResult();
                    if (product != null) {
                        callback.onSuccess(product);
                    } else {
                        callback.onFailure(new Throwable("Product is null"));
                    }
                } else {
                    callback.onFailure(new Throwable("Failed to get product details"));
                }
            }

            @Override
            public void onFailure(Call<SingleProductResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    private void fetchOrderItems(int orderId, LinearLayout itemContainer) {
        Call<OrderByUserResponse> call = orderService.getOrderById(orderId);
        call.enqueue(new Callback<OrderByUserResponse>() {
            @Override
            public void onResponse(Call<OrderByUserResponse> call, Response<OrderByUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderResult orderResult = response.body().getResult();
                    for (OrderItem item : orderResult.getItems()) {
                        fetchProductDetails(item.getProductId(), new ProductDetailsCallback() {
                            @Override
                            public void onSuccess(Product product) {
                                addOrderItemView(item, product, itemContainer);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.e("API_ERROR", "Error fetching product details", t);
                            }
                        });
                    }
                } else {
                    Log.e("API_ERROR", "Failed to get order items");
                }
            }

            @Override
            public void onFailure(Call<OrderByUserResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error fetching order items", t);
            }
        });
    }

    private void addOrderItemView(OrderItem item, Product product, LinearLayout itemContainer) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_item_details, itemContainer, false);
        TextView tvItemName = itemView.findViewById(R.id.tvItemName);
        TextView tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
        TextView tvItemTotal = itemView.findViewById(R.id.tvItemTotal);
        TextView tvItemQuantity = itemView.findViewById(R.id.tvItemQuantity);
        ImageView ivItem = itemView.findViewById(R.id.ivItem);

        Picasso
                .get()
                .load(product.getProductImage())
                .resize(50, 50)
                .into(ivItem);
        tvItemName.setText(product.getProductName());
        tvItemPrice.setText("Price: " + item.getPrice() + "VND");
        tvItemQuantity.setText("Quantity: " + item.getQuantity());
        tvItemTotal.setText("Total: " + item.getQuantity() * item.getPrice().intValue() + "VND");

        itemContainer.addView(itemView);
    }


    interface ProductDetailsCallback {
        void onSuccess(Product product);

        void onFailure(Throwable t);
    }


}
