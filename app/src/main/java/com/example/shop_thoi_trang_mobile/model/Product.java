package com.example.shop_thoi_trang_mobile.model;

import java.io.Serializable;
import java.math.BigDecimal;

import java.math.BigDecimal;

public class Product implements Serializable {
    private int productId;
    private String productName;
    private String productCode;
    private String productCategory;
    private String productBrand;
    private BigDecimal productPrice;
    private int productQuantity;
    private String productDescription;
    private String productImage;
    private String productStatus;

    public Product(int productId, String productName, String productCode, String productCategory, String productBrand,
                   BigDecimal productPrice, int productQuantity, String productDescription, String productImage, String productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.productCode = productCode;
        this.productCategory = productCategory;
        this.productBrand = productBrand;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productStatus = productStatus;
    }

    // Getters and setters for each field

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
}

