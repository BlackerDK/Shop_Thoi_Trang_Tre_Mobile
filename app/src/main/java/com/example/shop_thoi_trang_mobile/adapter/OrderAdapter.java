package com.example.shop_thoi_trang_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
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

        holder.tvOrderId.setText("Order#" + order.getOrderId());
        holder.tvOrderDate.setText("Date: " + DateToString(order.getOrderDate()));
        holder.tvOrderQuantity.setText("Quantity: " + order.getQuantity());
        holder.tvOrderTotal.setText("Total Amount: " + order.getOrderTotalAmount());

        holder.tvFullName.setText("Full Name: " + order.getFullName());
        holder.tvPhoneNumber.setText("Phone Number: " + order.getPhoneNumber());
        holder.tvShippingAddress.setText("Shipping Address: " + order.getShippingAddress());

        holder.itemContainer.removeAllViews();

        for (OrderItem item : order.getOrderItems()) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.order_item_details, holder.itemContainer, false);

            TextView tvItemName = itemView.findViewById(R.id.tvItemName);
            TextView tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            TextView tvItemTotal = itemView.findViewById(R.id.tvItemTotal);
            TextView tvItemQuantity = itemView.findViewById(R.id.tvItemQuantity);

            tvItemName.setText(item.getName());
            tvItemPrice.setText("Price: " + item.getPrice());
            tvItemTotal.setText("Total: " + item.getQuantity()*item.getPrice().intValue());
            tvItemQuantity.setText("Quantity: " + item.getQuantity());

            holder.itemContainer.addView(itemView);
        }

        holder.orderHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.orderDetails.getVisibility() == View.GONE) {
                    holder.orderDetails.setVisibility(View.VISIBLE);
                    holder.btnToggle.setImageResource(R.drawable.collapse);
                } else {
                    holder.orderDetails.setVisibility(View.GONE);
                    holder.btnToggle.setImageResource(R.drawable.expand);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderId, tvOrderDate, tvOrderQuantity, tvOrderTotal;
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
}
