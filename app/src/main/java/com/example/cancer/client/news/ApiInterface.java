package com.example.cancer.client.news;

import com.example.cancer.models.news.HeadLines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("everything")
    Call<HeadLines> getHeadLines(
            @Query("q") String q,
            @Query("apiKey") String apiKey,
            @Query("language") String language,
            @Query("sortBy") String sortBy
    );
}
