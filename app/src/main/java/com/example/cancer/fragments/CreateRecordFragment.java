package com.example.cancer.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.adapter.create.CreateAdapter;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.databinding.FragmentCreateRecordBinding;
import com.example.cancer.models.user.UserInfo;
import com.example.cancer.models.write.Write;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateRecordFragment extends Fragment {

    FragmentCreateRecordBinding binding;

    ViewPager viewPager;
    CreateAdapter adapter;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    private Uri selectedImage;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    private String patientName, patientSurname, patientPatronymic, patientPhone, day, month, year, name, info, image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateRecordBinding.inflate(inflater, container, false);

        viewPager = binding.viewPager;
        adapter = new CreateAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("flag_create_1", "0");
        editor.putString("flag_create_2", "0");
        editor.commit();

        mDatabase = FirebaseDatabase.getInstance().getReference("write");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnScroll.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsNameFragment()).commit();
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new TypesOfCancerFragment()).commit();
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        binding.btnApprove.setOnClickListener(view -> {
            SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES , MODE_PRIVATE);
            if (prefs.getString("flag_create_1", null).equals("1") && prefs.getString("flag_create_2", null).equals("1")) {
                name = prefs.getString("record_name", null);
                info = prefs.getString("info", null);
                image = prefs.getString("image", null);
                patientName =  prefs.getString("patient_name", null);
                patientSurname = prefs.getString("patient_surname", null);
                patientPatronymic = prefs.getString("patient_patronymic", null);
                patientPhone = prefs.getString("patient_phone", null);
                day = prefs.getString("day", null);
                month = prefs.getString("month", null);
                year = prefs.getString("year", null);
                if (!image.isEmpty()) {
                    selectedImage = Uri.parse(image);
                } else {
                    selectedImage = null;
                }
                wordRoomDatabase = WordRoomDatabase.getInstance(getActivity());
                Thread thread=new Thread(new AnotherRunnable());
                thread.start();
            } else {
                Toast.makeText(getActivity(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
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
                Word word = new Word(UserInfo.id, name, info, image,
                        patientName, patientSurname, patientPatronymic, patientPhone, day, month, year);
                wd.insert(word);
            } else {
                Word word = new Word(UserInfo.id, name, info, "",
                        patientName, patientSurname, patientPatronymic, patientPhone, day, month, year);
                wd.insert(word);
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!image.isEmpty()) {
                        StorageReference fileRef = mStorageRef.child(System.currentTimeMillis()
                                + "." + getFileExtension(selectedImage));
                        UploadTask uploadTask = fileRef.putFile(selectedImage);

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
                                        Write write = new Write(UserInfo.email, name, info, uri.toString(),
                                                patientName, patientSurname, patientPatronymic, patientPhone,
                                                day, month, year, UserInfo.id);
                                        mDatabase.push().setValue(write);
                                    }
                                });
                                Toast.makeText(getActivity(), R.string.data_add, Toast.LENGTH_SHORT).show();
                                getFragmentManager().beginTransaction().add(R.id.MA, new CreateRecordFragment()).commit();
                            }
                        });
                    } else {
                        Write write = new Write(UserInfo.email, name, info, "",
                                patientName, patientSurname, patientPatronymic, patientPhone,
                                day, month, year, UserInfo.id);
                        mDatabase.push().setValue(write);
                        Toast.makeText(getActivity(), R.string.data_add, Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().add(R.id.MA, new CreateRecordFragment()).commit();
                    }
                }
            });
        }
    }
}