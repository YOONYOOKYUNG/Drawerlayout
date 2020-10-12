package com.cookandroid.windowairfresh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    ImageView cancel;
    CheckBox checkbox1;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


       cancel = findViewById(R.id.cancel);
       checkbox1 = findViewById(R.id.checkbox1);
       cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkbox1.isChecked()==true){
                    SharedPreferences pref = getSharedPreferences("help", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("help_show", "1");
                    editor.commit();
                }
                finish();
            }
        });





}
}