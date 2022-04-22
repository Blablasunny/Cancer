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
    TextView n;
    TextView in;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
        Button button1 = (Button) findViewById(R.id.group_history);
        Button ed = (Button) findViewById(R.id.edit);
        n = (TextView) findViewById(R.id.editText2);
        in = (TextView) findViewById(R.id.editText1);
        im = (ImageView) findViewById(R.id.img1);

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new MyRecordActivity.AnotherRunnable());
        thread.start();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyRecordActivity.this, MyRecordsActivity.class);
                startActivity(i);
            }
        });

        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyRecordActivity.this, EditRecordActivity.class);
                Bundle bundle = getIntent().getExtras();
                String str = bundle.getString("name_info");
                i.putExtra("name_info", str);
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
            String str = bundle.getString("name_info");
            String str_in = wd.getInfoByName(str);
            String str_im = wd.getImageByName(str);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    n.setText(str);
                    in.setText(str_in);
                    if (str_im != null) {
                        if (!str_im.equals("")) {
                            Uri selectedImage = Uri.parse(str_im);
                            im.setImageURI(selectedImage);
                        }
                    }
                }
            });
        }
    }
}