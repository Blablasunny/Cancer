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
import com.example.cancer.databinding.FragmentEditRecordBinding;
import com.example.cancer.models.user.UserInfo;
import com.example.cancer.models.write.Write;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class EditRecordFragment extends Fragment {

    FragmentEditRecordBinding binding;

    static final int GALLERY_REQUEST = 1;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    private Uri selectedImage1;
    private String str;
    private long id;
    private Word word;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditRecordBinding.inflate(inflater, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("write");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        binding.imvWrite.setImageResource(R.drawable.ic_add_image);

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnEdit.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new CreatingRecordFragment()).commit();
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new TypesOfCancerFragment()).commit();
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        wordRoomDatabase = WordRoomDatabase.getInstance(getActivity());

        Thread thread=new Thread(new AnotherRunnable());
        thread.start();

        binding.btnApprove.setOnClickListener(view -> {
            if (isInputValid()){
                wordRoomDatabase = WordRoomDatabase.getInstance(getActivity());
                Thread thread1=new Thread(new AnotherRunnable1());
                thread1.start();
            }else{
                Toast.makeText(getActivity(), R.string.ed_name_info, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnIm.setOnClickListener(view ->  {
            Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
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
                    selectedImage1 = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage1);
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
            id = getArguments().getLong("id_info");
            String str_in = wd.getInfoById(id);
            String str_im = wd.getImageById(id);
            String str_n = wd.getNameById(id);
            getActivity().runOnUiThread(new Runnable() {
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
            id = getArguments().getLong("id_info");
            String str_im = wd.getImageById(id);
            if (selectedImage1 == null && (str_im == null || str_im.equals(""))){
                str = "";
            }else if (selectedImage1 == null) {
                str = wd.getImageById(id);
            }else{
                str = selectedImage1.toString();
            }
            word = new Word(id, binding.etName.getText().toString(), binding.etInfo.getText().toString(), str);
            wd.update(word);

            getActivity().runOnUiThread(new Runnable() {
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
                                Toast.makeText(getActivity(), R.string.ex_load_img, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Write write = new Write(UserInfo.email, name, info, uri.toString(), id);
                                        mDatabase.push().setValue(write);
                                    }
                                });
                                binding.etName.setText("");
                                binding.etInfo.setText("");
                                binding.imvWrite.setImageResource(R.drawable.ic_add_image);
                            }
                        });
                    } else {
                        Write write = new Write(UserInfo.email, binding.etName.getText().toString(), binding.etInfo.getText().toString(), str, id);
                        mDatabase.push().setValue(write);
                    }

                    Bundle b = new Bundle();
                    b.putLong("id_info", word.getId());
                    MyRecordFragment myRecordFragment = new MyRecordFragment();
                    myRecordFragment.setArguments(b);
                    getFragmentManager().beginTransaction().add(R.id.MA, myRecordFragment).commit();
                }
            });
        }
    }

    String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}