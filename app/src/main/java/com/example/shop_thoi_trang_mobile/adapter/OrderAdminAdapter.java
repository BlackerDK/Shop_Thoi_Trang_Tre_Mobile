package com.example.shop_thoi_trang_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.Product;

import java.util.ArrayList;

public class OrderAdminAdapter  extends RecyclerView.Adapter<OrderViewHolder>{

    private Context context;
    private ArrayList<Order> orders;

    public OrderAdminAdapter(Context Context, ArrayList<Order> Orders) {
        this.context = Context;
        this.orders = Orders;
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
        holder.orderTitle.setText(String.valueOf((order.getOrderId())));
        holder.orderType.setText(order.getOrderDate().toString());
        holder.orderPrice.setText(order.getOrderTotalAmount().toString()+ "vnd");
        holder.orderTongTien.setText(order.getOrderTotalAmount().toString()+"vnd");
        holder.orderStatus.setText(order.getOrderStatus());
        //Glide.with(context).load(product.getProductImage()).into(holder.cardImage);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
class OrderViewHolder extends RecyclerView.ViewHolder{
    ImageView orderImage;
    TextView orderTitle, orderType, orderPrice, orderBlank, orderTongTien, orderStatus;
    public OrderViewHolder(View itemView) {
        super(itemView);
        orderImage = itemView.findViewById(R.id.orderImage);
        orderTitle = itemView.findViewById(R.id.orderTitle);
        orderType = itemView.findViewById(R.id.orderType);
        orderPrice = itemView.findViewById(R.id.orderPrice);
        orderBlank = itemView.findViewById(R.id.orderBlank);
        orderTongTien = itemView.findViewById(R.id.orderTongTien);
        orderStatus = itemView.findViewById(R.id.orderStatus);
    }
}
