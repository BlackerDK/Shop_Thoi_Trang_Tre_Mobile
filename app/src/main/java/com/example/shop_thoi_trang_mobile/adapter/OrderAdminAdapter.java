package com.example.shop_thoi_trang_mobile.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.OrderStatusUpdateListener;
import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Order;

import java.util.ArrayList;

public class OrderAdminAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private Context context;
    private ArrayList<Order> orders;
    private final OrderStatusUpdateListener listener;


    public OrderAdminAdapter(Context Context, ArrayList<Order> Orders, OrderStatusUpdateListener listener) {
        this.context = Context;
        this.orders = Orders;
        this.listener = listener;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_admin, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        System.out.println(order.getPaymentType());
        holder.orderTitle.setText(String.valueOf((order.getOrderId())));
        holder.orderType.setText(order.getOrderDate().toString());
        holder.orderPrice.setText(String.format("%.2f", order.getOrderTotalAmount()) + "$");
        holder.orderTongTien.setText(String.format("%.2f", order.getOrderTotalAmount()) + "$");
        holder.orderStatus.setText(order.getOrderStatus());
//        holder.orderImage.setImageResource(order.getPaymentType().toLowerCase().equals("cod") ? R.drawable.cod : R.drawable.paypal);
        //Glide.with(context).load(product.getProductImage()).into(holder.cardImage);
        holder.acceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUpdateOrderStatus(order.getOrderId(), "PaySuccess");
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

}

class OrderViewHolder extends RecyclerView.ViewHolder {
    ImageView orderImage;
    TextView orderTitle, orderType, orderPrice, orderBlank, orderTongTien, orderStatus;
    ImageView orderIcon;
    Button acceptOrder;
    public OrderViewHolder(View itemView) {
        super(itemView);
        orderImage = itemView.findViewById(R.id.orderImage);
        orderTitle = itemView.findViewById(R.id.orderTitle);
        orderType = itemView.findViewById(R.id.orderType);
        orderPrice = itemView.findViewById(R.id.orderPrice);
        orderBlank = itemView.findViewById(R.id.orderBlank);
        orderTongTien = itemView.findViewById(R.id.orderTongTien);
        orderStatus = itemView.findViewById(R.id.orderStatus);
        orderIcon = itemView.findViewById(R.id.orderIcon);
        acceptOrder = itemView.findViewById(R.id.acceptOrder);
    }
}
