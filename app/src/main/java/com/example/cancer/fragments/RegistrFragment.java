package com.example.cancer.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.databinding.FragmentRegistrBinding;
import com.example.cancer.models.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrFragment extends Fragment {

    FragmentRegistrBinding binding;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegistrBinding.inflate(inflater, container, false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (isInputValid()) {
                    editor.putString("email", binding.etEmail.getText().toString());
                    editor.putString("password", binding.etPassword.getText().toString());
                    editor.putString("password2", binding.etPassword2.getText().toString());
                    editor.putString("flag_reg_2", "1");
                } else {
                    editor.putString("flag_reg_2", "0");
                }
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        binding.etEmail.addTextChangedListener(textWatcher);
        binding.etPassword.addTextChangedListener(textWatcher);
        binding.etPassword2.addTextChangedListener(textWatcher);
        return binding.getRoot();
    }

    boolean isInputValid(){
        return !binding.etEmail.getText().toString().isEmpty() && !binding.etPassword.getText().toString().isEmpty() && !binding.etPassword2.getText().toString().isEmpty();
    }
}