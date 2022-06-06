package com.example.cancer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.data.User;
import com.example.cancer.databinding.ActivityRegistrBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrActivity extends AppCompatActivity {

    ActivityRegistrBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabase;

    private String name, surname, patronymic, med, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

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

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        surname = bundle.getString("surname");
        patronymic = bundle.getString("patronymic");
        med = bundle.getString("med");
        phone = bundle.getString("phone");

        binding.btnSignUp.setOnClickListener(view ->  {
            if (isInputValid()) {
                signUp(binding.etEmail.getText().toString(), binding.etPassword.getText().toString(), binding.etPassword2.getText().toString());
            } else {
                Toast.makeText(RegistrActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void signUp(String email, String password, String password2) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && password2.equals(password) && password.length() >= 8) {
                    Toast.makeText(RegistrActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                    User user = new User(name, surname, patronymic, med, phone, email);
                    mDatabase.push().setValue(user);
                    Intent i = new Intent(RegistrActivity.this, AuthActivity.class);
                    startActivity(i);
                } else if (!password2.equals(password)) {
                    Toast.makeText(RegistrActivity.this, "Пароли должны совпадать", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(RegistrActivity.this, "Слишком короткий пароль", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrActivity.this, "Не удалось зарегистрироваться", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isInputValid(){
        return !binding.etEmail.getText().toString().isEmpty() && !binding.etPassword.getText().toString().isEmpty() && !binding.etPassword2.getText().toString().isEmpty();
    }
}