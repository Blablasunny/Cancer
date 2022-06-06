package com.example.cancer.client;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CancerInterface {
    @Multipart
    @POST("/lungkt/")
    Call<Cancer> getResCancer1(@Part MultipartBody.Part image);

    @Multipart
    @POST("/piel/")
    Call<Cancer> getResCancer2(@Part MultipartBody.Part image);

    @Multipart
    @POST("/breast/")
    Call<Cancer> getResCancer3(@Part MultipartBody.Part image);

    @Multipart
    @POST("/colon/")
    Call<Cancer> getResCancer4(@Part MultipartBody.Part image);

    @Multipart
    @POST("/breastmrt/")
    Call<Cancer> getResCancer5(@Part MultipartBody.Part image);

    @Multipart
    @POST("/oral/")
    Call<Cancer> getResCancer6(@Part MultipartBody.Part image);
}
