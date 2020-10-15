package com.cookandroid.windowairfresh;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Popup_warning extends Activity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup4_warning);


        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button closewar = findViewById(R.id.closewar);



        closewar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
