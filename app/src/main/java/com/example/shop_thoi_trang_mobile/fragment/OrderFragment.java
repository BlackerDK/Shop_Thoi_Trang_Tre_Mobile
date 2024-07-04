package com.example.shop_thoi_trang_mobile.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.OrderAdminAdapter;
import com.example.shop_thoi_trang_mobile.adapter.SpacingItemDecoration;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.OrderItem;
import com.example.shop_thoi_trang_mobile.model.ResponseBase;
import com.example.shop_thoi_trang_mobile.networking.AdminBaseService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private final String keyOrder;
    private final List<Order> orderItems;
    OrderAdminAdapter adapter;
    RecyclerView recyclerView;
    AdminBaseService adminBaseService;
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
        adminBaseService = RetrofitClient.getRetrofitInstance().create(AdminBaseService.class);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(5);
        recyclerView.addItemDecoration(spacingItemDecoration);
        recyclerView.setHasFixedSize(true);
        adapter = new OrderAdminAdapter(view.getContext(), (ArrayList<Order>) orderItems);
        recyclerView.setAdapter(adapter);
        LoadOrders();
        return view;
    }
    private void LoadOrders() {
        Call<ResponseBase<Order>> call = adminBaseService.getOrders();
        call.enqueue(new Callback<ResponseBase<Order>>() {
            @Override
            public void onResponse(Call<ResponseBase<Order>> call, Response<ResponseBase<Order>> response) {
                if(response.isSuccessful() && response.body() != null){
                    adapter.setOrders((ArrayList<Order>) response.body().getResult());
                    Log.e("res", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBase<Order>> call, Throwable throwable) {
                Log.e("err", throwable.getMessage());
            }
        });
    }
}