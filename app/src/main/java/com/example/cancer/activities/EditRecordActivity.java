package com.example.cancer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.models.write.Write;
import com.example.cancer.databinding.ActivityEditRecordBinding;
import com.example.cancer.models.user.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class EditRecordActivity extends AppCompatActivity {

    ActivityEditRecordBinding binding;

    static final int GALLERY_REQUEST = 1;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    private Uri selectedImage1;
    private String str;
    private long str_id;
    private Word word;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditRecordBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance().getReference("write");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        binding.imvWrite.setImageResource(R.drawable.ic_add_image);

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(EditRecordActivity.this, AccountActivity.class);
            startActivity(i);
        });

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(EditRecordActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(EditRecordActivity.this, TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            Intent i = new Intent(EditRecordActivity.this, NewsActivity.class);
            startActivity(i);
        });

        wordRoomDatabase = WordRoomDatabase.getInstance(this);

        Thread thread=new Thread(new EditRecordActivity.AnotherRunnable());
        thread.start();

        binding.btnApprove.setOnClickListener(view -> {
            if (isInputValid()){
                wordRoomDatabase = WordRoomDatabase.getInstance(this);
                Thread thread1=new Thread(new AnotherRunnable1());
                thread1.start();
            }else{
                Toast.makeText(this, R.string.ed_name_info, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnIm.setOnClickListener(view ->  {
            Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
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
                    binding.imvWrite.setImageBitmap(bitmap);
                }
        }
    }


    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            wd = wordRoomDatabase.getWordDao();
            Bundle bundle = getIntent().getExtras();
            long str_id = bundle.getLong("id_info");
            String str_in = wd.getInfoById(str_id);
            String str_im = wd.getImageById(str_id);
            String str_n = wd.getNameById(str_id);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.etName.setText(str_n);
                    binding.etInfo.setText(str_in);
                    if (str_im != null) {
                        if (!str_im.equals("")) {
                            Picasso.get().load(str_im).into(binding.imvWrite);
                        } else {
                            binding.imvWrite.setImageResource(R.drawable.ic_add_image);
                        }
                    }
                }
            });
        }
    }

    boolean isInputValid(){
        return !binding.etName.getText().toString().isEmpty() && !binding.etInfo.getText().toString().isEmpty();
    }

    class AnotherRunnable1 implements Runnable {
        @Override
        public void run() {
            wd = wordRoomDatabase.getWordDao();
            Bundle bundle = getIntent().getExtras();
            str_id = bundle.getLong("id_info");
            String str_im = wd.getImageById(str_id);
            if (selectedImage1 == null && (str_im == null || str_im.equals(""))){
                str = "";
            }else if (selectedImage1 == null) {
                str = wd.getImageById(str_id);
            }else{
                str = selectedImage1.toString();
            }
            word = new Word(str_id, binding.etName.getText().toString(), binding.etInfo.getText().toString(), str);
            wd.update(word);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (selectedImage1 != null) {
                        StorageReference fileRef = mStorageRef.child(System.currentTimeMillis()
                                + "." + getFileExtension(selectedImage1));
                        UploadTask uploadTask = fileRef.putFile(selectedImage1);

                        String name = binding.etName.getText().toString();
                        String info = binding.etInfo.getText().toString();

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(EditRecordActivity.this, R.string.ex_load_img, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Write write = new Write(UserInfo.email, name, info, uri.toString(), str_id);
                                        mDatabase.push().setValue(write);
                                    }
                                });
                                binding.etName.setText("");
                                binding.etInfo.setText("");
                                binding.imvWrite.setImageResource(R.drawable.ic_add_image);
                            }
                        });
                    } else {
                        Write write = new Write(UserInfo.email, binding.etName.getText().toString(), binding.etInfo.getText().toString(), str, str_id);
                        mDatabase.push().setValue(write);
                    }

                    Intent i = new Intent(EditRecordActivity.this, MyRecordActivity.class);
                    i.putExtra("id_info", word.getId());
                    startActivity(i);
                }
            });
        }
    }

    String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}