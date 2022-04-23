package com.example.cancer.client;

import com.example.cancer.client.Cancer1;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Cancer1Interface {
    @Multipart
    @POST("/lungkt/")
    Call<Cancer1> getResCancer1(@Part MultipartBody.Part image);

    @Multipart
    @POST("/piel/")
    Call<Cancer1> getResCancer2(@Part MultipartBody.Part image);

    @Multipart
    @POST("/breast/")
    Call<Cancer1> getResCancer3(@Part MultipartBody.Part image);

    @Multipart
    @POST("/colon/")
    Call<Cancer1> getResCancer4(@Part MultipartBody.Part image);

    @Multipart
    @POST("/breastmrt/")
    Call<Cancer1> getResCancer5(@Part MultipartBody.Part image);

    @Multipart
    @POST("/oral/")
    Call<Cancer1> getResCancer6(@Part MultipartBody.Part image);
}
