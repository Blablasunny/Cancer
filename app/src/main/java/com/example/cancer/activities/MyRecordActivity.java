package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;

import java.util.ArrayList;

public class MyRecordActivity extends AppCompatActivity {

    WordRoomDatabase wordRoomDatabase;
    ArrayList<Word> data;
    WordDao wd;

    TextView tvName;
    TextView tvBook;
    ImageView imv;
    Button bBack;
    Button bEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);

        bBack = (Button) findViewById(R.id.bt_back);
        bEdit = (Button) findViewById(R.id.bt_edit);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvBook = (TextView) findViewById(R.id.tv_book);
        imv = (ImageView) findViewById(R.id.imv);

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new MyRecordActivity.AnotherRunnable());
        thread.start();

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyRecordActivity.this, MyRecordsActivity.class);
                startActivity(i);
            }
        });

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyRecordActivity.this, EditRecordActivity.class);
                Bundle bundle = getIntent().getExtras();
                long str_id = bundle.getLong("id_info");
                i.putExtra("id_info", str_id);
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
            wd = wordRoomDatabase.getWordDao();
            Bundle bundle = getIntent().getExtras();
            long str_id = bundle.getLong("id_info");
            String str_in = wd.getInfoById(str_id);
            String str_im = wd.getImageById(str_id);
            String str_n = wd.getNameById(str_id);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvName.setText(str_n);
                    tvBook.setText(str_in);
                    if (str_im != null) {
                        if (!str_im.equals("")) {
                            Uri selectedImage = Uri.parse(str_im);
                            imv.setImageURI(selectedImage);
                        }
                    }
                }
            });
        }
    }
}