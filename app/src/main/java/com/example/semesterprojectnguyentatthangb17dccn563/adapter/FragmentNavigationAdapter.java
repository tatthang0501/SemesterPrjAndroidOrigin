package com.example.semesterprojectnguyentatthangb17dccn563.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.semesterprojectnguyentatthangb17dccn563.fragment.IncomeFragment;
import com.example.semesterprojectnguyentatthangb17dccn563.fragment.MoreFragment;
import com.example.semesterprojectnguyentatthangb17dccn563.fragment.NewFragment;
import com.example.semesterprojectnguyentatthangb17dccn563.fragment.SpendingFragment;

public class FragmentNavigationAdapter extends FragmentStatePagerAdapter {
    private int pageNum = 4;
    public FragmentNavigationAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new SpendingFragment();
            case 1: return new IncomeFragment();
            case 2: return new NewFragment();
            case 3: return new MoreFragment();
            default: return new SpendingFragment();
        }
    }

    @Override
    public int getCount() {
        return pageNum;
    }
}
