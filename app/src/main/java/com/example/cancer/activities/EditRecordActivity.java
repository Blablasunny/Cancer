package com.example.cancer.activities;

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

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class EditRecordActivity extends AppCompatActivity {

    static final int GALLERY_REQUEST = 1;

    WordRoomDatabase wordRoomDatabase;
    ArrayList<Word> data;
    WordDao wd;

    Uri selectedImage1;
    String str;

    TextView etName;
    TextView etBook;
    ImageView imv;
    Button bBack;
    Button bSave;
    Button bImv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        bBack = (Button) findViewById(R.id.bt_back);
        bSave = (Button) findViewById(R.id.bt_save);
        bImv = (Button) findViewById(R.id.bt_imv);
        etName = (TextView) findViewById(R.id.et_name);
        etBook = (TextView) findViewById(R.id.et_book);
        imv = (ImageView) findViewById(R.id.imv);

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new EditRecordActivity.AnotherRunnable());
        thread.start();

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditRecordActivity.this, MyRecordsActivity.class);
                startActivity(i);
            }
        });

        bSave.setOnClickListener(view -> {
            if (isInputValid()){
                wordRoomDatabase = WordRoomDatabase.getInstance(this);
                Thread thread1=new Thread(new AnotherRunnable1());
                thread1.start();
            }else{
                Toast.makeText(this, "Введите имя записи и текст", Toast.LENGTH_SHORT).show();
            }
        });

        bImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
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
                    selectedImage1 = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imv.setImageBitmap(bitmap);
                }
        }
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
                    etName.setText(str_n);
                    etBook.setText(str_in);
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

    boolean isInputValid(){
        return !etName.getText().toString().isEmpty() && !etBook.getText().toString().isEmpty();
    }

    class AnotherRunnable1 implements Runnable {
        @Override
        public void run() {
            data = (ArrayList<Word>) wordRoomDatabase
                    .getWordDao()
                    .loadAll();
            wd = wordRoomDatabase.getWordDao();
            Bundle bundle = getIntent().getExtras();
            long str_id = bundle.getLong("id_info");
            String str_im = wd.getImageById(str_id);
            if (selectedImage1 == null && (str_im == null || str_im.equals(""))){
                str = "";
            }else if (selectedImage1 == null) {
                str = wd.getImageById(str_id);
            }else{
                str = selectedImage1.toString();
            }
            Word word = new Word(str_id, etName.getText().toString(), etBook.getText().toString(), str);
            wd.update(word);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(EditRecordActivity.this, MyRecordActivity.class);
                    i.putExtra("id_info", word.getId());
                    startActivity(i);
                }
            });
        }
    }
}