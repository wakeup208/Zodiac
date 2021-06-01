package com.wakeup.zodiac.model;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Pager extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList();
    private final List<String> mFragmentTitleList = new ArrayList();

    public Pager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }
}
