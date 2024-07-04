package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.OrderRequest;
import com.example.shop_thoi_trang_mobile.model.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {
    final String ORDER = "Order";
    @POST(ORDER)
    Call<OrderResponse> createOrder(@Body OrderRequest orderRequest);
}
