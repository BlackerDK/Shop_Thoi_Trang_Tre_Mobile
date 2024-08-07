package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.OrderByUserResponse;
import com.example.shop_thoi_trang_mobile.model.OrderResponse;
import com.example.shop_thoi_trang_mobile.model.OrderRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Body;

public interface OrderService {
    final String ORDER = "Order";
    final String ORDER_BY_ID = "Order/{id}";
    final String ORDER_BY_USER_ID = "/api/Order/GetByUserID";

    @POST(ORDER)
    Call<OrderResponse> createOrder(@Body OrderRequest orderRequest);
    @GET(ORDER)
    Call<OrderResponse> getAllOrder();
    @GET(ORDER_BY_ID)
    Call<OrderByUserResponse> getOrderById(@Path("id") int id);
    @GET(ORDER_BY_USER_ID)
    Call<OrderResponse> getOrdersByUserId(@Query("userid") int userId);
}
