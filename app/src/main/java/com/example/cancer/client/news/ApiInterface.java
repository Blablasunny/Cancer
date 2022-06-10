package com.example.cancer.client.news;

import com.example.cancer.models.news.HeadLines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("news")
    Call<HeadLines> getHeadLines(
            @Query("apiKey") String apiKey,
            @Query("q") String q
    );
}
