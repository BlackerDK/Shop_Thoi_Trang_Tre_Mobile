package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.model.SingleProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    final String PRODUCT = "Product";
    final String PRODUCT_BY_ID = "Product/{id}";
    @GET(PRODUCT)
    Call<ProductResponse> getAllProduct();
    @GET(PRODUCT_BY_ID)
    Call<ProductResponse> getProductById(@Path("id") int id);

    @GET(PRODUCT_BY_ID)
    Call<SingleProductResponse> getSingleProductById(@Path("id") int id);
}
