package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ActlogActivity extends AppCompatActivity {
    ListView listview;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actlog);

        listview = findViewById(R.id.listview);

        //create data
        ArrayList<Logs> arrayList = new ArrayList<>();
        arrayList.add(new Logs(R.drawable.waflogo2,"User opened the window"));
        arrayList.add(new Logs(R.drawable.waflogo2,"User closed the window"));

        //make custom adapter
        LogAdapter logAdapter = new LogAdapter(this, R.layout.row, arrayList);
        listview.setAdapter(logAdapter);


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

