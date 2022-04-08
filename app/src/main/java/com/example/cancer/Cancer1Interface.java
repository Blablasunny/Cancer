package com.example.cancer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Cancer1Interface {
    @Multipart
    @POST("/lung/")
    Call<Cancer1> getResCancer1(@Part MultipartBody.Part image);
}
