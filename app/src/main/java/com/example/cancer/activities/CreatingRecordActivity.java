package com.example.cancer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordListAdapter;
import com.example.cancer.data.WordRoomDatabase;

import java.io.IOException;
import java.util.ArrayList;


public class CreatingRecordActivity extends AppCompatActivity {

    static final int GALLERY_REQUEST = 1;
    private EditText edt2;
    private EditText edt;
    ArrayList<Word> data;
    WordListAdapter wordAdapter;
    WordRoomDatabase wordRoomDatabase;
    WordDao wd;
    Uri selectedImage;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_record);
        ImageView imv = (ImageView) findViewById(R.id.img1);
        Button button1 = (Button) findViewById(R.id.group_history);
        Button s = (Button) findViewById(R.id.save);
        edt = (EditText) findViewById(R.id.editText1);
        edt2 = (EditText) findViewById(R.id.editText2);
        TextView txtv = (TextView) findViewById(R.id.txt1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreatingRecordActivity.this, MainScreenActivity.class);
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

        s.setOnClickListener(view -> {
            if (isInputValid()){
                wordRoomDatabase = WordRoomDatabase.getInstance(this);
                Thread thread=new Thread(new AnotherRunnable());
                thread.start();
            }else{
                Toast.makeText(this, "Введите имя записи и текст", Toast.LENGTH_SHORT).show();
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
    boolean isInputValid(){
        return !edt2.getText().toString().isEmpty() && !edt.getText().toString().isEmpty();
    }

    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            data = (ArrayList<Word>) wordRoomDatabase
                    .getWordDao()
                    .loadAll();
            wd = wordRoomDatabase.getWordDao();
            if (selectedImage == null){
                str = "";
            }else{
                str = selectedImage.toString();
            }
            Word word = new Word(edt2.getText().toString(), edt.getText().toString(), str);
            wd.insertAll(word);
        }
    }
}