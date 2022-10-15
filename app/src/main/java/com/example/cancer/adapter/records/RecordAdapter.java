package com.example.cancer.adapter.records;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cancer.fragments.MyRecordFragment;
import com.example.cancer.fragments.MyRecordInfoFragment;

public class RecordAdapter extends FragmentPagerAdapter {

    public RecordAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MyRecordInfoFragment();
            case 1:
                return new MyRecordFragment();
            default:
                return new MyRecordInfoFragment();
        }
    }
}