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
    //활동 로그 출력
    private DatabaseManager databaseManager;
    ListView timeline;
    TimelineAdapter timelineAdapter;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //databaseManager 객체 얻어옴
        databaseManager = databaseManager.getInstance(this);

        timeline = findViewById(R.id.timeline);

        //Adapter 생성
        timelineAdapter = new TimelineAdapter();

        //databaseManager에 들어온 데이터를 추가함
        timelineAdapter.setDatabaseManager(databaseManager);

        //업데이트하기
        timelineAdapter.renewItem();

        //리스트뷰 참조, Adapter 닫기
        timeline.setAdapter(timelineAdapter);

        // 뒤로가기 버튼
        backarrow = findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}


