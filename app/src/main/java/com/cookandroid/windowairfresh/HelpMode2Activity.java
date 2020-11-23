package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cookandroid.windowairfresh.R;

public class HelpMode2Activity extends AppCompatActivity {
    //메인 페이지 도움말
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_mode2);
        ImageView cancel1 = (ImageView) findViewById(R.id.cancel1);
        //닫힘 버튼
        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}