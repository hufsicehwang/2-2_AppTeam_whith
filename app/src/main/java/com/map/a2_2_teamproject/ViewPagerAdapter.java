package com.map.a2_2_teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext;
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new BlankFragment1());
        items.add(new BlankFragment2());
        items.add(new BlankFragment3());

        itext = new ArrayList<String>();

        // tab 이름 지정정
        itext.add("음식점");
        itext.add("카페");
        itext.add("명소");
    }

    @Nullable
    @Override // ctrl + o 를 눌러 오버라이딩
    public CharSequence getPageTitle(int position) {
        return itext.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}

