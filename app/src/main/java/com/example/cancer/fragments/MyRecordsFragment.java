package com.example.cancer.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cancer.R;
import com.example.cancer.activities.TypesOfCancerActivity;
import com.example.cancer.data.Word;
import com.example.cancer.data.WordDao;
import com.example.cancer.data.WordListAdapter;
import com.example.cancer.data.WordRoomDatabase;
import com.example.cancer.databinding.FragmentMyRecordsBinding;

import java.util.ArrayList;


public class MyRecordsFragment extends Fragment {

    FragmentMyRecordsBinding binding;

    WordRoomDatabase wordRoomDatabase;
    ArrayList<Word> data;
    WordListAdapter wordAdapter;
    WordDao wd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyRecordsBinding.inflate(inflater, container, false);

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

        binding.btnSearch.setOnClickListener(view ->  {
            if (!binding.etQuestion.getText().toString().isEmpty()) {
                Thread thread1 = new Thread(new AnotherRunnable1());
                thread1.start();
            } else {
                Thread thread2 = new Thread(new AnotherRunnable());
                thread2.start();
            }
        });

        return binding.getRoot();
    }

    class AnotherRunnable1 implements Runnable {
        @Override
        public void run() {
            String s = binding.etQuestion.getText().toString();
            wd = wordRoomDatabase.getWordDao();
            data = (ArrayList<Word>) wd.loadAllByName(s);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WordListAdapter.OnWordClickListener wordClickListener = new WordListAdapter.OnWordClickListener() {
                        @Override
                        public void onWordClick(Word word, int position) {
                            Bundle b = new Bundle();
                            b.putLong("id_info", word.getId());
                            MyRecordFragment myRecordFragment = new MyRecordFragment();
                            myRecordFragment.setArguments(b);
                            getFragmentManager().beginTransaction().add(R.id.MA, myRecordFragment).commit();
                        }
                    };
                    wordAdapter = new WordListAdapter(getActivity(), data, wordClickListener);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.recyclerview.setAdapter(wordAdapter);
                }
            });
        }
    }

    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            wd = wordRoomDatabase.getWordDao();
            data = (ArrayList<Word>) wd.loadAll();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WordListAdapter.OnWordClickListener wordClickListener = new WordListAdapter.OnWordClickListener() {
                        @Override
                        public void onWordClick(Word word, int position) {
                            Bundle b = new Bundle();
                            b.putLong("id_info", word.getId());
                            MyRecordFragment myRecordFragment = new MyRecordFragment();
                            myRecordFragment.setArguments(b);
                            getFragmentManager().beginTransaction().add(R.id.MA, myRecordFragment).commit();
                        }
                    };
                    wordAdapter = new WordListAdapter(getActivity(), data, wordClickListener);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.recyclerview.setAdapter(wordAdapter);
                }
            });
        }
    }
}