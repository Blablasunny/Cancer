package com.example.cancer.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.databinding.FragmentRegistrInfoBinding;

public class RegistrInfoFragment extends Fragment {

    FragmentRegistrInfoBinding binding;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegistrInfoBinding.inflate(inflater, container, false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (isInputValid()) {
                    editor.putString("name", binding.etName.getText().toString());
                    editor.putString("surname", binding.etSurname.getText().toString());
                    editor.putString("patronymic", binding.etPatronymic.getText().toString());
                    editor.putString("med", binding.etMed.getText().toString());
                    editor.putString("phone", binding.etPhone.getText().toString());
                    editor.putString("flag_reg_1", "1");
                } else {
                    editor.putString("flag_reg_1", "0");
                }
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        binding.etName.addTextChangedListener(textWatcher);
        binding.etSurname.addTextChangedListener(textWatcher);
        binding.etPatronymic.addTextChangedListener(textWatcher);
        binding.etPhone.addTextChangedListener(textWatcher);
        binding.etMed.addTextChangedListener(textWatcher);

        return binding.getRoot();
    }

    boolean isInputValid(){
        return !binding.etSurname.getText().toString().isEmpty() && !binding.etName.getText().toString().isEmpty() && !binding.etPatronymic.getText().toString().isEmpty()
                && !binding.etMed.getText().toString().isEmpty() && !binding.etPhone.getText().toString().isEmpty();
    }
}