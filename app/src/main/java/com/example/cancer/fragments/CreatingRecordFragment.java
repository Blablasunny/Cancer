package com.example.cancer.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.databinding.FragmentCreatingRecordBinding;
import com.example.cancer.models.user.UserInfo;
import com.example.cancer.models.write.Write;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CreatingRecordFragment extends Fragment {

    FragmentCreatingRecordBinding binding;

    static final int GALLERY_REQUEST = 1;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    private Uri selectedImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreatingRecordBinding.inflate(inflater, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("write");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        binding.imvWrite.setImageResource(R.drawable.ic_add_image);

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnScroll.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsFragment()).commit();
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new TypesOfCancerFragment()).commit();
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        binding.btnIm.setOnClickListener(view ->  {
            Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });

        binding.btnApprove.setOnClickListener(view -> {
            if (isInputValid()){
                wordRoomDatabase = WordRoomDatabase.getInstance(getActivity());
                Thread thread=new Thread(new AnotherRunnable());
                thread.start();
            }else{
                Toast.makeText(getActivity(), R.string.ed_name_info, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
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
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            wd = wordRoomDatabase.getWordDao();
            if (selectedImage != null) {
                Word word = new Word(UserInfo.id, binding.etName.getText().toString(), binding.etInfo.getText().toString(), selectedImage.toString());
                wd.insert(word);
            } else {
                Word word = new Word(UserInfo.id, binding.etName.getText().toString(), binding.etInfo.getText().toString(), "");
                wd.insert(word);
            }
            getActivity().runOnUiThread(new Runnable() {
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
                                Toast.makeText(getActivity(), R.string.ex_load_img, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Write write = new Write(UserInfo.email, name, info, uri.toString(), UserInfo.id);
                                        mDatabase.push().setValue(write);
                                    }
                                });
                                Toast.makeText(getActivity(), R.string.data_add, Toast.LENGTH_SHORT).show();
                                binding.etName.setText("");
                                binding.etInfo.setText("");
                                binding.imvWrite.setImageResource(R.drawable.ic_add_image);
                            }
                        });
                    } else {
                        Write write = new Write(UserInfo.email, binding.etName.getText().toString(), binding.etInfo.getText().toString(), "", UserInfo.id);
                        mDatabase.push().setValue(write);
                        Toast.makeText(getActivity(), R.string.data_add, Toast.LENGTH_SHORT).show();
                        binding.etName.setText("");
                        binding.etInfo.setText("");
                        binding.imvWrite.setImageResource(R.drawable.ic_add_image);
                    }
                }
            });
        }
    }
}