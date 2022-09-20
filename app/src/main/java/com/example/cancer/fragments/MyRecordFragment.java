package com.example.cancer.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.R;
import com.example.cancer.activities.TypesOfCancerActivity;
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

    private StorageReference mStorageRef;

    private long id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyRecordBinding.inflate(inflater, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnEdit.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new CreatingRecordFragment()).commit();
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), TypesOfCancerActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        wordRoomDatabase = WordRoomDatabase.getInstance(getActivity());

        Thread thread=new Thread(new AnotherRunnable());
        thread.start();


        binding.btnEditWrite.setOnClickListener(view ->  {
            id = getArguments().getLong("id_info");
            Bundle b = new Bundle();
            b.putLong("id_info", id);
            EditRecordFragment editRecordFragment = new EditRecordFragment();
            editRecordFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.MA, editRecordFragment).commit();
        });

        return binding.getRoot();
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
                    binding.tvName.setText(str_n);
                    binding.tvInfo.setText(str_in);
                    if (str_im != null) {
                        if (!str_im.equals("")) {
                            Picasso.get().load(str_im).into(binding.imvWrite);
                        }
                    }
                }
            });
        }
    }
}