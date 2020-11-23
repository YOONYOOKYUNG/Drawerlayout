package com.cookandroid.windowairfresh;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class Main_SlideAdapter extends FragmentStateAdapter {
    //메인 페이지 슬라이드 기능
    //FragmentStateAdapter : Fragment를 Viewpager2와 연동

    DatabaseManager databaseManager;

    //Main_SlideAdapter 생성자
    public Main_SlideAdapter(FragmentActivity fa, DatabaseManager dm) {
        super(fa);
        databaseManager = dm;
    }

    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public ArrayList<WindowDetails> checklist = new ArrayList<>();

    @Override
    public Fragment createFragment(int position) {
        if (databaseManager != null) {
            checklist = databaseManager.getAll();
        }
        switch (position) {
            case 0:
                //처음 보이는 페이지
                return new MainActivity_Fragment1();
            case 1:
                //왼쪽으로 스와이프 시 보이는 페이지
                if (checklist.isEmpty()) {
                    //등록된 창문이 없을 경우
                    return new MainActivity_Fragment3();
                } else {
                    //등록된 창문이 있을 경우
                    return new MainActivity_Fragment2();
                }
            default:
                return null;
        }
    }

    //메인에 보이는 페이지 개수
    @Override
    public int getItemCount() {
        return 2;
    }
}
