package com.example.cancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class EditRecordActivity extends AppCompatActivity {

    WordRoomDatabase wordRoomDatabase;
    ArrayList<Word> data;
    WordDao wd;
    TextView n;
    TextView in;
    ImageView im;
    Uri selectedImage;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        Button button1 = (Button) findViewById(R.id.group_history);
        Button s = (Button) findViewById(R.id.save);
        n = (TextView) findViewById(R.id.editText2);
        in = (TextView) findViewById(R.id.editText1);
        im = (ImageView) findViewById(R.id.img1);

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new EditRecordActivity.AnotherRunnable());
        thread.start();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditRecordActivity.this, MyRecordsActivity.class);
                startActivity(i);
            }
        });

        s.setOnClickListener(view -> {
            if (isInputValid()){
                wordRoomDatabase = WordRoomDatabase.getInstance(this);
                Thread thread1=new Thread(new AnotherRunnable1());
                thread1.start();
            }else{
                Toast.makeText(this, "Введите имя записи и текст", Toast.LENGTH_SHORT).show();
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

    boolean isInputValid(){
        return !n.getText().toString().isEmpty() && !in.getText().toString().isEmpty();
    }

    class AnotherRunnable1 implements Runnable {
        @Override
        public void run() {
            data = (ArrayList<Word>) wordRoomDatabase
                    .getWordDao()
                    .loadAll();
            wd = wordRoomDatabase.getWordDao();
            Bundle bundle = getIntent().getExtras();
            String str = bundle.getString("name_info");
            String str_im = wd.getImageByName(str);
            Word word = new Word(n.getText().toString(), in.getText().toString(), str_im);
            wd.update(word);
        }
    }
}