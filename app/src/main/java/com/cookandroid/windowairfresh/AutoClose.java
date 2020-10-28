package com.cookandroid.windowairfresh;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Message;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AutoClose extends Thread {
    WindowListAdapter adapter = new WindowListAdapter();

    public void run() {
        DatabaseManager databaseManager = DatabaseManager.getInstance(MainActivity.mContext);
        adapter = new WindowListAdapter();
        adapter.setDatabaseManager(databaseManager);

        while(true) {
            adapter.initialiseList();

            // API와 사용자 설정수치 가져와 비교.
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
                Log.d("자동모드", "비가 와서 창문 닫았습니다");
                allwindowclose(2);

            } else if (outsidetemp < coldtemp || outsidetemp > hottemp) {
                Log.d("자동모드", "자동모드:온도 때문에 창문 닫았습니다");
                allwindowclose(3);

            } else if (dustresult > comparedust) {
                Log.d("자동모드", "자동모드:미세먼지 때문에 창문 닫았습니다.");
                allwindowclose(4);
            }
            try {
                Thread.sleep(1000);
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

            //Timeline 추가
            DatabaseManager databaseManager = DatabaseManager.getInstance(MainActivity.mContext);
            adapter = new WindowListAdapter();
            adapter.setDatabaseManager(databaseManager);

            //현재 날짜, 시간 불러오기
            Calendar cal =Calendar.getInstance();
            SimpleDateFormat sdf_date = new SimpleDateFormat("MM/dd E");
            SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");

            String date = sdf_date.format(cal.getTime());
            String time = sdf_time.format(cal.getTime());

            String content;

            //창문이 자동으로 닫히는 경우에 따라 분류류
            switch (num){
                case 2:
                    content = "비가 와서 모든 창문을 닫았습니다.";
                    closeRain();
                    break;
                case 3:
                    content = "사용자가 설정한 온도 값에 따라 모든 창문을 닫았습니다.";
                    closeTemp();
                    break;
                case 4:
                    content = "사용자가 설정한 미세먼지 수치에 따라 모든 창문을 닫았습니다.";
                    closeDust();
                    break;
                default:
                    content = "";
            }

           //table에 넣기
            databaseManager.timeline_insert(date, time, content, "닫힘");
        }
    }
    private void closeRain(){
        Intent intent = new Intent(MainActivity.mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.mContext, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.mContext,
                "channel_rain");
        builder.setSmallIcon(R.drawable.noti_rain)
                .setContentTitle("자동 개폐 알림")
                .setContentText("밖에 비가 와서 모든 창문을 닫았습니다.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager manager =
                (NotificationManager) MainActivity.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel("channel_rain",
                    "rainclose", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1, builder.build());
    }

    private void closeTemp(){
        Intent intent2 = new Intent(MainActivity.mContext, MainActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent2 = PendingIntent.getActivity(MainActivity.mContext, 0, intent2, 0);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(MainActivity.mContext,
                "channel_temp");
        builder2.setSmallIcon(R.drawable.noti_thermo)
                .setContentTitle("자동 개폐 알림")
                .setContentText("설정한 온도에 따라 모든 창문을 닫았습니다.")
                .setContentIntent(pIntent2)
                .setAutoCancel(true);

        NotificationManager manager2 =
                (NotificationManager) MainActivity.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            manager2.createNotificationChannel(new NotificationChannel("channel_temp",
                    "tempclose", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager2.notify(2, builder2.build());
    }

    private void closeDust(){
        Intent intent3 = new Intent(MainActivity.mContext, MainActivity.class);
        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent3 = PendingIntent.getActivity(MainActivity.mContext, 0, intent3, 0);

        NotificationCompat.Builder builder3 = new NotificationCompat.Builder(MainActivity.mContext,
                "channel_dust");
        builder3.setSmallIcon(R.drawable.noti_dust)
                .setContentTitle("자동 개페 알림")
                .setContentText("설정한 미세먼지 수치에 따라 모든 창문을 닫았습니다.")
                .setContentIntent(pIntent3)
                .setAutoCancel(true);

        NotificationManager manager3 =
                (NotificationManager) MainActivity.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            manager3.createNotificationChannel(new NotificationChannel("channel_dust",
                    "dustclose", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager3.notify(3,builder3.build());
    }
}