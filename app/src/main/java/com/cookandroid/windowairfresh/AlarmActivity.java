package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    TextView st_time;
    ImageView set_alarm, backarrow;
    private static int ONE_MINUTE = 5626;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
       // new AlarmHATT(getApplication()).Alarm();

        st_time = findViewById(R.id.st_time);

        final Switch switchbtn = findViewById(R.id.sb_use_listener);
        final Switch switchbtn2 = findViewById(R.id.sb_use_listener2);

        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchbtn.isChecked()==true){
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
                    Toast.makeText(getApplicationContext(),"매 시간 알림을 설정합니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    switchbtn2.setChecked(false);
                    Toast.makeText(getApplicationContext(),"매 시간 알림을 해제합니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        st_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchbtn2.isChecked() == true) {
                    Calendar cal = Calendar.getInstance();
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    final int min = cal.get(Calendar.MINUTE);
                    TimePickerDialog tpd;
                    tpd = new TimePickerDialog(AlarmActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        }

                    }, hour, min, false);
                    tpd.setTitle("시간 설정");
                    tpd.show();
                    Toast.makeText(getApplicationContext(),"설정된 시간에 알람이 켜집니다.",Toast.LENGTH_SHORT).show();

                }else if(switchbtn2.isChecked()==false){
                    Toast.makeText(getApplicationContext(),"방해 금지 모드 설정이 꺼졌습니다.\n방해모드금지를 활성화 시켜주세요.",Toast.LENGTH_SHORT).show();
                }
            }

        });

        set_alarm = findViewById(R.id.set_alarm);


        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this,SetalarmActivity.class);
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
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        notificationManager.cancelAll();

    }
    private void compare(){

    }
    private void timerNoti(){

//    }
//    public class AlarmHATT {
//        private Context context;
//        public AlarmHATT(Context context){
//            this.context = context;
//        }
//        public void Alarm(){
//            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//            Intent intent = new Intent(AlarmActivity.this, 0, intent, 0 );
//
//            PendingIntent sender = PendingIntent.getBroadcast(AlarmActivity.this,0,intent,0);
//
//            Calendar calendar = Calendar.getInstance();
//
//            calendar.set(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
//
//            am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
//        }
    }
}

