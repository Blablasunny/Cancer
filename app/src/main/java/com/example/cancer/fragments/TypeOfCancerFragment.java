package com.example.cancer.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.databinding.FragmentTypeOfCancerBinding;

import java.io.IOException;

public class TypeOfCancerFragment extends Fragment {

    FragmentTypeOfCancerBinding binding;

    static final int GALLERY_REQUEST = 1;

    private Uri selectedImage;
    private int n;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTypeOfCancerBinding.inflate(inflater, container, false);

        binding.imvDiagnosis.setImageResource(R.drawable.ic_add_image);

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnEdit.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new CreateRecordFragment()).commit();
        });

        binding.btnScroll.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsNameFragment()).commit();
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        binding.tvType.setText(getArguments().getString("type_cancer"));

        binding.btnBack.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new TypesOfCancerFragment()).commit();
        });

        binding.btnSend.setOnClickListener(view ->  {
            if (selectedImage != null) {
                Bundle b = new Bundle();
                b.putString("type_cancer", getArguments().getString("type_cancer"));
                b.putInt("type_cancer_number", getArguments().getInt("type_cancer_number"));
                b.putString("selectImage", selectedImage.toString());
                TypeOfCancerResultFragment typeOfCancerResultFragment = new TypeOfCancerResultFragment();
                typeOfCancerResultFragment.setArguments(b);
                getFragmentManager().beginTransaction().add(R.id.MA, typeOfCancerResultFragment).commit();
            } else {
                Toast.makeText(getActivity(), R.string.ex_fill_img, Toast.LENGTH_SHORT).show();
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
                    selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imvDiagnosis.setImageBitmap(bitmap);
                }
        }
    }
}