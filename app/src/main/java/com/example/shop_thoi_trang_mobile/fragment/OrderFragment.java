package com.example.shop_thoi_trang_mobile.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.OrderAdminAdapter;
import com.example.shop_thoi_trang_mobile.adapter.SpacingItemDecoration;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private final String keyOrder;
    private final List<Order> orderItems;
    OrderAdminAdapter adapter;
    RecyclerView recyclerView;
    public OrderFragment(String KeyOrder, List<Order> OrderItems) {
        keyOrder = KeyOrder;
        orderItems = OrderItems;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.orderRecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(5);
        recyclerView.addItemDecoration(spacingItemDecoration);
        recyclerView.setHasFixedSize(true);
        adapter = new OrderAdminAdapter(view.getContext(), (ArrayList<Order>) orderItems);
        recyclerView.setAdapter(adapter);
        return view;
    }
}