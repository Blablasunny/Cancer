package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.example.cancer.databinding.ActivityNewsDetailBinding;

public class NewsDetailActivity extends AppCompatActivity {

    ActivityNewsDetailBinding binding;

    private String title, content, date, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        date = intent.getStringExtra("date");
        description = intent.getStringExtra("description");

        binding.tvTitle.setText(title);
        binding.tvDate.setText(date);
        if (content == null) {
            binding.tvContent.setText(description);
        } else {
            binding.tvContent.setText(content);
        }

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
    }
}