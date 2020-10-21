package com.cookandroid.windowairfresh;

import android.content.SharedPreferences;

import android.os.Message;
import android.util.Log;

import java.util.ArrayList;


public class AutoClose extends Thread {
    WindowListAdapter adapter = new WindowListAdapter();

    public void run() {
        DatabaseManager databaseManager = DatabaseManager.getInstance(MainActivity.mContext);
        adapter = new WindowListAdapter();
        adapter.setDatabaseManager(databaseManager);

        while(true) {
            adapter.initialiseList();
            SharedPreferences sf = (MainActivity.mContext).getSharedPreferences("autoset", 0);
            int hottemp = Integer.parseInt(sf.getString("high_temp", "30"));
            int coldtemp = Integer.parseInt(sf.getString("low_temp", "0"));
            int comparedust = Integer.parseInt(sf.getString("compare_dust", "20"));
            Boolean modestate = sf.getBoolean("modestate", false);
            float insidedust = ((MainActivity) MainActivity.mContext).insidedust;
            float outsidedust = ((MainActivity) MainActivity.mContext).outsidedust;
            float outsidetemp = ((MainActivity) MainActivity.mContext).outsidetemp;
            int outsiderain = ((MainActivity) MainActivity.mContext).outsiderain;
            float dustresult = outsidedust - insidedust;
            if (modestate){
                if (outsiderain != 0) {
                Log.d("자동모드", "비와서 창문 닫았습니다");
                allwindowclose(2);
            } else if (outsidetemp < coldtemp || outsidetemp > hottemp) {
                Log.d("자동모드", "자동모드:온도 때문에 창문 닫았습니다");
                allwindowclose(3);
            } else if (dustresult > comparedust) {
                Log.d("자동모드", "자동모드:미세먼지 때문에 창문 닫았습니다.");
                allwindowclose(4);
            }
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
        }
        }
    }

    //창문 자동설정 - 모두닫기
    public void allwindowclose(int num)
    {
        int windownumber = adapter.getCount();
        boolean windowsclosed = false;

        for (int i = 0; i < windownumber; i++) {
            if(adapter.listViewItemList.get(i).getState()){
                ((MainActivity)MainActivity.mContext).closewindow(i);
                windowsclosed = true;
                adapter.listViewItemList.get(i).setState(false);
                ((MainActivity)MainActivity.mContext).dbcloseupdate(i);}
        }

        if (windowsclosed){
            Message message = ((MainActivity)MainActivity.mContext).autohandler.obtainMessage(num);
            message.sendToTarget();
        }

    }

}