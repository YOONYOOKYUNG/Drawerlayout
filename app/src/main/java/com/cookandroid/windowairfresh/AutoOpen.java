package com.cookandroid.windowairfresh;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

public class AutoOpen extends Thread {
    WindowListAdapter adapter = new WindowListAdapter();
    public void run() {
        ArrayList<WindowDetails> checklist = new ArrayList<>() ;
        float insidedust=((MainActivity)MainActivity.mContext).insidedust;
        float outsidedust=((MainActivity)MainActivity.mContext).outsidedust;
        float outsidetemp=((MainActivity)MainActivity.mContext).outsidetemp;
        int outsiderain=((MainActivity)MainActivity.mContext).outsiderain;
        DatabaseManager databaseManager = DatabaseManager.getInstance(MainActivity.mContext);
        adapter = new WindowListAdapter();
        adapter.setDatabaseManager(databaseManager);
        adapter.initialiseList();
        while(true){
            if (databaseManager != null){
                checklist = databaseManager.getAll();
            }
            if (!checklist.isEmpty()) {
                SharedPreferences sf = MainActivity.mContext.getSharedPreferences("autoset", 0);
                Boolean modestate = sf.getBoolean("modestate",false);
                int hottemp =  Integer.parseInt(sf.getString("High_temp","30"));
                int coldtemp= Integer.parseInt(sf.getString("Low_temp","0"));
                int comparedust = Integer.parseInt(sf.getString("Compare_dust","20"));
                if(modestate){
                    if (!((MainActivity)MainActivity.mContext).btsocketstate)
                    { ((MainActivity)MainActivity.mContext).opensocket();}
                    float dustresult = outsidedust-insidedust;
                    int windownumber = adapter.getCount();
                    if(outsiderain==0&& coldtemp<outsidetemp && outsidetemp<hottemp&&dustresult < -comparedust)
                    {
                        Log.d("자동모드", "자동모드:창문 열었어요");
                        boolean windowsOpened = false;
                        for(int i=0;i<windownumber;i++) {
                            if(checklist.get(i).getState()==false)
                            {
                                ((MainActivity)MainActivity.mContext).openwindow(i);
                                windowsOpened = true;
                                adapter.listViewItemList.get(i).setState(true);
                                if (databaseManager != null) {
                                    ContentValues updateRowValue = new ContentValues();
                                    updateRowValue.put("state", "true");
                                    databaseManager.update(updateRowValue,adapter.listViewItemList.get(i).getName());
                                }
                            }
                        }
                        if (windowsOpened){
                            Message message = ((MainActivity)MainActivity.mContext).autohandler.obtainMessage(1);
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
}