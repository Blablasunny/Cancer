package com.example.cancer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.R;
import com.example.cancer.databinding.FragmentTypesOfCancerBinding;

public class TypesOfCancerFragment extends Fragment {

    FragmentTypesOfCancerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTypesOfCancerBinding.inflate(inflater, container, false);

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnEdit.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new CreateRecordFragment()).commit();
        });

        binding.btnScroll.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsNameFragment()).commit();
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        binding.btnCancer1.setOnClickListener(view ->  {
            Bundle b = new Bundle();
            b.putString("type_cancer", getString(R.string.cancer_1));
            b.putInt("type_cancer_number", 1);
            TypeOfCancerFragment typeOfCancerFragment = new TypeOfCancerFragment();
            typeOfCancerFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, typeOfCancerFragment).commit();
        });

        binding.btnCancer2.setOnClickListener(view ->  {
            Bundle b = new Bundle();
            b.putString("type_cancer", getString(R.string.cancer_2));
            b.putInt("type_cancer_number", 2);
            TypeOfCancerFragment typeOfCancerFragment = new TypeOfCancerFragment();
            typeOfCancerFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, typeOfCancerFragment).commit();
        });

        binding.btnCancer3.setOnClickListener(view ->  {
            Bundle b = new Bundle();
            b.putString("type_cancer", getString(R.string.cancer_3));
            b.putInt("type_cancer_number", 3);
            TypeOfCancerFragment typeOfCancerFragment = new TypeOfCancerFragment();
            typeOfCancerFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, typeOfCancerFragment).commit();
        });

        binding.btnCancer4.setOnClickListener(view ->  {
            Bundle b = new Bundle();
            b.putString("type_cancer", getString(R.string.cancer_4));
            b.putInt("type_cancer_number", 4);
            TypeOfCancerFragment typeOfCancerFragment = new TypeOfCancerFragment();
            typeOfCancerFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, typeOfCancerFragment).commit();
        });

        binding.btnCancer5.setOnClickListener(view ->  {
            Bundle b = new Bundle();
            b.putString("type_cancer", getString(R.string.cancer_5));
            b.putInt("type_cancer_number", 5);
            TypeOfCancerFragment typeOfCancerFragment = new TypeOfCancerFragment();
            typeOfCancerFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, typeOfCancerFragment).commit();
        });

        binding.btnCancer6.setOnClickListener(view ->  {
            Bundle b = new Bundle();
            b.putString("type_cancer", getString(R.string.cancer_6));
            b.putInt("type_cancer_number", 6);
            TypeOfCancerFragment typeOfCancerFragment = new TypeOfCancerFragment();
            typeOfCancerFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, typeOfCancerFragment).commit();
        });

        return binding.getRoot();
    }
}