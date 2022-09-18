package com.example.cancer.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.R;
import com.example.cancer.activities.AccountActivity;
import com.example.cancer.activities.CreatingRecordActivity;
import com.example.cancer.activities.MyRecordsActivity;
import com.example.cancer.activities.NewsDetailActivity;
import com.example.cancer.activities.TypesOfCancerActivity;
import com.example.cancer.databinding.ActivityNewsDetailBinding;
import com.example.cancer.databinding.FragmentNewsDetailBinding;

public class NewsDetailFragment extends Fragment {

    FragmentNewsDetailBinding binding;

    private String title, content, date, description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsDetailBinding.inflate(inflater, container, false);

        title = getArguments().getString("title");
        content = getArguments().getString("content");
        date = getArguments().getString("date");
        description = getArguments().getString("description");

        binding.tvTitle.setText(title);
        binding.tvDate.setText(date);
        if (content == null) {
            binding.tvContent.setText(description);
        } else {
            binding.tvContent.setText(content);
        }

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
}