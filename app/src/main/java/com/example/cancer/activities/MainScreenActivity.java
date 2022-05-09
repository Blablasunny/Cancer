package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cancer.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainScreenActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Button button1 = (Button) findViewById(R.id.group_scan);
        Button button2 = (Button) findViewById(R.id.group_new);
        Button button3 = (Button) findViewById(R.id.group_history);
        Button button4 = (Button) findViewById(R.id.group_history1);

        mAuth = FirebaseAuth.getInstance();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreenActivity.this, TypesOfCancerActivity.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreenActivity.this, CreatingRecordActivity.class);
                startActivity(i);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreenActivity.this, MyRecordsActivity.class);
                startActivity(i);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(MainScreenActivity.this, AuthActivity.class);
                startActivity(i);
            }
        });
    }
}