package com.example.cancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MyRecordsActivity extends AppCompatActivity {

    WordRoomDatabase wordRoomDatabase;
    ArrayList<Word> data;
    WordListAdapter wordAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_records);
        Button button1 = (Button) findViewById(R.id.group_history);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new AnotherRunnable());
        thread.start();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyRecordsActivity.this, MainScreenActivity.class);
                startActivity(i);
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
                            i.putExtra("name_info", word.getName());
                            startActivity(i);
                        }
                    };
                    wordAdapter = new WordListAdapter(MyRecordsActivity.this, data, wordClickListener);
                    recyclerView.setLayoutManager(new GridLayoutManager(MyRecordsActivity.this, 2));
                    recyclerView.setAdapter(wordAdapter);
                }
            });
        }
    }
}


