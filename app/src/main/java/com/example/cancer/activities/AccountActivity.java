package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.example.cancer.R;
import com.example.cancer.databinding.ActivityAccountBinding;
import com.example.cancer.models.user.UserInfo;

public class AccountActivity extends AppCompatActivity {

    ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAccountBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.tvInfo.setText(Html.fromHtml(getString(R.string.tv_surname) + UserInfo.surname + "<br />" +
                getString(R.string.tv_name) + UserInfo.name + "<br />" +
                getString(R.string.tv_patronymic) + UserInfo.patronymic + "<br />" +
                getString(R.string.tv_med) + UserInfo.med + "<br />" +
                getString(R.string.tv_email) + UserInfo.email + "<br />" +
                getString(R.string.tv_phone) + UserInfo.phone));

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(AccountActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(AccountActivity.this, MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(AccountActivity.this, TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            Intent i = new Intent(AccountActivity.this, NewsActivity.class);
            startActivity(i);
        });
    }
}