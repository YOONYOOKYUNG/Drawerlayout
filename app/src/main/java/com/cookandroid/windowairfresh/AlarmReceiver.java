package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AlarmReceiver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmspinner);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmspinner);
        final TimePicker timePicker;
        Button btn_send;

        timePicker = findViewById(R.id.timepicker);
        btn_send = findViewById(R.id.btn_send);
        //앞서 설정한 시간을 보여주고 설정값이 없으면 디폴트값은 현재시간으로 지정.
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm",MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotify = new GregorianCalendar();
        nextNotify.setTimeInMillis(millis);

        Date nextDate = nextNotify.getTime();
        String txt_date = new SimpleDateFormat("hh시 mm분", Locale.getDefault()).format(nextDate);

        Toast.makeText(getApplicationContext(),txt_date+"으로 설정 되었습니다.",Toast.LENGTH_SHORT).show();

        //이전 설정값으로 timepicker 초기화
        Date currentTime = nextNotify.getTime();
        final SimpleDateFormat HourFormat = new SimpleDateFormat("hh",Locale.getDefault());
        final SimpleDateFormat MinFormat = new SimpleDateFormat("mm",Locale.getDefault());

        final int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        final int pre_minute = Integer.parseInt(MinFormat.format(currentTime));

        if (Build.VERSION.SDK_INT >= 23 ){
            timePicker.setHour(pre_hour);
            timePicker.setMinute(pre_minute);
        }
        else{
            timePicker.setCurrentHour(pre_hour);
            timePicker.setCurrentMinute(pre_minute);
        }

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour,hour_24,minute;
                final String ampm;
                if(Build.VERSION.SDK_INT>=23){
                    hour_24 = timePicker.getHour();
                    minute = timePicker.getMinute();
                }
                else{
                    hour_24 = timePicker.getCurrentHour();
                    minute = timePicker.getChildCount();
                }
                if(hour_24>12){
                    ampm = "오후 ";
                    hour = hour_24 - 12;
                }else{
                    hour = hour_24;
                    ampm = "오전 ";
                }
                //현재 지정된 시간으로 알림 시간 설정
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY,hour_24);
                calendar.set(Calendar.MINUTE,minute);

                //이미 지난 시간으로 지정했다면 다음 날 같은 시간으로 설정함.
                if(calendar.before(Calendar.getInstance())){
                    calendar.add(Calendar.DATE,1);
                }
                Date currentTime = calendar.getTime();
                String txt_date = new SimpleDateFormat("hh시 mm분",Locale.getDefault()).format(currentTime);

                Toast.makeText(getApplicationContext(), ampm + txt_date+"으로 알람이 설정되었습니다.",Toast.LENGTH_SHORT).show();

                //preference에 설정한 값 저장
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm",MODE_PRIVATE).edit();

                editor.putLong("nextNotifyTime",(long)calendar.getTimeInMillis());
                editor.apply();

                dailyNotification(calendar);

                Intent intent = getIntent();
                String ampmtime = ampm + txt_date;
                intent.putExtra("time",ampmtime);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    void dailyNotification(Calendar calendar){
        Boolean dailyNotify = true;
        //무조건 알림을 허용

        PackageManager packageManager = this.getPackageManager();
        ComponentName receiver = new ComponentName(this,DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

//사용자가 매일 알람을 허용했다면
        if (dailyNotify) {
            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        }
        // 부팅 후 실행되는 리시버 사용가능하게 설정
        packageManager.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
}