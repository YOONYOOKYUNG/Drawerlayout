package com.cookandroid.windowairfresh;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AutoSetActivity extends AppCompatActivity {
    Button manual_mode, auto_mode, goWindowbtn;
    LinearLayout manual_layout;
    RelativeLayout dustbtn,tempLow_btn,auto_layout,temp_btn;
    TextView high_temp_txt,low_temp_txt,dust_txt;
    ImageView question, question2,backarrow;
    Boolean modestate;
    String shared_temp_high,shared_temp_low,shared_dust;
    int RESULT_TEST = 1212;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modesetting);
        manual_mode = findViewById(R.id.manual_mode);
        manual_layout = findViewById(R.id.manual_layout);
        auto_mode = findViewById(R.id.auto_mode);
        auto_layout = findViewById(R.id.auto_layout);
        dustbtn = findViewById(R.id.dustbtn);
        temp_btn = findViewById(R.id.temp_btn);
        high_temp_txt = findViewById(R.id.high_temp_txt);
        low_temp_txt = findViewById(R.id.low_temp_txt);
        dust_txt = findViewById(R.id.dust_txt);
        goWindowbtn = findViewById(R.id.goWindowbtn);
        question = findViewById(R.id.question);
        question2 = findViewById(R.id.question2);
        backarrow = findViewById(R.id.backarrow);

        SharedPreferences sf = getSharedPreferences("autoset",0);
        modestate = sf.getBoolean("modestate",false);
        shared_temp_high = sf.getString("hightemp","30");
        shared_temp_low = sf.getString("lowtemp","0");
        shared_dust = sf.getString("comparedust","20");

        high_temp_txt.setText(shared_temp_high);
        low_temp_txt.setText(shared_temp_low);
        dust_txt.setText(shared_dust);


        if (modestate==false){ // 모드값 저장이 수동일떄 = 0
            manual_mode.setBackgroundResource(R.drawable.modebtn);
            auto_mode.setBackgroundResource(R.drawable.modeoffbtn);
            manual_layout.setVisibility(View.VISIBLE);
            auto_layout.setVisibility(View.INVISIBLE);
        }else{                // 모드값 저장이 자동일떄 = 1
            auto_layout.setVisibility(View.VISIBLE);
            manual_layout.setVisibility(View.INVISIBLE);
            auto_mode.setBackgroundResource(R.drawable.modebtn);
            manual_mode.setBackgroundResource(R.drawable.modeoffbtn);
        };
        manual_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manual_mode.setBackgroundResource(R.drawable.modebtn);
                auto_mode.setBackgroundResource(R.drawable.modeoffbtn);
                manual_layout.setVisibility(View.VISIBLE);
                auto_layout.setVisibility(View.INVISIBLE);
                modestate = false; //수동버튼 누르면 0 저장
            }
        });
        auto_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manual_layout.setVisibility(View.INVISIBLE);
                auto_layout.setVisibility(View.VISIBLE);
                manual_mode.setBackgroundResource(R.drawable.modeoffbtn);
                auto_mode.setBackgroundResource(R.drawable.modebtn);
                modestate = true; //자동버튼 누르면 1 저장
            }
        });

        temp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), popup_set_temp.class);
                startActivityForResult(intent,RESULT_TEST);
            }
        });
        dustbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),popup_set_dust.class);
                startActivityForResult(intent2,RESULT_TEST);
            }
        });

        goWindowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutoSetActivity.this, WindowlistActivity.class);
                startActivity(intent);
            }
        });
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), HelpMode1Activity.class);
                startActivity(intent2);

            }
        });
        question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), HelpMode2Activity.class);
                startActivity(intent3);
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RESULT_TEST) {
            if (resultCode == 1) {
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                low_temp_txt.setText(data.getStringExtra("lowtemp"));
                high_temp_txt.setText(data.getStringExtra("hightemp"));
                //shared_temp_low=low_temp_txt.getText().toString();
                //shared_temp_high=high_temp_txt.getText().toString();

            }
            else if(resultCode==2){
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                dust_txt.setText(data.getStringExtra("comparedust"));
                //shared_dust=dust_txt.getText().toString();
            }
        }
    }

    protected void onStop(){
        super.onStop();
        SharedPreferences sf = getSharedPreferences("autoset",0);
        SharedPreferences.Editor editor =sf.edit();
        editor.clear();
        editor.putBoolean("modestate",modestate);
        editor.putString("hightemp",shared_temp_high);
        editor.putString("lowtemp",shared_temp_low);
        editor.putString("comparedust",shared_dust);
        editor.commit();
    }
}