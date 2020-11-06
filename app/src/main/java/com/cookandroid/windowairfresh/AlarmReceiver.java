package com.cookandroid.windowairfresh;

import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, AlarmSpinnerActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default");

        //오레오 api 26 이상에서는 채널 필요
        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
            builder.setSmallIcon(R.drawable.alaram);

            String channelName = "매일 알람 채널";
            String description = "매일 정해진 시간에 알람합니다.";

            int importance = NotificationManager.IMPORTANCE_HIGH;//소리랑 알림메세지 같이 함

            NotificationChannel channel = new NotificationChannel("default",channelName,importance);

            channel.setDescription(description);

            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
                //노티피케이션 채널을 시스템에 등록
            }
        }else builder.setSmallIcon(R.drawable.alaram);


        String pty,dust;
        SharedPreferences pf1 = context.getSharedPreferences("fragment2",context.MODE_PRIVATE);

        dust=pf1.getString("dust","15");
        pty=pf1.getString("pty","0");
            if (Integer.parseInt(dust) < 30) {
                builder.setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("Time to watch")
                        .setContentTitle("WAF")
                        .setSmallIcon(R.drawable.main_popup_colorgood)
                        .setContentText("미세먼지가 좋으니 외부 활동을 하셔도 좋습니다.")
                        .setContentInfo("INFO")
                        .setContentIntent(pendingIntent);
            } else if (Integer.parseInt(dust) < 50) {
                builder.setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("Time to watch")
                        .setContentTitle("WAF")
                        .setSmallIcon(R.drawable.main_popup_colorsoso)
                        .setContentText("미세먼지가 괜찮으나 기관지가 약한분들은 조심하세요.")
                        .setContentInfo("INFO")
                        .setContentIntent(pendingIntent);
            } else if (Integer.parseInt(dust) >= 80) {
                builder.setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("Time to watch")
                        .setContentTitle("WAF")
                        .setSmallIcon(R.drawable.main_popup_colorbad)
                        .setContentText("미세먼지가 나쁘니 외출을 삼가해주세요.")
                        .setContentInfo("INFO")
                        .setContentIntent(pendingIntent);
            }else if(Integer.parseInt(pty)==1){
                builder.setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("Time to watch")
                        .setContentTitle("WAF")
                        .setSmallIcon(R.drawable.window_close_wh)
                        .setContentText("비가 오니까 창문을 닫아주세요. ")
                        .setContentInfo("INFO")
                        .setContentIntent(pendingIntent);
            }

        if(notificationManager != null){
            notificationManager.notify(1234,builder.build()); //노티피케이션 동작 시킴.
            Calendar nextNotifyTime = Calendar.getInstance();

            //내일 같은 시간으로 알림 설정
            nextNotifyTime.add(Calendar.DATE,1);

            //preference에 설정한 값 저장
            SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm",MODE_PRIVATE).edit();
            editor.putLong("nextNotifiyTime", nextNotifyTime.getTimeInMillis());
            editor.apply();

            Date currentDateTime = nextNotifyTime.getTime();
            String date_txt = new SimpleDateFormat("hh시 mm분", Locale.getDefault()).format(currentDateTime);

            //Toast.makeText(context.getApplicationContext(),"다음 알림은"+date_txt+"으로 설정되었습니다.",Toast.LENGTH_SHORT).show();
        }
    }


}
