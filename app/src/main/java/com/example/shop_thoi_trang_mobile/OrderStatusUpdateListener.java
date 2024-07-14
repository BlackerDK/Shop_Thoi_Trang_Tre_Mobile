package com.example.shop_thoi_trang_mobile;

public interface OrderStatusUpdateListener {
    void onUpdateOrderStatus(int orderId, String orderStatus);
}
