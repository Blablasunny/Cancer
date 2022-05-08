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

public class RegistActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETPassword;
    private EditText ETEmail;
    private EditText ETPassword2;
    private Button bBack;
    private Button bSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        ETPassword = (EditText) findViewById(R.id.editText2);
        ETEmail = (EditText) findViewById(R.id.editText1);
        ETPassword2 = (EditText) findViewById(R.id.editText3);
        bBack = (Button) findViewById(R.id.back);
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
                    signUp(ETEmail.getText().toString(), ETPassword.getText().toString(), ETPassword2.getText().toString());
                } else {
                    Toast.makeText(RegistActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistActivity.this, AuthActivity.class);
                startActivity(i);
            }
        });

    }

    public void signUp(String email, String password, String password2) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && password2.equals(password)) {
                    Toast.makeText(RegistActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegistActivity.this, AuthActivity.class);
                    startActivity(i);
                } else if (!password2.equals(password)){
                    Toast.makeText(RegistActivity.this, "Пароли должны совпадать", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistActivity.this, "Не удалось зарегистрироваться", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isInputValid(){
        return !ETEmail.getText().toString().isEmpty() && !ETPassword.getText().toString().isEmpty() && !ETPassword2.getText().toString().isEmpty();
    }
}