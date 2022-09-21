package com.example.cancer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.R;
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
            getFragmentManager().beginTransaction().add(R.id.MA, new CreatingRecordFragment()).commit();
        });

        binding.btnScroll.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsFragment()).commit();
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new TypesOfCancerFragment()).commit();
        });

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnBack.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        return binding.getRoot();
    }
}