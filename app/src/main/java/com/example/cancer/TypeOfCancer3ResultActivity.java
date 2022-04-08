package com.example.cancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TypeOfCancer3ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_cancer_3_result);
        Button button1 = (Button) findViewById(R.id.group_history);
        ImageView imv = (ImageView) findViewById(R.id.img1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypeOfCancer3ResultActivity.this, TypeOfCancer3Activity.class);
                startActivity(i);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = bundle.getString("selectImage");
            if (str != null) {
                if (!str.equals("")) {
                    Uri selectedImage = Uri.parse(str);
                    imv.setImageURI(selectedImage);
                }
            }
        }
    }
}