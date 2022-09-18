package com.example.cancer.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.activities.CreatingRecordActivity;
import com.example.cancer.activities.MyRecordsActivity;
import com.example.cancer.activities.TypesOfCancerActivity;
import com.example.cancer.adapter.NewsAdapter;
import com.example.cancer.client.news.ApiClient;
import com.example.cancer.databinding.FragmentNewsBinding;
import com.example.cancer.models.news.HeadLines;
import com.example.cancer.models.news.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    FragmentNewsBinding binding;

    NewsAdapter newsAdapter;
    List<Results> results = new ArrayList<>();

    final String API_KEY = "pub_81996a6c4446b582afeed0ca0e0e5e6da28d";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRequest(API_KEY);
            }
        });

        doRequest(API_KEY);

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        return binding.getRoot();
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
                    newsAdapter = new NewsAdapter(getActivity(), results);
                    binding.recyclerview.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {
                binding.swipeRefresh.setRefreshing(false);

                Toast.makeText(getActivity(), R.string.ex_internet, Toast.LENGTH_LONG).show();
            }
        });
    }
}