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
    //자동으로 창문이 닫히는 모드
    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    WindowListAdapter adapter = new WindowListAdapter();

    public void run() {
        //DatabaseManager 객체 얻어오기
        DatabaseManager databaseManager = DatabaseManager.getInstance(MainActivity.mContext);
        adapter = new WindowListAdapter();
        adapter.setDatabaseManager(databaseManager);

        while (true) {
            adapter.initialiseList();

            //Main에서 받아온 API 수치와 사용자 설정수치 가져와 비교
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

            //모드 구분 코드
            if (modestate) {
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
    public void allwindowclose(int num) {
        int windownumber = adapter.getCount();
        boolean windowsclosed = false;

        for (int i = 0; i < windownumber; i++) {
            if (adapter.listViewItemList.get(i).getState()) {
                ((MainActivity) MainActivity.mContext).closewindow(i);
                windowsclosed = true;
                adapter.listViewItemList.get(i).setState(false);
                ((MainActivity) MainActivity.mContext).dbcloseupdate(i);
            }
        }

        //창문이 자동으로 닫힐 시
        if (windowsclosed) {
            //메세지 보내기
            Message message = ((MainActivity) MainActivity.mContext).autohandler.obtainMessage(num);
            message.sendToTarget();

            //Timeline 추가
            DatabaseManager databaseManager = DatabaseManager.getInstance(MainActivity.mContext);
            adapter = new WindowListAdapter();
            adapter.setDatabaseManager(databaseManager);

            //현재 날짜, 시간 불러오기
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf_date = new SimpleDateFormat("MM/dd E");
            SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");

            String date = sdf_date.format(cal.getTime());
            String time = sdf_time.format(cal.getTime());

            String content;

            //창문이 자동으로 닫히는 경우에 따라 분류
            switch (num) {
                case 2:
                    //비가 와서 창문을 닫는 경우
                    content = "비가 와서 모든 창문을 닫았습니다.";
                    //알림 띄우기
                    closeRain();
                    break;
                case 3:
                    //사용자가 설정한 온도에 따라 창문을 닫는 경우
                    content = "사용자가 설정한 온도 값에 따라 모든 창문을 닫았습니다.";
                    //알림 띄우기
                    closeTemp();
                    break;
                case 4:
                    //사용자가 설정한 미세먼지 차이 수치에 따라 창문을 닫는 경우
                    content = "사용자가 설정한 미세먼지 수치에 따라 모든 창문을 닫았습니다.";
                    //알림 띄우기
                    closeDust();
                    break;
                default:
                    content = "";
            }

            //table에 넣기
            databaseManager.timeline_insert(date, time, content, "닫힘");
        }
    }

    //비가 와서 창문이 닫힐 때 띄우는 알림
    private void closeRain() {
        //알림 클릭 시 넘어가는 페이지
        Intent intent = new Intent(MainActivity.mContext, MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.mContext, 0, intent, 0);

        //Builder 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.mContext,
                "channel_rain");
        //아이콘, 제목, 내용, 탭 클릭 시 자동 제거
        builder.setSmallIcon(R.drawable.noti_rain)
                .setContentTitle("자동 개폐 알림")
                .setContentText("밖에 비가 와서 모든 창문을 닫았습니다.")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //Notification 관리자 객체를 Context로부터 소환
        NotificationManager manager =
                (NotificationManager) MainActivity.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Notification 채널 생성
            manager.createNotificationChannel(new NotificationChannel("channel_rain",
                    "rainclose", NotificationManager.IMPORTANCE_HIGH));
        }
        manager.notify(1, builder.build());
    }

    //설정한 온도에 따라 창문이 닫힐 때 띄우는 알림
    private void closeTemp() {
        //알림 클릭 시 넘어가는 페이지
        Intent intent2 = new Intent(MainActivity.mContext, MainActivity.class);
        intent2.setAction(Intent.ACTION_MAIN);
        intent2.addCategory(Intent.ACTION_MAIN);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent2 = PendingIntent.getActivity(MainActivity.mContext, 0, intent2, 0);

        //Builder 생성
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(MainActivity.mContext,
                "channel_temp");
        //아이콘, 제목, 내용, 탭 클릭 시 자동 제거
        builder2.setSmallIcon(R.drawable.noti_thermo)
                .setContentTitle("자동 개폐 알림")
                .setContentText("설정한 온도에 따라 모든 창문을 닫았습니다.")
                .setContentIntent(pIntent2)
                .setAutoCancel(true);

        //Notification 관리자 객체를 Context로부터 소환
        NotificationManager manager2 =
                (NotificationManager) MainActivity.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Notification 채널 생성
            manager2.createNotificationChannel(new NotificationChannel("channel_temp",
                    "tempclose", NotificationManager.IMPORTANCE_HIGH));
        }
        manager2.notify(2, builder2.build());
    }

    //미세먼지 수치에 의해 창문이 닫힐 때 띄우는 알림
    private void closeDust() {
        //알림 클릭 시 넘어가는 페이지
        Intent intent3 = new Intent(MainActivity.mContext, MainActivity.class);
        intent3.setAction(Intent.ACTION_MAIN);
        intent3.addCategory(Intent.ACTION_MAIN);
        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent3 = PendingIntent.getActivity(MainActivity.mContext, 0, intent3, 0);

        //Builder 생성
        NotificationCompat.Builder builder3 = new NotificationCompat.Builder(MainActivity.mContext,
                "channel_dust");
        //아이콘, 제목, 내용, 탭 클릭 시 자동 제거
        builder3.setSmallIcon(R.drawable.noti_dust)
                .setContentTitle("자동 개페 알림")
                .setContentText("설정한 미세먼지 수치에 따라 모든 창문을 닫았습니다.")
                .setContentIntent(pIntent3)
                .setAutoCancel(true);

        //Notification 관리자 객체를 Context로부터 소환
        NotificationManager manager3 =
                (NotificationManager) MainActivity.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Notification 채널 생성
            manager3.createNotificationChannel(new NotificationChannel("channel_dust",
                    "dustclose", NotificationManager.IMPORTANCE_HIGH));
        }
        manager3.notify(3, builder3.build());
    }
}