package com.example.cancer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cancer1Client {
    public static Retrofit getClient(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();


        return new Retrofit.Builder()
                .baseUrl("http://192.168.0.21:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
