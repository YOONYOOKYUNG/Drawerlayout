package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TutorialActivity extends AppCompatActivity {
ImageView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        help = findViewById(R.id.help);

        //어플 처음 실행 후 데이터 저장 -> 더 이상 도움말 페이지 안나오게 함
        SharedPreferences pref = getSharedPreferences("ONLYFIRST", MODE_PRIVATE);
        String First = pref.getString("FirstTime", "");

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if(First.equals("OK")){
            //어플을 처음 실행했을 경우
            Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            //어플을 두 번째 이후로 실행했을 경우
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("FirstTime","OK");            editor.apply();
        }
    }
}
