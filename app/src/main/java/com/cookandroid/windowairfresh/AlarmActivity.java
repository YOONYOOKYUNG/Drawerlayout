package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {
    Calendar alarmCalendar;
    TextView st_time,st_time2;
    ImageView set_alarm, fin_alarm, backarrow;
    int alarmHour, alarmMin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        st_time = findViewById(R.id.st_time);
        st_time2 = findViewById(R.id.st_time2);

        final Switch switchbtn = findViewById(R.id.sb_use_listener);
        final Switch switchbtn2 = findViewById(R.id.sb_use_listener2);


        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchbtn.isChecked() == true){
                    createNotifi();
                    Toast.makeText(getApplicationContext(),"푸쉬 알림이 켜졌습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    switchbtn2.setChecked(false);
                    removeNotifi();
                    Toast.makeText(getApplicationContext(),"푸쉬 알림이 꺼졌습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchbtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    Toast.makeText(getApplicationContext(),"방해 금지 모드 설정이 켜졌습니다.",Toast.LENGTH_SHORT).show();

                }
                else{
                    removeNotifi();
                    Toast.makeText(getApplicationContext(),"방해 금지 모드 설정이 꺼졌습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        st_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchbtn2.isChecked() == true) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            alarmHour = hourOfDay;
                            alarmMin = minute;
                            setAlarm();
                        }
                    }, alarmHour, alarmMin, false);
                    Toast.makeText(getApplicationContext(),"방해 금지 모드 설정이 설정되었습니다.",Toast.LENGTH_SHORT).show();
                }else if(switchbtn2.isChecked()==false){
                    Toast.makeText(getApplicationContext(),"방해 금지 모드 설정이 꺼졌습니다.\n방해모드금지를 활성화 시켜주세요.",Toast.LENGTH_SHORT).show();
                }
            }

        });


        st_time2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                final int min = cal.get(Calendar.MINUTE);
                TimePickerDialog tpd;
                tpd = new TimePickerDialog(AlarmActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        st_time2.setText(hourOfDay + " 시 " + minute + " 분 ");

                    }

                }, hour, min, false);
                tpd.setTitle("시간 설정");
                tpd.show();
            }
        });

        set_alarm = findViewById(R.id.set_alarm);
        fin_alarm = findViewById(R.id.fin_alarm);


        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this,SetalarmActivity.class);
                startActivity(intent);
            }
        });

        fin_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this, FinalarmActivity.class);
                startActivity(intent);
            }
        });

        backarrow = findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    //알림 켰을때
    private void createNotifi(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.alaram);
        builder.setContentTitle("WAF 푸쉬 알림");
        builder.setContentText("푸쉬 알림이 커졌습니다.");

        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(new NotificationChannel("default","기본채널",NotificationManager.IMPORTANCE_DEFAULT));

        }
        notificationManager.notify(1,builder.build());
    }
    //알림 껐을때
    private void removeNotifi(){
        NotificationManagerCompat.from(this).cancel(1);
    }
    private void setAlarm(){

        alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(System.currentTimeMillis());
        alarmCalendar.set(Calendar.HOUR_OF_DAY,alarmHour);
        alarmCalendar.set(Calendar.MINUTE,alarmMin);
        alarmCalendar.set(Calendar.SECOND,0);
        //TimepickerDialog에서 설정한 시간을 알람 시간으로 설정

        if(alarmCalendar.before(Calendar.getInstance())) alarmCalendar.add(Calendar.DATE, 1);
        //알람 시간이 현재시간보다 빠를때 하루 뒤로 맞춤
       /* Intent alarmIntent = new Intent(getApplicationContext(),Alarm_Receiver.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmIntent.setAction(Alarm_Receiver.ACTION_RESTART_SERVICE);
        PendingIntent alarmCallPendingIntent = PendingIntent.getBroadcast(
                AlarmActivity.this, 0,alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    alarmCalendar.getTimeInMillis(),alarmCallPendingIntent);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(),alarmCallPendingIntent
            );
        }*/



    }
}

