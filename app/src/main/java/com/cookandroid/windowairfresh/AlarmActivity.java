package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {
    final int pagenum = 1;
    String sharedName = "file";
    TextView st_time;
    RelativeLayout rl3;
    ImageView backarrow;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        final Switch swit_push, swit_setTime;

        swit_push = findViewById(R.id.swit_push);
        swit_setTime = findViewById(R.id.swit_setTime);
        st_time = findViewById(R.id.st_time);
        rl3 = findViewById(R.id.rl3);
        backarrow = findViewById(R.id.backarrow);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });


        SharedPreferences state = getSharedPreferences(sharedName, MODE_PRIVATE);
        Boolean state1 = state.getBoolean("push_On", swit_push.isChecked());
        Boolean state2 = state.getBoolean("setTime_ON", swit_setTime.isChecked());
        swit_push.setChecked(state1);
        swit_setTime.setChecked(state2);
        //스위치값저장

        swit_push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    createNotifi();

                }
            }
        });
        swit_setTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swit_setTime.isChecked() == true) {
                    alarmSetTime();
                } else {
                }

            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swit_setTime.isChecked() == true) {
                    Intent intent = new Intent(getApplicationContext(), AlarmSpinnerActivity.class);
                    startActivityForResult(intent, pagenum);
                } else {
                    Toast.makeText(getApplicationContext(), "매 시간 알림받기를 켜주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createNotifi(){
       //    PendingIntent mpendingIntent = PendingIntent.getActivity(, 0,
       //         new Intent(getApplicationContext(), MainActivity.class),
        //        PendingIntent.FLAG_UPDATE_CURRENT);//노티피케이션을 클릭시 해당 클래스로 화면 이동.

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.alarm_icon);
        builder.setContentTitle("WAF 푸쉬 알림");
        builder.setContentText("푸쉬 알림이 켜졌습니다.");

        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(new NotificationChannel("default","기본채널",NotificationManager.IMPORTANCE_DEFAULT));


        }
        notificationManager.notify(1,builder.build());
    }
    public void alarmSetTime(){
//        PendingIntent mpendingIntent = PendingIntent.getActivity(MainActivity.this, 0, new Intent(getApplicationContext(),
//               MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);//노티피케이션을 클릭시 해당 클래스로 화면 이동.

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "alarm");

        builder2.setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle("매 시간 알림이 켜졌습니다.")
                .setContentText("설정한 시간에 따라 매 시간 알림을 보내드립니다.")
                .setAutoCancel(false);
                //.setContentIntent(mpendingIntent);


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(new NotificationChannel("alarm","매 시간 알림",NotificationManager.IMPORTANCE_DEFAULT));
        }
        notificationManager.notify(2,builder2.build());
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent date){
        //타임셋클래스의 실행결과를 onAcitivityResult 메서드를 전달받는다.메인에서 호출한 액티비티가 종료되면 위 메서드를 호출한다.

        TextView st_time = (TextView)findViewById(R.id.st_time);
        if(requestCode==pagenum){//액티비티가 정상적으로 종료됐다면
            if(resultCode == RESULT_OK){
                //호출한 경우에만 처리
                st_time.setText(date.getStringExtra("time"));
            }
        }
        super.onActivityResult(requestCode,resultCode,date);
    }

    protected void onStop(){

        Switch swit_push = (Switch)findViewById(R.id.swit_push);
        Switch swit_setTime = (Switch)findViewById(R.id.swit_setTime);
        TextView st_time = (TextView)findViewById(R.id.st_time);
        SharedPreferences state = getSharedPreferences(sharedName, 0);//SharedPreference에 스위치 온오프값을 저장함.
        SharedPreferences.Editor editor2 = state.edit();//저장하려면 eidtor가 필요함.
        editor2.putBoolean("push_ON",swit_push.isChecked());
        editor2.putBoolean("setTime_ON",swit_setTime.isChecked());
        editor2.putString("set_Time", (String) st_time.getText());
        editor2.commit();

        super.onStop();
    }
    @Override
    public void onBackPressed(){
        AlarmActivity.this.finish();
        super.onBackPressed();
    }
}

