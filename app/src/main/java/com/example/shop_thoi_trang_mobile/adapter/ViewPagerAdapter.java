package com.example.shop_thoi_trang_mobile.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shop_thoi_trang_mobile.fragment.OrderFragment;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderItem;
import com.example.shop_thoi_trang_mobile.networking.AdminBaseService;

import java.util.List;
import java.util.stream.Collectors;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<Order> orderItems;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Order> OrderItems) {
        super(fragmentActivity);
        orderItems = OrderItems;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new OrderFragment("Tab1", orderItems.stream().filter(order -> order.getOrderStatus().equals("0")).collect(Collectors.toList()));
            case 1:
                return new OrderFragment("Tab2", orderItems);
            default:
                return new OrderFragment("Tab1", orderItems);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public void setOrderItems(List<Order> orderItems) {
        this.orderItems = orderItems;
        notifyDataSetChanged();
    }
}
