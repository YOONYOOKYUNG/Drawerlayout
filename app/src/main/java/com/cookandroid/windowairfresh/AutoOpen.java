package com.cookandroid.windowairfresh;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Message;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

                    //Timeline 추가
                    //현재 날짜, 시간 불러오기
                    Calendar cal =Calendar.getInstance();
                    SimpleDateFormat sdf_date = new SimpleDateFormat("MM/dd E");
                    SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");

                    String date = sdf_date.format(cal.getTime());
                    String time = sdf_time.format(cal.getTime());
                    String content = "사용자가 설정한 수치에 따라 창문이 열렸습니다.";

                    //table에 넣기
                    databaseManager.timeline_insert(date, time, content, "열림");

                    openNoty();

                }
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
        }
    }
    private void openNoty(){
        Intent intent = new Intent(MainActivity.mContext, MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.mContext, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.mContext, "channel_open");
        builder.setSmallIcon(R.drawable.window_open)
                .setContentTitle("자동 개폐 알림")
                .setContentText("내부 미세먼지 수치가 외부보다 높아 모든 창문을 열였습니다.")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) MainActivity.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel("channel_open",
                    "자동 열림 ", NotificationManager.IMPORTANCE_HIGH));
        }
        manager.notify(1, builder.build());
    }
}