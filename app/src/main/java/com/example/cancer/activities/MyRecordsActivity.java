package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordListAdapter;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.databinding.ActivityMyRecordBinding;
import com.example.cancer.databinding.ActivityMyRecordsBinding;

import java.util.ArrayList;

public class MyRecordsActivity extends AppCompatActivity {

    ActivityMyRecordsBinding binding;

    WordRoomDatabase wordRoomDatabase;
    ArrayList<Word> data;
    WordListAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyRecordsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(MyRecordsActivity.this, AccountActivity.class);
            startActivity(i);
        });

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(MyRecordsActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(MyRecordsActivity.this, TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            Intent i = new Intent(MyRecordsActivity.this, NewsActivity.class);
            startActivity(i);
        });

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new AnotherRunnable());
        thread.start();

//        bAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!etAnswer.getText().toString().isEmpty()) {
//                    Thread thread1 = new Thread(new AnotherRunnable1());
//                    thread1.start();
//                } else {
//                    Thread thread2 = new Thread(new AnotherRunnable());
//                    thread2.start();
//                }
//            }
//        });
    }
    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            data = (ArrayList<Word>) wordRoomDatabase
                    .getWordDao()
                    .loadAll();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WordListAdapter.OnWordClickListener wordClickListener = new WordListAdapter.OnWordClickListener() {
                        @Override
                        public void onWordClick(Word word, int position) {
                            Intent i = new Intent(MyRecordsActivity.this, MyRecordActivity.class);
                            i.putExtra("id_info", word.getId());
                            startActivity(i);
                        }
                    };
                    wordAdapter = new WordListAdapter(MyRecordsActivity.this, data, wordClickListener);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(MyRecordsActivity.this));
                    binding.recyclerview.setAdapter(wordAdapter);
                }
            });
        }
    }

//    class AnotherRunnable1 implements Runnable {
//        @Override
//        public void run() {
//            String s = etAnswer.getText().toString();
//            data = (ArrayList<Word>) wordRoomDatabase
//                    .getWordDao()
//                    .loadAllByName(s);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    WordListAdapter.OnWordClickListener wordClickListener = new WordListAdapter.OnWordClickListener() {
//                        @Override
//                        public void onWordClick(Word word, int position) {
//                            Intent i = new Intent(MyRecordsActivity.this, MyRecordActivity.class);
//                            i.putExtra("id_info", word.getId());
//                            startActivity(i);
//                        }
//                    };
//                    wordAdapter = new WordListAdapter(MyRecordsActivity.this, data, wordClickListener);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(MyRecordsActivity.this));
//                    recyclerView.setAdapter(wordAdapter);
//                }
//            });
//        }
//    }
}


