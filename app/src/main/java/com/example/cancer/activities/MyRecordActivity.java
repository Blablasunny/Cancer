package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.databinding.ActivityMyRecordBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyRecordActivity extends AppCompatActivity {

    ActivityMyRecordBinding binding;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyRecordBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(MyRecordActivity.this, AccountActivity.class);
            startActivity(i);
        });

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(MyRecordActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(MyRecordActivity.this, TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            Intent i = new Intent(MyRecordActivity.this, NewsActivity.class);
            startActivity(i);
        });

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new MyRecordActivity.AnotherRunnable());
        thread.start();


        binding.btnEditWrite.setOnClickListener(view ->  {
            Intent i = new Intent(MyRecordActivity.this, EditRecordActivity.class);
            Bundle bundle = getIntent().getExtras();
            long str_id = bundle.getLong("id_info");
            i.putExtra("id_info", str_id);
            startActivity(i);
        });
    }

    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            wd = wordRoomDatabase.getWordDao();
            Bundle bundle = getIntent().getExtras();
            long str_id = bundle.getLong("id_info");
            String str_in = wd.getInfoById(str_id);
            String str_im = wd.getImageById(str_id);
            String str_n = wd.getNameById(str_id);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.tvName.setText(str_n);
                    binding.tvInfo.setText(str_in);
                    if (str_im != null) {
                        if (!str_im.equals("")) {
                            Picasso.get().load(str_im).into(binding.imvWrite);
                        }
                    }
                }
            });
        }
    }
}