package com.example.cancer.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.models.write.Write;
import com.example.cancer.databinding.ActivityAuthBinding;
import com.example.cancer.models.user.User;
import com.example.cancer.models.user.UserInfo;
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

import java.util.ArrayList;


public class AuthActivity extends AppCompatActivity{

    ActivityAuthBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseWrite;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

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

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        binding.btnSignIn.setOnClickListener(view ->  {
            if (isInputValid()) {
                signIn(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
            } else {
                Toast.makeText(AuthActivity.this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AuthActivity.this, R.string.auth_success, Toast.LENGTH_SHORT).show();
                    UserInfo.email = email;

                    ValueEventListener vListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                User user = ds.getValue(User.class);
                                if (user != null && user.getEmail().equals(UserInfo.email)) {
                                    UserInfo.med = user.getMed();
                                    UserInfo.name = user.getName();
                                    UserInfo.surname = user.getSurname();
                                    UserInfo.patronymic = user.getPatronymic();
                                    UserInfo.phone = user.getPhone();
                                    UserInfo.id = 0;
                                    mDatabaseWrite = FirebaseDatabase.getInstance().getReference("write/" +
                                            UserInfo.email.substring(0, UserInfo.email.length() - 3) +
                                            UserInfo.email.substring(UserInfo.email.length() - 2));

                                    Thread thread = new Thread(new AnotherRunnable());
                                    thread.start();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, getString(R.string.ex_load_data), databaseError.toException());
                        }
                    };
                    mDatabase.addValueEventListener(vListener);
                } else {
                    Toast.makeText(AuthActivity.this, R.string.ex_auth, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isInputValid(){
        return !binding.etPassword.getText().toString().isEmpty() && !binding.etPassword.getText().toString().isEmpty();
    }

    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            ValueEventListener vListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    wd = wordRoomDatabase.getWordDao();
                    wd.deleteAll();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Write write = ds.getValue(Write.class);
                        Word word = new Word(write.getId(), write.getName(), write.getInfo(), write.getImage());
                        wd.insert(word);
                    }
                    ArrayList<Word> data = (ArrayList<Word>) wd.loadAll();
                    UserInfo.id = data.size() + 1;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, getString(R.string.ex_load_data), databaseError.toException());
                }
            };
            mDatabaseWrite.addValueEventListener(vListener);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(AuthActivity.this, TypesOfCancerActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}