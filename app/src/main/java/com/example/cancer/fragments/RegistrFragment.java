package com.example.cancer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private String name, surname, patronymic, med, phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegistrBinding.inflate(inflater, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("user");

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

        name = getArguments().getString("name");
        surname = getArguments().getString("surname");
        patronymic = getArguments().getString("patronymic");
        med = getArguments().getString("med");
        phone = getArguments().getString("phone");

        binding.btnSignUp.setOnClickListener(view ->  {
            if (isInputValid()) {
                signUp(binding.etEmail.getText().toString(), binding.etPassword.getText().toString(), binding.etPassword2.getText().toString());
            } else {
                Toast.makeText(getActivity(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnBack.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putString("name", name);
            b.putString("surname", surname);
            b.putString("patronymic", patronymic);
            b.putString("med", med);
            b.putString("phone", phone);
            b.putString("flag", "1");
            RegistrInfoFragment registrInfoFragment = new RegistrInfoFragment();
            registrInfoFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, registrInfoFragment).commit();
        });

        return binding.getRoot();
    }

    public void signUp(String email, String password, String password2) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && password2.equals(password) && password.length() >= 8) {
                    Toast.makeText(getActivity(), R.string.registr_success, Toast.LENGTH_SHORT).show();

                    User user = new User(name, surname, patronymic, med, phone, email);
                    mDatabase.push().setValue(user);

                    getFragmentManager().beginTransaction().add(R.id.MA, new AuthFragment()).commit();
                } else if (!password2.equals(password)) {
                    Toast.makeText(getActivity(), R.string.ex_password_is, Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(getActivity(), R.string.ex_password_short, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.ex_registr, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isInputValid(){
        return !binding.etEmail.getText().toString().isEmpty() && !binding.etPassword.getText().toString().isEmpty() && !binding.etPassword2.getText().toString().isEmpty();
    }
}