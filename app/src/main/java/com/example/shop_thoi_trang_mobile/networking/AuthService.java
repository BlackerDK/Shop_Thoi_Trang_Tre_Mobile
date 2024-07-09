package com.example.shop_thoi_trang_mobile.networking;

import com.example.shop_thoi_trang_mobile.model.AuthListResponse;
import com.example.shop_thoi_trang_mobile.model.AuthResponse;
import com.example.shop_thoi_trang_mobile.model.ProductResponse;
import com.example.shop_thoi_trang_mobile.model.UserRequest;
import com.example.shop_thoi_trang_mobile.model.UserUpdateRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AuthService {
    @GET("Auth")
    Call<AuthListResponse> getUsers();
    @POST("Auth")
    Call<AuthResponse> login(@Query("email") String email, @Query("pass") String pass);
    @PUT("Auth")
    Call<AuthResponse> register(@Body UserRequest userRequest);
    @PUT("Auth/UpdateUser")
    Call<AuthResponse> updateUser(@Body UserUpdateRequest userRequest);

}
