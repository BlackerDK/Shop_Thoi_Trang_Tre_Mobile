package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.model.SingleProductResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    final String PRODUCT = "Product";
    final String PRODUCT_BY_ID = "Product/{id}";
    @GET(PRODUCT)
    Call<ProductResponse> getAllProduct();
    @GET(PRODUCT_BY_ID)
    Call<ProductResponse> getProductById(@Path("id") int id);
    @GET(PRODUCT_BY_ID)
    Call<SingleProductResponse> getSingleProductById(@Path("id") int id);
    @POST(PRODUCT)
    Call<ProductResponse> addProduct(@Body Product product);
    @PUT(PRODUCT)
    Call<ProductResponse> updateProduct(@Body Product product);
    @DELETE(PRODUCT)
    Call<ProductResponse> deleteProduct(@Query("id") int id);
}
