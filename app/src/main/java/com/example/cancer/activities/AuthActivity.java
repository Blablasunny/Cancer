package com.example.cancer.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cancer.databinding.ActivityAuthBinding;
import com.example.cancer.user.User;
import com.example.cancer.user.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import soup.neumorphism.NeumorphButton;


public class AuthActivity extends AppCompatActivity{

    ActivityAuthBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference mDatabase;

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

        mDatabase = FirebaseDatabase.getInstance().getReference("user");

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
                    UserInfo.email = email;

                    ValueEventListener vListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                User user = ds.getValue(User.class);
                                if (user.getEmail().equals(UserInfo.email)) {
                                    UserInfo.med = user.getMed();
                                    UserInfo.name = user.getName();
                                    UserInfo.surname = user.getSurname();
                                    UserInfo.patronymic = user.getPatronymic();
                                    UserInfo.phone = user.getPhone();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    };
                    mDatabase.addValueEventListener(vListener);

                    Intent i = new Intent(AuthActivity.this, TypesOfCancerActivity.class);
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