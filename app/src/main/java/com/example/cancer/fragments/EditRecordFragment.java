package com.example.cancer.fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.R;
import com.example.cancer.data.words.WordDao;
import com.example.cancer.data.words.WordRoomDatabase;
import com.example.cancer.databinding.FragmentEditRecordBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class EditRecordFragment extends Fragment {

    FragmentEditRecordBinding binding;

    static final int GALLERY_REQUEST = 1;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    private Uri selectedImage1;
    private long id;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditRecordBinding.inflate(inflater, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("write");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        binding.imvWrite.setImageResource(R.drawable.ic_add_image);

        wordRoomDatabase = WordRoomDatabase.getInstance(getActivity());

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (isInputValid()) {
                    editor.putString("record_name", binding.etName.getText().toString());
                    editor.putString("info", binding.etInfo.getText().toString());
                    String strIm = wd.getImageById(id);
                    if (selectedImage1 == null) {
                        editor.putString("image", "");
                    }else {
                        editor.putString("image", selectedImage1.toString());
                    }
                    editor.putString("flag_edit_2", "1");
                } else {
                    editor.putString("flag_edit_2", "0");
                }
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        binding.etName.addTextChangedListener(textWatcher);
        binding.etInfo.addTextChangedListener(textWatcher);

        Thread thread=new Thread(new AnotherRunnable());
        thread.start();

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
                    sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    String strIm = wd.getImageById(id);
                    if (selectedImage1 == null && (strIm == null || strIm.equals(""))) {
                        editor.putString("image", "");
                    } else if (selectedImage1 == null) {
                        editor.putString("image", strIm);
                    } else {
                        editor.putString("image", selectedImage1.toString());
                    }
                    editor.commit();
                }
        }
    }


    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            wd = wordRoomDatabase.getWordDao();
            SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES , MODE_PRIVATE);
            id = prefs.getLong("id_info", 0);
            String strInfo = wd.getInfoById(id);
            String strImage = wd.getImageById(id);
            String strName = wd.getNameById(id);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.etName.setText(strName);
                    binding.etInfo.setText(strInfo);
                    if (strImage != null) {
                        if (!strImage.equals("")) {
                            Picasso.get().load(strImage).into(binding.imvWrite);
                        }
                    }
                }
            });
        }
    }

    boolean isInputValid(){
        return !binding.etName.getText().toString().isEmpty() && !binding.etInfo.getText().toString().isEmpty();
    }
}