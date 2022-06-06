package com.example.cancer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.databinding.ActivityRegistrInfoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrInfoActivity extends AppCompatActivity {

    ActivityRegistrInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrInfoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(view ->  {
            if (isInputValid()) {
                Intent i = new Intent(RegistrInfoActivity.this, RegistrActivity.class);
                i.putExtra("name", binding.etName.getText().toString());
                i.putExtra("surname", binding.etSurname.getText().toString());
                i.putExtra("patronymic", binding.etPatronymic.getText().toString());
                i.putExtra("med", binding.etMed.getText().toString());
                i.putExtra("phone", binding.etPhone.getText().toString());
                startActivity(i);
            } else {
                Toast.makeText(RegistrInfoActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });

    }

    boolean isInputValid(){
        return !binding.etSurname.getText().toString().isEmpty() && !binding.etName.getText().toString().isEmpty() && !binding.etPatronymic.getText().toString().isEmpty()
                && !binding.etMed.getText().toString().isEmpty() && !binding.etPhone.getText().toString().isEmpty();
    }
}