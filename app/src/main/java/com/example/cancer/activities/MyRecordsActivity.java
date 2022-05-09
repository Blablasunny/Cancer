package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordListAdapter;
import com.example.cancer.data.WordRoomDatabase;

import java.util.ArrayList;

public class MyRecordsActivity extends AppCompatActivity {

    WordRoomDatabase wordRoomDatabase;
    ArrayList<Word> data;
    WordListAdapter wordAdapter;
    RecyclerView recyclerView;

    EditText etAnswer;
    Button bBack;
    Button bAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_records);

        bBack = (Button) findViewById(R.id.bt_back);
        etAnswer = (EditText) findViewById(R.id.et_answer);
        bAnswer = (Button) findViewById(R.id.bt_answer);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new AnotherRunnable());
        thread.start();

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyRecordsActivity.this, MainScreenActivity.class);
                startActivity(i);
            }
        });

        bAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etAnswer.getText().toString().isEmpty()) {
                    Thread thread1 = new Thread(new AnotherRunnable1());
                    thread1.start();
                } else {
                    Thread thread2 = new Thread(new AnotherRunnable());
                    thread2.start();
                }
            }
        });
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
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyRecordsActivity.this));
                    recyclerView.setAdapter(wordAdapter);
                }
            });
        }
    }

    class AnotherRunnable1 implements Runnable {
        @Override
        public void run() {
            String s = etAnswer.getText().toString();
            data = (ArrayList<Word>) wordRoomDatabase
                    .getWordDao()
                    .loadAllByName(s);
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
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyRecordsActivity.this));
                    recyclerView.setAdapter(wordAdapter);
                }
            });
        }
    }
}


