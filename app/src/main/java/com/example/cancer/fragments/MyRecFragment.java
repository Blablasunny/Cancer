package com.example.cancer.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.R;
import com.example.cancer.adapter.create.CreateAdapter;
import com.example.cancer.adapter.records.RecordAdapter;
import com.example.cancer.databinding.FragmentMyRecBinding;

public class MyRecFragment extends Fragment {

    FragmentMyRecBinding binding;

    ViewPager viewPager;
    RecordAdapter adapter;

    private long id;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyRecBinding.inflate(inflater, container, false);

        viewPager = binding.viewPager;
        adapter = new RecordAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        id = getArguments().getLong("id_info");

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnEdit.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new CreateRecordFragment()).commit();
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new TypesOfCancerFragment()).commit();
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        binding.btnEditWrite.setOnClickListener(view ->  {
            editor.putLong("id_info", 0);
            editor.commit();
            Bundle b = new Bundle();
            b.putLong("id_info", id);
            EdRecordFragment edRecordFragment = new EdRecordFragment();
            edRecordFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, edRecordFragment).commit();
        });

        binding.btnBack.setOnClickListener(view -> {
            editor.putLong("id_info", 0);
            editor.commit();
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsNameFragment()).commit();
        });

        editor.putLong("id_info", id);
        editor.commit();

        return binding.getRoot();
    }
}