package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.example.cancer.databinding.ActivityNewsDetailBinding;

public class NewsDetailActivity extends AppCompatActivity {

    ActivityNewsDetailBinding binding;

    private String title, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");

        binding.tvTitle.setText(title);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadsImagesAutomatically(true);
        binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.loadUrl(url);

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(NewsDetailActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(NewsDetailActivity.this, MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(NewsDetailActivity.this, TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(NewsDetailActivity.this, AccountActivity.class);
            startActivity(i);
        });

        if (binding.webView.isShown()) {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}