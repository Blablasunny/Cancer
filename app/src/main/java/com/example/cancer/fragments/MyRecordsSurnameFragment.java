package com.example.cancer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cancer.R;
import com.example.cancer.data.words.Word;
import com.example.cancer.data.words.WordDao;
import com.example.cancer.data.words.WordListAdapter;
import com.example.cancer.data.words.WordRoomDatabase;
import com.example.cancer.databinding.FragmentMyRecordsSurnameBinding;

import java.util.ArrayList;

public class MyRecordsSurnameFragment extends Fragment {

    FragmentMyRecordsSurnameBinding binding;

    WordRoomDatabase wordRoomDatabase;
    ArrayList<Word> data;
    WordListAdapter wordAdapter;
    WordDao wd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyRecordsSurnameBinding.inflate(inflater, container, false);

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnEdit.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new CreateRecordFragment()).commit();
        });

        binding.btnDiagnosis.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new TypesOfCancerFragment()).commit();
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        binding.btnName.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsNameFragment()).commit();
        });

        binding.btnDate.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsDateFragment()).commit();
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
            data = (ArrayList<Word>) wd.loadAllByPatientSurname(s);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WordListAdapter.OnWordClickListener wordClickListener = new WordListAdapter.OnWordClickListener() {
                        @Override
                        public void onWordClick(Word word, int position) {
                            Bundle b = new Bundle();
                            b.putLong("id_info", word.getId());
                            MyRecFragment myRecFragment = new MyRecFragment();
                            myRecFragment.setArguments(b);
                            getFragmentManager().beginTransaction().add(R.id.MA, myRecFragment).commit();
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
                            MyRecFragment myRecFragment = new MyRecFragment();
                            myRecFragment.setArguments(b);
                            getFragmentManager().beginTransaction().add(R.id.MA, myRecFragment).commit();
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