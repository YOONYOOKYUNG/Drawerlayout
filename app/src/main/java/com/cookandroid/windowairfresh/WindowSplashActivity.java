package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;


public class WindowSplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windowsplash);
        ImageView windowsplash =(ImageView)findViewById(R.id.windowsplash);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(windowsplash);
        Glide.with(this).load(R.drawable.windowsplash).into(gifImage);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WindowSplashActivity.this, WindowlistActivity.class));
                finish();
            }
        }, 6000);  //시간설정
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();

    }
}
