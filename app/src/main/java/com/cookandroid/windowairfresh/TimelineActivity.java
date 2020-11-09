package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    private DatabaseManager databaseManager;
    ListView timeline;
    TimelineAdapter timelineAdapter;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        databaseManager = databaseManager.getInstance(this);
        timeline = findViewById(R.id.timeline);
        timelineAdapter = new TimelineAdapter();
        timelineAdapter.setDatabaseManager(databaseManager);
        timelineAdapter.renewItem();

        timeline.setAdapter(timelineAdapter);
        // 뒤로가기 버튼
        backarrow = findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }
}


