package com.example.cancer.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.R;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.databinding.FragmentMyRecordBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyRecordFragment extends Fragment {

    FragmentMyRecordBinding binding;

    WordRoomDatabase wordRoomDatabase;
    WordDao wd;

    private long id;

    private StorageReference mStorageRef;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyRecordBinding.inflate(inflater, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        wordRoomDatabase = WordRoomDatabase.getInstance(getActivity());

        Thread thread=new Thread(new AnotherRunnable());
        thread.start();

        return binding.getRoot();
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
                    binding.tvName.setText(strName);
                    binding.tvInfo.setText(strInfo);
                    if (strImage != null) {
                        if (!strImage.equals("")) {
                            Picasso.get().load(strImage).into(binding.imvWrite);
                        }
                    }
                }
            });
        }
    }
}