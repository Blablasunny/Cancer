package com.example.cancer.adapter.create;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cancer.fragments.CreatingRecordFragment;
import com.example.cancer.fragments.CreatingRecordInfoFragment;

public class CreateAdapter extends FragmentPagerAdapter {

    public CreateAdapter(@NonNull FragmentManager fm) {
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
                return new CreatingRecordInfoFragment();
            case 1:
                return new CreatingRecordFragment();
            default:
                return new CreatingRecordInfoFragment();
        }
    }
}
