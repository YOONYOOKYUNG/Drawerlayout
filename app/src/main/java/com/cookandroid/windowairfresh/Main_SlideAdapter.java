package com.cookandroid.windowairfresh;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class Main_SlideAdapter extends FragmentStateAdapter {
    DatabaseManager databaseManager;
    public Main_SlideAdapter(FragmentActivity fa, DatabaseManager dm){
        super(fa);
        databaseManager =  dm;
    }

    public ArrayList<WindowDetails> checklist = new ArrayList<>() ;

    @Override
    public Fragment createFragment(int position) {
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
    public int getItemCount() {
        return 2;
    }
}
