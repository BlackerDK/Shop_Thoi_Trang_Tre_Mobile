package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.Product;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    String PRODUCT = "Product";
    @GET(PRODUCT)
    Call<ProductResponse> getAllProduct();
    @GET(PRODUCT+"/{id}")
    Call<ProductResponse> getProductById(@Path("id") int id);
    @POST(PRODUCT)
    Call<ProductResponse> addProduct(@Body Product product);
    @PUT(PRODUCT)
    Call<ProductResponse> updateProduct(@Body Product product);
    @DELETE(PRODUCT)
    Call<ProductResponse> deleteProduct(@Query("id") int id);
}
