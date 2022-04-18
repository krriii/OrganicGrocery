package com.example.organicgrocery.api;

import com.example.organicgrocery.api.response.AddressResponse;
import com.example.organicgrocery.api.response.AllProductResponse;
import com.example.organicgrocery.api.response.CategoryResponse;
import com.example.organicgrocery.api.response.DashResponse;
import com.example.organicgrocery.api.response.LoginResponse;
import com.example.organicgrocery.api.response.RegisterResponse;
import com.example.organicgrocery.api.response.SingleProductResponse;
import com.example.organicgrocery.api.response.SliderResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("/ecommerce/api/v1/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/ecommerce/api/v1/register")
    Call<RegisterResponse> register(@Field("name") String names, @Field("email") String email, @Field("password") String password);

//    @GET("/api/v1/get-all-products")
//    Call<AllProductResponse> getAllProducts();

    @GET("/ecommerce/api/v1/get-categories")
    Call<CategoryResponse> getCategories();

    @GET("/ecommerce/api/v1/get-all-products")
    Call<AllProductResponse> getAllProducts();

    @GET("/ecommerce/api/v1/slider")
    Call<SliderResponse> getSliders();

    @GET("/ecommerce/api/v1/get-products-by-category")
    Call<AllProductResponse> getProductsByCategory(@Query("c_id") int catid);

    @FormUrlEncoded
    @POST("/ecommerce/api/v1/order")
    Call<RegisterResponse> order(@Header("Apikey") String apikey,
                                 @Field("address_id") int address_id,
                                 @Field("p_type") int p_type,
                                 @Field("payment_refrence") String paymentRefrence);

    @FormUrlEncoded
    @POST("/ecommerce/api/v1/cart")
    Call<AllProductResponse> addToCart(@Header("Apikey") String apikey, @Field("p_id") int p, @Field("quantity") int q);


    @GET("/ecommerce/api/v1/cart")
    Call<AllProductResponse> getMyCart(@Header("Apikey") String apikey);

    @DELETE("/ecommerce/api/v1/cart")
    Call<RegisterResponse> deleteFromCart(@Header("Apikey") String apikey, @Query("c_id") int cartID);

    @GET("/ecommerce/api/v1/get-all-products")
    Call<SingleProductResponse> getProductById(@Query("id") int c_id);

    @FormUrlEncoded
    @POST("/api/v1/address")
    Call<AddressResponse> addAddress(
            @Header("Apikey") String apikey,
            @Field("province") String province,
            @Field("district") String district,
            @Field("city") String city,
            @Field("street") String street);

    @GET("/api/v1/address")
    Call<AddressResponse> getMyAddresses(@Header("Apikey") String apikey);

    @GET("/api/v1/dash")
    Call<DashResponse> getDash(@Header("api_key") String apikey);

    @DELETE("/api/v1/category")
    Call<RegisterResponse> deleteCategory(@Header("api_key") String apikey, @Query("c_id") int id);

    @Multipart
    @POST("/api/v1/upload-product")
    Call<RegisterResponse> uploadProduct(
            @Header("api_key") String apikey,
            @Part MultipartBody.Part[] files,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("quantity") RequestBody quantity,
            @Part("discount_price") RequestBody discount_price,
            @Part("categories") RequestBody categories
    );
    @Multipart
    @POST("/api/v1/upload-category")
    Call<RegisterResponse> uploadCategory(
            @Header("Apikey") String apikey,
            @Part MultipartBody.Part file,
            @Part("name") RequestBody name);
}


















