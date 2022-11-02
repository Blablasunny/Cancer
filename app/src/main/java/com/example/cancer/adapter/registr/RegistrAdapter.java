package com.example.cancer.adapter.registr;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cancer.fragments.RegistFragment;
import com.example.cancer.fragments.RegistrFragment;
import com.example.cancer.fragments.RegistrInfoFragment;

public class RegistrAdapter extends FragmentPagerAdapter {

    public RegistrAdapter(@NonNull FragmentManager fm) {
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
                return new RegistrInfoFragment();
            case 1:
                return new RegistrFragment();
            default:
                return new RegistrInfoFragment();
        }
    }
}
