package com.example.shop_thoi_trang_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.OnCartItemChangeListener;
import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.CartItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItemList;
    private Context context;
    private OnCartItemChangeListener listener;

    public CartAdapter(Context context,List<CartItem> cartItemList, OnCartItemChangeListener listener) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.itemName.setText(cartItem.getName());
        holder.itemQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.itemQuantityWithPrice.setText(cartItem.getQuantity() + " x " + cartItem.getPrice());
        holder.itemTotalPrice.setText(String.valueOf(cartItem.getQuantity() * cartItem.getPrice()));
        Picasso.get().load(cartItem.getImage()).into(holder.itemImage);

        // increase quantity
        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getQuantity();
                ++quantity;
                cartItem.setQuantity(quantity);
                holder.itemQuantity.setText(String.valueOf(quantity));
                holder.itemQuantityWithPrice.setText(quantity + " x " + cartItem.getPrice());
                holder.itemTotalPrice.setText(String.valueOf(quantity * cartItem.getPrice()));
                notifyItemChanged(holder.getAdapterPosition());
                listener.onCartItemChanged();
            }
        });

        // decrease quantity
        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getQuantity();
                if (quantity > 1) {
                    --quantity;
                    cartItem.setQuantity(quantity);
                    holder.itemQuantity.setText(String.valueOf(quantity));
                    holder.itemQuantityWithPrice.setText(quantity + " x " + cartItem.getPrice());
                    holder.itemTotalPrice.setText(String.valueOf(quantity * cartItem.getPrice()));
                    notifyItemChanged(holder.getAdapterPosition());
                    listener.onCartItemChanged();
                } else {
                    confirmDelete(holder.getAdapterPosition());
                }
            }
        });
    }

    public void confirmDelete(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cartItemList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartItemList.size());
                        listener.onCartItemChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        notifyItemChanged(position);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void removeItem(int position) {
        cartItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartItemList.size());
        listener.onCartItemChanged();
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity, itemQuantityWithPrice, itemTotalPrice;
        ImageView itemImage;
        ImageButton decreaseQuantity, increaseQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_item_name);
            itemQuantity = itemView.findViewById(R.id.tv_quantity);
            itemImage = itemView.findViewById(R.id.tv_item_image);
            itemQuantityWithPrice = itemView.findViewById(R.id.tv_item_quantity_price);
            itemTotalPrice = itemView.findViewById(R.id.tv_item_total_price);

            decreaseQuantity = itemView.findViewById(R.id.btn_decrease_quantity);
            increaseQuantity = itemView.findViewById(R.id.btn_increase_quantity);
        }

    }
}