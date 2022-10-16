package com.example.cancer.fragments;

import static android.app.Activity.RESULT_OK;

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
import com.example.cancer.databinding.FragmentCreatingRecordBinding;

import java.io.IOException;

public class CreatingRecordFragment extends Fragment {

    FragmentCreatingRecordBinding binding;

    static final int GALLERY_REQUEST = 1;

    private Uri selectedImage;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreatingRecordBinding.inflate(inflater, container, false);

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
                    if (selectedImage != null) {
                        editor.putString("image", selectedImage.toString());
                    } else {
                        editor.putString("image", "");
                    }
                    editor.putString("flag_create_2", "1");
                } else {
                    editor.putString("flag_create_2", "0");
                }
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        binding.etName.addTextChangedListener(textWatcher);
        binding.etInfo.addTextChangedListener(textWatcher);

        binding.imvWrite.setImageResource(R.drawable.ic_add_image);

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
                    selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imvWrite.setImageBitmap(bitmap);
                    sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    if (selectedImage != null) {
                        editor.putString("image", selectedImage.toString());
                    } else {
                        editor.putString("image", "");
                    }
                    editor.commit();
                }
        }
    }
    boolean isInputValid(){
        return !binding.etName.getText().toString().isEmpty() && !binding.etInfo.getText().toString().isEmpty();
    }
}