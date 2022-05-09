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

    ArrayList<Word> data;
    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    Uri selectedImage;
    String str;
    long id = -1;

    Button bBack;
    Button bSave;
    ImageView imv;
    private EditText etName;
    private EditText etBook;
    TextView tvHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_record);

        imv = (ImageView) findViewById(R.id.imv);
        bBack = (Button) findViewById(R.id.bt_back);
        bSave = (Button) findViewById(R.id.bt_save);
        etBook = (EditText) findViewById(R.id.et_book);
        etName = (EditText) findViewById(R.id.et_name);
        tvHint = (TextView) findViewById(R.id.tv_hint);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreatingRecordActivity.this, MainScreenActivity.class);
                startActivity(i);
            }
        });

        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHint.setText("");
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        bSave.setOnClickListener(view -> {
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
        return !etName.getText().toString().isEmpty() && !etBook.getText().toString().isEmpty();
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
            if (id == -1) {
                Word word = new Word(etName.getText().toString(), etBook.getText().toString(), str);
                id = wd.insert(word);
            }else{
                Word word = new Word(id, etName.getText().toString(), etBook.getText().toString(), str);
                wd.update(word);
            }
        }
    }
}
