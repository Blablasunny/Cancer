package com.example.cancer.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.cancer.R;
import com.example.cancer.databinding.FragmentAccountBinding;
import com.example.cancer.models.user.User;
import com.example.cancer.models.user.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);

        hideKeyboardFrom(getActivity(), container);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                } else {
                }
            }
        };

        binding.btnLogOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            getFragmentManager().beginTransaction().add(R.id.MA, new AuthFragment()).commit();
        });

//        binding.btnNotify.setOnClickListener(view -> {
//            getFragmentManager().beginTransaction().add(R.id.MA, new NotifyFragment()).commit();
//        });

        binding.tvInfo.setText(Html.fromHtml(getString(R.string.tv_surname) + UserInfo.surname + "<br />" +
                getString(R.string.tv_name) + UserInfo.name + "<br />" +
                getString(R.string.tv_patronymic) + UserInfo.patronymic + "<br />" +
                getString(R.string.tv_med) + UserInfo.med + "<br />" +
                getString(R.string.tv_email) + UserInfo.email + "<br />" +
                getString(R.string.tv_phone) + UserInfo.phone));

        binding.btnEdit.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new CreateRecordFragment()).commit();
        });

        binding.btnScroll.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsNameFragment()).commit();
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new TypesOfCancerFragment()).commit();
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