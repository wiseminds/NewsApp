package com.example.android.mynewsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Implements  PagerAdapter that represents each page as a {@link Fragment}
 * that is persistently kept in the fragment manager as long as the user
 * can return to the page.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;   //number of pages.

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new SportsNewsFragment();
            case 1: // Fragment # 0 - This will show SecondFragment different title
                return new TechNewsFragment();
            case 2: // Fragment # 1 - This will show ThirdFragment
                return new PoliticsNewsFragment();
        }
        return null;
    }

    /**
     * Return the number Fragment to hold.
     */
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
