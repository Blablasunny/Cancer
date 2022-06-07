package com.example.cancer.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.data.Write;
import com.example.cancer.databinding.ActivityCreatingRecordBinding;
import com.example.cancer.user.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;


public class CreatingRecordActivity extends AppCompatActivity {

    ActivityCreatingRecordBinding binding;

    static final int GALLERY_REQUEST = 1;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_record);

        binding = ActivityCreatingRecordBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance().getReference("write/" +
                UserInfo.email.substring(0, UserInfo.email.length() - 3) +
                UserInfo.email.substring(UserInfo.email.length() - 2));
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        binding.imvWrite.setImageResource(R.drawable.ic_add_image);

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(CreatingRecordActivity.this, AccountActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(CreatingRecordActivity.this, MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(CreatingRecordActivity.this, TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            Intent i = new Intent(CreatingRecordActivity.this, NewsActivity.class);
            startActivity(i);
        });

        binding.btnIm.setOnClickListener(view ->  {
            Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });

        binding.btnApprove.setOnClickListener(view -> {
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
                    binding.imvWrite.setImageBitmap(bitmap);
                }
        }
    }
    boolean isInputValid(){
        return !binding.etName.getText().toString().isEmpty() && !binding.etInfo.getText().toString().isEmpty();
    }

    String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            wd = wordRoomDatabase.getWordDao();
            if (selectedImage != null) {
                Word word = new Word(binding.etName.getText().toString(), binding.etInfo.getText().toString(), selectedImage.toString());
                wd.insert(word);
            } else {
                Word word = new Word(binding.etName.getText().toString(), binding.etInfo.getText().toString(), "");
                wd.insert(word);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (selectedImage != null) {
                        StorageReference fileRef = mStorageRef.child(System.currentTimeMillis()
                                + "." + getFileExtension(selectedImage));
                        UploadTask uploadTask = fileRef.putFile(selectedImage);

                        String name = binding.etName.getText().toString();
                        String info = binding.etInfo.getText().toString();

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(CreatingRecordActivity.this, "Не удалось сохранить изображение", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Write write = new Write(name, info, uri.toString());
                                        mDatabase.push().setValue(write);
                                    }
                                });
                                Toast.makeText(CreatingRecordActivity.this, "Запись создана", Toast.LENGTH_SHORT).show();
                                binding.etName.setText("");
                                binding.etInfo.setText("");
                                binding.imvWrite.setImageResource(R.drawable.ic_add_image);
                            }
                        });
                    } else {
                        Write write = new Write(binding.etName.getText().toString(), binding.etInfo.getText().toString(), "");
                        mDatabase.push().setValue(write);
                        Toast.makeText(CreatingRecordActivity.this, "Запись создана", Toast.LENGTH_SHORT).show();
                        binding.etName.setText("");
                        binding.etInfo.setText("");
                        binding.imvWrite.setImageResource(R.drawable.ic_add_image);
                    }
                }
            });
        }
    }
}
