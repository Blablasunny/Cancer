package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cancer.R;

public class TypesOfCancerActivity extends AppCompatActivity {

    Button bBack;
    Button bCancer1;
    Button bCancer2;
    Button bCancer3;
    Button bCancer4;
    Button bCancer5;
    Button bCancer6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_of_cancer);

        bBack = (Button) findViewById(R.id.bt_back);
        bCancer1 = (Button) findViewById(R.id.bt_cancer_1);
        bCancer2 = (Button) findViewById(R.id.bt_cancer_2);
        bCancer3 = (Button) findViewById(R.id.bt_cancer_3);
        bCancer4 = (Button) findViewById(R.id.bt_cancer_4);
        bCancer5 = (Button) findViewById(R.id.bt_cancer_5);
        bCancer6 = (Button) findViewById(R.id.bt_cancer_6);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, MainScreenActivity.class);
                startActivity(i);
            }
        });

        bCancer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer1Activity.class);
                startActivity(i);
            }
        });

        bCancer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer2Activity.class);
                startActivity(i);
            }
        });

        bCancer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer3Activity.class);
                startActivity(i);
            }
        });

        bCancer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer4Activity.class);
                startActivity(i);
            }
        });

        bCancer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer5Activity.class);
                startActivity(i);
            }
        });

        bCancer6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypesOfCancerActivity.this, TypeOfCancer6Activity.class);
                startActivity(i);
            }
        });
    }
}