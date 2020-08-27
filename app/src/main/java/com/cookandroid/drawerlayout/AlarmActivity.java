package com.cookandroid.drawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    TextView st_time,st_time2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        st_time = findViewById(R.id.st_time);
        st_time2 = findViewById(R.id.st_time2);

        Switch switchbtn = findViewById(R.id.sb_use_listener);


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
    }
}

