package com.cookandroid.windowairfresh;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Main_SlideAdapter extends FragmentPagerAdapter {

    public Main_SlideAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Main_Fragment1();
            case 1:
                return new Main_Fragment2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
