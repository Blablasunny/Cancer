package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cancer.R;
import com.example.cancer.databinding.ActivityNewBinding;

public class NewActivity extends AppCompatActivity {

    ActivityNewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(NewActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(NewActivity.this, MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(NewActivity.this, TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(NewActivity.this, NewsActivity.class);
            startActivity(i);
        });
    }
}