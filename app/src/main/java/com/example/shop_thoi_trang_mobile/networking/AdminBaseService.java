package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.Notice;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.ResponseBase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AdminBaseService {
    String NOTIFICATION = "Notice";
    String ORDER = "Order";

    @GET(NOTIFICATION)
    Call<ResponseBase<Notice>> getNotifications();

    @GET(ORDER)
    Call<ResponseBase<Order>> getOrders();

    @PUT(ORDER)
    Call<ResponseBase<Order>> updateOrderStatus(
            @Query("id") int orderId,
            @Query("status") String orderStatus
    );
}
