package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.Notice;
import com.example.shop_thoi_trang_mobile.model.Order;
import com.example.shop_thoi_trang_mobile.model.ResponseBase;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminBaseService {
    String NOTIFICATION = "Notice";
    String ORDER = "Order";

    @GET(NOTIFICATION)
    Call<ResponseBase<Notice>> getNotifications();
    @GET(ORDER)
    Call<ResponseBase<Order>> getOrders();
}
