package com.example.cancer.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cancer.R;
import com.example.cancer.databinding.ActivityTypeOfCancerBinding;

import java.io.IOException;

public class TypeOfCancerActivity extends AppCompatActivity {

    ActivityTypeOfCancerBinding binding;

    static final int GALLERY_REQUEST = 1;

    private Uri selectedImage;
    private int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTypeOfCancerBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(TypeOfCancerActivity.this, AccountActivity.class);
            startActivity(i);
        });

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(TypeOfCancerActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(TypeOfCancerActivity.this, MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            Intent i = new Intent(TypeOfCancerActivity.this, NewsActivity.class);
            startActivity(i);
        });

        Bundle bundle = getIntent().getExtras();
        n = bundle.getInt("type_cancer");

        if (n == 1) {
            binding.tvType.setText("Рак легких по КТ");
        } else if (n == 2) {
            binding.tvType.setText("Рак кожи");
        } else if (n == 3) {
            binding.tvType.setText("Рак молочной железы по биопсии");
        } else if (n == 4) {
            binding.tvType.setText("Рак молочной железы по МРТ");
        } else if (n == 5) {
            binding.tvType.setText("Рак толстой кишки по биопсии");
        } else {
            binding.tvType.setText("Рак полости рта по биопсии");
        }

        binding.btnSend.setOnClickListener(view ->  {
            Intent i = new Intent(TypeOfCancerActivity.this, TypeOfCancerResultActivity.class);
            if (selectedImage != null) {
                i.putExtra("selectImage", selectedImage.toString());
            } else {
                i.putExtra("selectImage", "");
            }
            i.putExtra("type_cancer", n);
            startActivity(i);
        });

        binding.btnIm.setOnClickListener(view ->  {
            Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imvDiagnosis.setImageBitmap(bitmap);
                }
        }
    }

}