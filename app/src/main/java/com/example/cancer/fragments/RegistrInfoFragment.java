package com.example.cancer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.databinding.FragmentRegistrInfoBinding;


public class RegistrInfoFragment extends Fragment {

    FragmentRegistrInfoBinding binding;

    private String flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegistrInfoBinding.inflate(inflater, container, false);

        flag = getArguments().getString("flag");
        if (flag.equals("1")) {
            binding.etName.setText(getArguments().getString("name"));
            binding.etSurname.setText(getArguments().getString("surname"));
            binding.etPatronymic.setText(getArguments().getString("patronymic"));
            binding.etMed.setText(getArguments().getString("med"));
            binding.etPhone.setText(getArguments().getString("phone"));
        }

        binding.btnNext.setOnClickListener(view ->  {
            if (isInputValid()) {
                Bundle b = new Bundle();
                b.putString("name", binding.etName.getText().toString());
                b.putString("surname", binding.etSurname.getText().toString());
                b.putString("patronymic", binding.etPatronymic.getText().toString());
                b.putString("med", binding.etMed.getText().toString());
                b.putString("phone", binding.etPhone.getText().toString());
                RegistrFragment registrFragment = new RegistrFragment();
                registrFragment.setArguments(b);
                getFragmentManager().beginTransaction().add(R.id.MA, registrFragment).commit();
            } else {
                Toast.makeText(getActivity(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnBack.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AuthFragment()).commit();
        });

        return binding.getRoot();
    }

    boolean isInputValid(){
        return !binding.etSurname.getText().toString().isEmpty() && !binding.etName.getText().toString().isEmpty() && !binding.etPatronymic.getText().toString().isEmpty()
                && !binding.etMed.getText().toString().isEmpty() && !binding.etPhone.getText().toString().isEmpty();
    }
}