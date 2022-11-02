package com.example.cancer.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.cancer.R;
import com.example.cancer.adapter.registr.RegistrAdapter;
import com.example.cancer.databinding.FragmentRegistBinding;
import com.example.cancer.models.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistFragment extends Fragment {

    FragmentRegistBinding binding;

    ViewPager viewPager;
    RegistrAdapter adapter;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private String name, surname, patronymic, med, phone, email, password, password2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegistBinding.inflate(inflater, container, false);

        viewPager = binding.viewPager;
        adapter = new RegistrAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("flag_reg_1", "0");
        editor.putString("flag_reg_2", "0");
        editor.commit();

        binding.btnBack.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AuthFragment()).commit();
        });

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

        binding.btnSignUp.setOnClickListener(view ->  {
            SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES , MODE_PRIVATE);
            if (prefs.getString("flag_reg_1", null).equals("1") && prefs.getString("flag_reg_2", null).equals("1")) {
                name = prefs.getString("name", null);
                surname = prefs.getString("surname", null);
                patronymic = prefs.getString("patronymic", null);
                med = prefs.getString("med", null);
                phone = prefs.getString("phone", null);
                email = prefs.getString("email", null);
                password = prefs.getString("password", null);
                password2 = prefs.getString("password2", null);
                signUp(email, password, password2);
            } else {
                Toast.makeText(getActivity(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
            }
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
}