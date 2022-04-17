package com.example.cancer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cancer1Client {
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
                .baseUrl("http://192.168.0.20:5000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }
}
