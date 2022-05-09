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

    Button bScan;
    Button bNew;
    Button bBooks;
    Button bSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        bScan = (Button) findViewById(R.id.bt_scan);
        bNew = (Button) findViewById(R.id.bt_new);
        bBooks = (Button) findViewById(R.id.bt_books);
        bSignOut = (Button) findViewById(R.id.bt_sign_out);

        mAuth = FirebaseAuth.getInstance();

        bScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreenActivity.this, TypesOfCancerActivity.class);
                startActivity(i);
            }
        });

        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreenActivity.this, CreatingRecordActivity.class);
                startActivity(i);
            }
        });

        bBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreenActivity.this, MyRecordsActivity.class);
                startActivity(i);
            }
        });

        bSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(MainScreenActivity.this, AuthActivity.class);
                startActivity(i);
            }
        });
    }
}