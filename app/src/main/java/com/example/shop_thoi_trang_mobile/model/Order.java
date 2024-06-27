package com.example.shop_thoi_trang_mobile.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private Date orderDate;
    private int userId;
    private BigDecimal orderTotalAmount;
    private String orderStatus;

    private int quantity;
    private String fullName;
    private String phoneNumber;
    private String shippingAddress;
    private List<OrderItem> orderItems;

    public Order(int orderId, Date orderDate, int userId, BigDecimal orderTotalAmount, String orderStatus, int quantity, String fullName, String phoneNumber, String shippingAddress, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.userId = userId;
        this.orderTotalAmount = orderTotalAmount;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
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

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
