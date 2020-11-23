
package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cookandroid.windowairfresh.R;

public class HelpMode3Activity extends AppCompatActivity {
    //정보 화면
    ImageView cancel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_mode_suchi);
        cancel1 = findViewById(R.id.cancel1);
        //뒤로가기 버튼
        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}