package com.example.cancer.client.cancer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CancerClient {
    public static Retrofit getClient(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://192.168.0.15:5000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }
}
