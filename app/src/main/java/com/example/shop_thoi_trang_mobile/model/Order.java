package com.example.shop_thoi_trang_mobile.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private String orderDate;
    private int userId;
    private BigDecimal orderTotalAmount;
    private String orderStatus;
    private String paymentType;

    public Order(int orderId, String orderDate, int userId, BigDecimal orderTotalAmount, String orderStatus, String paymentType) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.userId = userId;
        this.orderTotalAmount = orderTotalAmount;
        this.orderStatus = orderStatus;
        this.paymentType = paymentType;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
