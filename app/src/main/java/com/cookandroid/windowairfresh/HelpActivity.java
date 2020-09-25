package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class HelpActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView helpimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

       // helpimg = findViewById(R.id.helpimg);

       // helpimg.setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View view) {
        //        startActivity(new Intent(help.this, MainActivity.class));
        //    }
       // });


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HelpActivity.this, MainActivity.class));
            }
        };

        handler.postDelayed(runnable, 3000);

}
}