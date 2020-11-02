package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;


public class WindowSplashActivity extends AppCompatActivity {
    WindowListAdapter adapter= new WindowListAdapter();
    private String btaddress,windowname;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = DatabaseManager.getInstance(this);
        setContentView(R.layout.activity_windowsplash);
        adapter.setDatabaseManager(databaseManager);
        ImageView windowsplash =(ImageView)findViewById(R.id.windowsplash);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(windowsplash);
        Glide.with(this).load(R.drawable.windowsplash).into(gifImage);
        btaddress=getIntent().getStringExtra("btaddress");
        windowname=getIntent().getStringExtra("windowname");
        adapter.addItem(windowname,btaddress,true); //세번째 블루투스어드레스는 주소값을 넣어주면됨
        adapter.notifyDataSetChanged();
        Log.d("경원","btaddress:"+btaddress);
        Log.d("경원","windowname"+windowname);
        Log.d("경원","확인");
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 8000);  //시간설정
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
