package com.example.cancer.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cancer.R;

import java.io.IOException;


public class TypeOfCancer2Activity extends AppCompatActivity {

    static final int GALLERY_REQUEST = 1;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_cancer_2);
        ImageView imv = (ImageView) findViewById(R.id.img1);
        Button button1 = (Button) findViewById(R.id.group_history);
        Button button2 = (Button) findViewById(R.id.send);
        TextView txtv = (TextView) findViewById(R.id.txt1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypeOfCancer2Activity.this, TypesOfCancerActivity.class);
                startActivity(i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypeOfCancer2Activity.this, TypeOfCancer2ResultActivity.class);
                if (selectedImage != null) {
                    i.putExtra("selectImage", selectedImage.toString());
                }
                startActivity(i);
            }
        });
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtv.setText("");
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        ImageView imv = (ImageView) findViewById(R.id.img1);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imv.setImageBitmap(bitmap);
                }
        }
    }
}