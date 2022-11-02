package com.example.cancer.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.data.words.Word;
import com.example.cancer.data.words.WordDao;
import com.example.cancer.data.words.WordRoomDatabase;
import com.example.cancer.databinding.FragmentAuthBinding;
import com.example.cancer.models.user.User;
import com.example.cancer.models.user.UserInfo;
import com.example.cancer.models.write.Write;
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

public class AuthFragment extends Fragment {

    FragmentAuthBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseWrite;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    String myEmail, myPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAuthBinding.inflate(inflater, container, false);
        System.out.println(binding.etEmail.getText().toString() + "222");
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("flag_reg_1", "0");
        editor.putString("flag_reg_2", "0");
        editor.commit();

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

        wordRoomDatabase = WordRoomDatabase.getInstance(getActivity());

        binding.btnSignIn.setOnClickListener(view ->  {
            if (isInputValid()) {
                signIn(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
            } else {
                Toast.makeText(getActivity(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSignUp.setOnClickListener(view ->  {
            getFragmentManager().beginTransaction().add(R.id.MA, new RegistFragment()).commit();
        });

        return binding.getRoot();
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), R.string.auth_success, Toast.LENGTH_SHORT).show();
                    UserInfo.email = email;

                    ValueEventListener vListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            myPass = binding.etPassword.getText().toString();
                            myEmail = binding.etEmail.getText().toString();
                            binding.etEmail.setText("");
                            System.out.println(binding.etEmail.getText().toString() + "333");
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                User user = ds.getValue(User.class);
                                if (user != null && user.getEmail().equals(UserInfo.email)) {
                                    UserInfo.med = user.getMed();
                                    UserInfo.name = user.getName();
                                    UserInfo.surname = user.getSurname();
                                    UserInfo.patronymic = user.getPatronymic();
                                    UserInfo.phone = user.getPhone();
                                    UserInfo.id = 0;
                                    mDatabaseWrite = FirebaseDatabase.getInstance().getReference("write");

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
                    Toast.makeText(getActivity(), R.string.ex_auth, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isInputValid(){
        return !binding.etPassword.getText().toString().isEmpty() && !binding.etPassword.getText().toString().isEmpty();
    }

    boolean isInputValidMy(){
        return !myEmail.isEmpty() && !myPass.isEmpty();
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
                        if (write.getEmail().equals(UserInfo.email)) {
                            Word word = new Word(write.getId(), write.getName(), write.getInfo(), write.getImage(),
                                    write.getPatientName(), write.getPatientSurname(), write.getPatientPatronymic(),
                                    write.getPatientPhone(), write.getDay(), write.getMonth(), write.getYear());
                            wd.insert(word);
                        }
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

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isInputValidMy()) {
                        getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
                    }
                }
            });
        }
    }
}