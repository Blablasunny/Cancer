package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cancer.R;
import com.example.cancer.databinding.ActivityTypesOfCancerBinding;

public class TypesOfCancerActivity extends AppCompatActivity {

    ActivityTypesOfCancerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTypesOfCancerBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(TypesOfCancerActivity.this, AccountActivity.class);
            startActivity(i);
        });

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(TypesOfCancerActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(TypesOfCancerActivity.this, MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            Intent i = new Intent(TypesOfCancerActivity.this, NewsActivity.class);
            startActivity(i);
        });

        binding.btnCancer1.setOnClickListener(view ->  {
            Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancerActivity.class);
            i.putExtra("type_cancer", 1);
            startActivity(i);
        });

        binding.btnCancer2.setOnClickListener(view ->  {
            Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancerActivity.class);
            i.putExtra("type_cancer", 2);
            startActivity(i);
        });

        binding.btnCancer3.setOnClickListener(view ->  {
            Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancerActivity.class);
            i.putExtra("type_cancer", 3);
            startActivity(i);
        });

        binding.btnCancer4.setOnClickListener(view ->  {
            Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancerActivity.class);
            i.putExtra("type_cancer", 4);
            startActivity(i);
        });

        binding.btnCancer5.setOnClickListener(view ->  {
            Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancerActivity.class);
            i.putExtra("type_cancer", 5);
            startActivity(i);
        });

        binding.btnCancer6.setOnClickListener(view ->  {
            Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancerActivity.class);
            i.putExtra("type_cancer", 6);
            startActivity(i);
        });
    }
}