package com.cookandroid.drawerlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActlogActivity extends AppCompatActivity {
    RecyclerView recyview;
    String s1[];
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actlog);

        s1 = getResources().getStringArray(R.array.description);

        MyAdapter myAdapter = new MyAdapter(this, s1);
        recyview = findViewById(R.id.recyview);
        recyview.setAdapter(myAdapter);
        recyview.setLayoutManager(new LinearLayoutManager(this));

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

