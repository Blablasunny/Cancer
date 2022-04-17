package com.example.cancer;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


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