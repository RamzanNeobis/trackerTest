package ru.startandroid.develop.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {


    private int numOfTabs = 2 ;


    public PagerAdapter(FragmentManager fm){
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FragmentInfo();
        }
        if(position == 1){
            return new FragmentInfo();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
