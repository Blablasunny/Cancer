package com.example.cancer.adapter.edit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cancer.fragments.EditRecordFragment;
import com.example.cancer.fragments.EditRecordInfoFragment;

public class EditAdapter extends FragmentPagerAdapter {

    public EditAdapter(@NonNull FragmentManager fm) {
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
                return new EditRecordInfoFragment();
            case 1:
                return new EditRecordFragment();
            default:
                return new EditRecordInfoFragment();
        }
    }
}
