package com.cookandroid.drawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class WindowlistActivity extends AppCompatActivity {
    ImageButton btn1;
    ImageView backarrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windowlist);

        final ListView listview;

        btn1 = findViewById(R.id.btn1);
        final ListViewAdapter adapter;
        adapter = new ListViewAdapter();

        // 커스텀 다이얼로그에서 입력한 메시지를 출력할 TextView 를 준비한다.
        final TextView main_label = (TextView) findViewById(R.id.main_label);


        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog=new CustomDialog(WindowlistActivity.this);
                customDialog.setAdapter(adapter);
                customDialog.callFunction(main_label);
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
}
