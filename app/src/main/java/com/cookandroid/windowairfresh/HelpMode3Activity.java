
package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cookandroid.windowairfresh.R;

public class HelpMode3Activity extends AppCompatActivity {
ImageView cancel1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_mode_suchi);
        cancel1 = findViewById(R.id.cancel1);

        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}