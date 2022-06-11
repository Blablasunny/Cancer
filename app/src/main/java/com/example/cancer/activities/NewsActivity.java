package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cancer.adapter.NewsAdapter;
import com.example.cancer.client.news.ApiClient;
import com.example.cancer.databinding.ActivityNewsBinding;
import com.example.cancer.models.news.HeadLines;
import com.example.cancer.models.news.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    ActivityNewsBinding binding;

    NewsAdapter newsAdapter;
    List<Results> results = new ArrayList<>();

    final String API_KEY = "pub_81996a6c4446b582afeed0ca0e0e5e6da28d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRequest(API_KEY);
            }
        });

        doRequest(API_KEY);

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(NewsActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(NewsActivity.this, MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(NewsActivity.this, TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(NewsActivity.this, AccountActivity.class);
            startActivity(i);
        });
    }

    public void doRequest(String apiKey) {
        binding.swipeRefresh.setRefreshing(true);

        Call<HeadLines> call = ApiClient.getInstance().getApi().getHeadLines(apiKey, "cancer OR oncology");
        call.enqueue(new Callback<HeadLines>() {
            @Override
            public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {
                if (response.isSuccessful() && response.body().getResults() != null) {
                    binding.swipeRefresh.setRefreshing(false);

                    results.clear();
                    results = response.body().getResults();
                    newsAdapter = new NewsAdapter(NewsActivity.this, results);
                    binding.recyclerview.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {
                binding.swipeRefresh.setRefreshing(false);

                Toast.makeText(NewsActivity.this, "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }
}