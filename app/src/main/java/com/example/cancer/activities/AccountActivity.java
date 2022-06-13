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

        binding.tvInfo.setText(Html.fromHtml("<font color=#006060>" + R.string.surname + ": </font>" + UserInfo.surname + "<br />" +
                "<font color=#006060>" + R.string.name + ": </font>" + UserInfo.name + "<br />" +
                "<font color=#006060>" + R.string.patronymic + ": </font>" + UserInfo.patronymic + "<br />" +
                "<font color=#006060>" + R.string.med + ": </font>" + UserInfo.med + "<br />" +
                "<font color=#006060>" + R.string.email + ": </font>" + UserInfo.email + "<br />" +
                "<font color=#006060>" + R.string.phone + ": </font>" + UserInfo.phone));

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