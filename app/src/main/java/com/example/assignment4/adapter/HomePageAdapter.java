package com.example.assignment4.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentsList;
    private List<String> mTabNamesList;

    public HomePageAdapter(FragmentManager fm, List<Fragment> fragmentsList, ArrayList<String> tabNamesList) {
        super(fm);
        this.mFragmentsList=fragmentsList;
        this.mTabNamesList=tabNamesList;
    }

    @Override
    public Fragment getItem(int i) {
        return this.mFragmentsList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNamesList.get(position);
    }
}