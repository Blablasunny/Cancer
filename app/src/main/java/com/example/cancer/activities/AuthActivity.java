package com.example.cancer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.cancer.R;
import com.example.cancer.databinding.ActivityAuthBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import soup.neumorphism.NeumorphButton;


public class AuthActivity extends AppCompatActivity{

    ActivityAuthBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

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

        binding.btnSignIn.setOnClickListener(view ->  {
            if (isInputValid()) {
                signIn(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
            } else {
                Toast.makeText(AuthActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSignUp.setOnClickListener(view ->  {
            Intent i = new Intent(AuthActivity.this, RegistrInfoActivity.class);
            startActivity(i);
        });

    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AuthActivity.this, AccountActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(AuthActivity.this, "Не удалось авторизоваться", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isInputValid(){
        return !binding.etPassword.getText().toString().isEmpty() && !binding.etPassword.getText().toString().isEmpty();
    }
}