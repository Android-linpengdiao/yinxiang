package com.yinxiang.adapter;

import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PagerAdapter extends FragmentPagerAdapter {

    private LinkedHashMap<String, Fragment> mFragments = new LinkedHashMap<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Set<Map.Entry<String, Fragment>> entrySet = mFragments.entrySet();
        int i = 0;
        for (Map.Entry<String, Fragment> entry : entrySet) {
            if (i == position) {
                return entry.getValue();
            }
            i++;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Set<Map.Entry<String, Fragment>> entrySet = mFragments.entrySet();
        int i = 0;
        for (Map.Entry<String, Fragment> entry : entrySet) {
            if (i == position) {
                return entry.getKey();
            }
            i++;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    public void addFragment(String title, Fragment fragment) {
        mFragments.put(title, fragment);
    }
}