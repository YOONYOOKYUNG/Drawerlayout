package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    private DatabaseManager databaseManager;
    ListView timeline;
    TimelineAdapter timelineAdapter;

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

    }

}


