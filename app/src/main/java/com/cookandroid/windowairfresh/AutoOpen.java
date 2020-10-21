package com.cookandroid.windowairfresh;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;

public class AutoOpen extends Thread {
    WindowListAdapter adapter = new WindowListAdapter();

    public void run() {
        DatabaseManager databaseManager = DatabaseManager.getInstance(MainActivity.mContext);
        while(true) {
            SharedPreferences sf = (MainActivity.mContext).getSharedPreferences("autoset", 0);
            int hottemp =  Integer.parseInt(sf.getString("high_temp","30"));
            int coldtemp= Integer.parseInt(sf.getString("low_temp","0"));
            int comparedust = Integer.parseInt(sf.getString("compare_dust","20"));
            Boolean modestate = sf.getBoolean("modestate", false);
            float insidedust=((MainActivity)MainActivity.mContext).insidedust;
            float outsidedust=((MainActivity)MainActivity.mContext).outsidedust;
            float outsidetemp=((MainActivity)MainActivity.mContext).outsidetemp;
            int outsiderain=((MainActivity)MainActivity.mContext).outsiderain;
            adapter = new WindowListAdapter();
            adapter.setDatabaseManager(databaseManager);
            adapter.initialiseList();
            float dustresult = outsidedust-insidedust;
            int windownumber = adapter.getCount();
            if (modestate){
            if (outsiderain == 0 && coldtemp<outsidetemp && outsidetemp< hottemp && dustresult+comparedust<0) {
                Log.d("자동모드", "자동모드:창문 열었어요");
                boolean windowsOpened = false;
                for (int i = 0; i < windownumber; i++) {
                    {
                        if(!adapter.listViewItemList.get(i).getState()){
                        ((MainActivity) MainActivity.mContext).openwindow(i);
                        windowsOpened = true;
                        adapter.listViewItemList.get(i).setState(true);
                        if (databaseManager != null) {
                            ContentValues updateRowValue = new ContentValues();
                            updateRowValue.put("state", "true");
                            databaseManager.update(updateRowValue, adapter.listViewItemList.get(i).getName());
                        }
                       }
                    }
                }
                if (windowsOpened) {
                    Message message = ((MainActivity) MainActivity.mContext).autohandler.obtainMessage(1);
                    message.sendToTarget();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
        }
    }
}