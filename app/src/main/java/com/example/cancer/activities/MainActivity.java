package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.cancer.R;
import com.example.cancer.fragments.AuthFragment;
import com.example.cancer.fragments.RegistFragment;
import com.example.cancer.fragments.RegistrFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.MA, new AuthFragment()).commit();
    }
}