package com.example.cancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TypesOfCancerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_of_cancer);
        Button button1 = (Button) findViewById(R.id.group_history);
        Button cancer1 = (Button) findViewById(R.id.cancer_1);
        Button cancer2 = (Button) findViewById(R.id.cancer_2);
        Button cancer3 = (Button) findViewById(R.id.cancer_3);
        Button cancer4 = (Button) findViewById(R.id.cancer_4);
        Button cancer5 = (Button) findViewById(R.id.cancer_5);
        Button cancer6 = (Button) findViewById(R.id.cancer_6);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, MainScreenActivity.class);
                startActivity(i);
            }
        });

        cancer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer1Activity.class);
                startActivity(i);
            }
        });

        cancer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer2Activity.class);
                startActivity(i);
            }
        });

        cancer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer3Activity.class);
                startActivity(i);
            }
        });

        cancer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer4Activity.class);
                startActivity(i);
            }
        });

        cancer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer5Activity.class);
                startActivity(i);
            }
        });

        cancer6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer6Activity.class);
                startActivity(i);
            }
        });
    }
}