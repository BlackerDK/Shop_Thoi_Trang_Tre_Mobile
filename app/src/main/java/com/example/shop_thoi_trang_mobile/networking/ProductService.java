package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    String PRODUCT = "Product";
    @GET(PRODUCT)
    Call<ProductResponse> getAllProduct();
    @GET(PRODUCT+"/{id}")
    Call<ProductResponse> getProductById(@Path("id") int id);
}
