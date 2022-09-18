package com.example.cancer.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.cancer.R;
import com.example.cancer.activities.CreatingRecordActivity;
import com.example.cancer.activities.MyRecordsActivity;
import com.example.cancer.activities.NewsActivity;
import com.example.cancer.activities.TypesOfCancerActivity;
import com.example.cancer.databinding.FragmentAccountBinding;
import com.example.cancer.models.user.UserInfo;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);

        hideKeyboardFrom(getActivity(), container);

        binding.tvInfo.setText(Html.fromHtml(getString(R.string.tv_surname) + UserInfo.surname + "<br />" +
                getString(R.string.tv_name) + UserInfo.name + "<br />" +
                getString(R.string.tv_patronymic) + UserInfo.patronymic + "<br />" +
                getString(R.string.tv_med) + UserInfo.med + "<br />" +
                getString(R.string.tv_email) + UserInfo.email + "<br />" +
                getString(R.string.tv_phone) + UserInfo.phone));

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

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        return binding.getRoot();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}