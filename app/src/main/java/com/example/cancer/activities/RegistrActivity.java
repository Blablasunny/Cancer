package com.example.cancer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cancer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText etPassword;
    private EditText etEmail;
    private EditText etPassword2;
    private Button bBack;
    private Button bSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        etPassword = (EditText) findViewById(R.id.et_password);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword2 = (EditText) findViewById(R.id.et_password_2);
        bBack = (Button) findViewById(R.id.bt_back);
        bSignUp = (Button) findViewById(R.id.sign_up);

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

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid()) {
                    signUp(etEmail.getText().toString(), etPassword.getText().toString(), etPassword2.getText().toString());
                } else {
                    Toast.makeText(RegistrActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrActivity.this, AuthActivity.class);
                startActivity(i);
            }
        });

    }

    public void signUp(String email, String password, String password2) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && password2.equals(password) && password.length() >= 8) {
                    Toast.makeText(RegistrActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
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
        return !etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty() && !etPassword2.getText().toString().isEmpty();
    }
}