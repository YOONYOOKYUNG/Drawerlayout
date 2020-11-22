package com.cookandroid.windowairfresh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.windowairfresh.R;

public class HelpActivity extends AppCompatActivity {
    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

           cancel = findViewById(R.id.cancel);
           cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pf = getSharedPreferences("help", MODE_PRIVATE);
                    SharedPreferences.Editor editor =pf.edit();
                    editor.putBoolean("show", false); //First라는 key값으로 infoFirst 데이터를 저장한다.
                    editor.commit();
                    finish();
                }
            });


}
}