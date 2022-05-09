package com.example.cancer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cancer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AuthActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETPassword;
    private EditText ETEmail;
    private Button bSignIn;
    private Button bSignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ETPassword = (EditText) findViewById(R.id.editText1);
        ETEmail = (EditText) findViewById(R.id.editText2);
        bSignIn = (Button) findViewById(R.id.sign_in);
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

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid()) {
                    signIn(ETEmail.getText().toString(), ETPassword.getText().toString());
                } else {
                    Toast.makeText(AuthActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthActivity.this, RegistrActivity.class);
                startActivity(i);
            }
        });

    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AuthActivity.this, MainScreenActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(AuthActivity.this, "Не удалось авторизоваться", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isInputValid(){
        return !ETPassword.getText().toString().isEmpty() && !ETEmail.getText().toString().isEmpty();
    }
}