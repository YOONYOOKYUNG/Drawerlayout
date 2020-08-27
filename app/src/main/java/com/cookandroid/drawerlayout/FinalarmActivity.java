package com.cookandroid.drawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class FinalarmActivity extends AppCompatActivity {
    TimePicker tp;
    Button btnsave;
    TextView st_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalarm);

        btnsave = findViewById(R.id.btnsave);
        st_time = findViewById(R.id.st_time);

        final Calendar cal = Calendar.getInstance();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
