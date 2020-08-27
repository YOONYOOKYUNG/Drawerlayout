package com.cookandroid.drawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetalarmActivity extends AppCompatActivity {
    TimePicker tp;
    TextView st_time;
    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setalarm);

        btnsave = findViewById(R.id.btnsave);
        st_time = findViewById(R.id.st_time);
        tp = findViewById(R.id.tp);


        final Calendar cal = Calendar.getInstance();
        int hourday = cal.get(cal.HOUR_OF_DAY);
        int min = cal.get(cal.MINUTE);


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                String st_txt = intent.getExtras().getString("st_txt");
                finish();
            }
        });

    }
}

