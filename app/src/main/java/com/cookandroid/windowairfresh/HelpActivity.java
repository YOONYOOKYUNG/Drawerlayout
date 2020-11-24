package com.cookandroid.windowairfresh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {
    ImageView cancel;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        cancel = findViewById(R.id.cancel);
        checkBox = findViewById(R.id.checkbox1);

        //취소버튼 이벤트
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked() == true) { //체크박스가 체크 되었을 시
                    // 다음부터 도움말 창이 보이지 않도록
                    // SharedPreferences에 false 저장
                    SharedPreferences pf = getSharedPreferences("help", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pf.edit();
                    editor.putBoolean("show", false);
                    editor.commit();
                    finish();
                } else {// 체크박스가 체크되어있지 않을 시
                    finish();
                }
            }
        });
    }
}