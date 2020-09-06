package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    TextView st_time,st_time2;
    ImageView set_alarm, fin_alarm, backarrow;
    NotificationManager nm;
    Notification.Builder builder;
    PendingIntent fpi;
    Intent push;

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
                    removeNotifi();
                    Toast.makeText(getApplicationContext(),"푸쉬 알림이 꺼졌습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        st_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                final int min = cal.get(Calendar.MINUTE);
                TimePickerDialog tpd;
                tpd = new TimePickerDialog(AlarmActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        st_time.setText(hourOfDay + " 시 " + minute + " 분 ");

                    }

                }, hour, min, false);
                tpd.setTitle("시간 설정");
                tpd.show();
            }
        });

        st_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                final int min = cal.get(Calendar.MINUTE);
                TimePickerDialog tpd;
                tpd = new TimePickerDialog(AlarmActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
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
}

