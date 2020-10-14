package com.cookandroid.windowairfresh;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class Main_SlideAdapter extends FragmentPagerAdapter {
    DatabaseManager databaseManager;
    public Main_SlideAdapter(FragmentManager fm, DatabaseManager dm){
        super(fm);
        databaseManager =  dm;
    }

    public ArrayList<WindowDetails> checklist = new ArrayList<>() ;

    @Override
    public Fragment getItem(int position) {
        if (databaseManager != null){
            checklist = databaseManager.getAll();
        }
        switch (position){
            case 0:
                return new Main_Fragment1();
            case 1:
                if (checklist.isEmpty()){ return new Main_Fragment3(); }
                else                    { return new Main_Fragment2(); }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
