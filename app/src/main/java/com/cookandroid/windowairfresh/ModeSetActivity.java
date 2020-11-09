package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModeSetActivity extends AppCompatActivity {
    Button manual_mode, auto_mode, goWindowbtn;
    LinearLayout manual_layout;
    RelativeLayout dustbtn,auto_layout,temp_btn;
    TextView high_temp_txt,low_temp_txt,dust_txt;
    ImageView question, question2,backarrow,question3;
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
        question3 = findViewById(R.id.question3);

        // shared 값 받아오기
        get_shared();

        // 화면에 shared 값 띄우기
        high_temp_txt.setText(shared_temp_high);
        low_temp_txt.setText(shared_temp_low);
        dust_txt.setText(shared_dust);

        //모드값에 따른 화면 변경
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


        //모드 값 버튼 이벤트에 따른 화면변경 및 shared값 변경
        manual_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manual_mode.setBackgroundResource(R.drawable.modebtn);
                auto_mode.setBackgroundResource(R.drawable.modeoffbtn);
                manual_layout.setVisibility(View.VISIBLE);
                auto_layout.setVisibility(View.INVISIBLE);
                modestate = false; //수동버튼 누르면 0 저장
                set_shared_boolean(modestate);
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

        //수치설정창(자동모드) 이벤트
        temp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModeSetActivity_popup_temp.class);
                intent.putExtra("high_temp",shared_temp_high);
                intent.putExtra("low_temp",shared_temp_low);
                startActivityForResult(intent,RESULT_TEST);
            }
        });
        dustbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), ModeSetActivity_popup_dust.class);
                intent2.putExtra("compare_dust",shared_dust);
                startActivityForResult(intent2,RESULT_TEST);
            }
        });

        //창문설정창(수동모드) 이벤트
        goWindowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(ModeSetActivity.this, WindowlistActivity.class);
                startActivity(intent3);
            }
        });

        //도움말1 수동 이벤트
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getApplicationContext(), HelpMode1Activity.class);
                startActivity(intent4);

            }
        });
        //도움말2 이벤트
        question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getApplicationContext(), HelpMode2Activity.class);
                startActivity(intent5);
            }
        });
        question3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(getApplicationContext(),HelpMode3Activity.class);
                startActivity(intent6);
            }
        });

        //뒤로가기 이벤트
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent7);
            }

        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RESULT_TEST) {
            if (resultCode == 1) {
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                // popup에서 입력값 받아오기
                shared_temp_high = data.getStringExtra("high_temp");
                shared_temp_low = data.getStringExtra("low_temp");
                // 사용자 입력값 autoset화면에 띄우기
                high_temp_txt.setText(shared_temp_high);
                low_temp_txt.setText(shared_temp_low);
                //shared 값 저장
                set_shared_string("high_temp",shared_temp_high);
                set_shared_string("low_temp",shared_temp_low);
            }
            else if(resultCode==2){
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                // popup에서 입력값 받아오기
                shared_dust = data.getStringExtra("compare_dust");
                // 사용자 입력값 autoset화면에 띄우기
                dust_txt.setText(shared_dust);
                //shared 값 저장
                set_shared_string("compare_dust",shared_dust);
            }
        }
    }

    // shared 모드 값 수신 함수
    public void get_shared() {
        SharedPreferences sf = getSharedPreferences("autoset",0);
        modestate = sf.getBoolean("modestate",false);
        shared_temp_high = sf.getString("high_temp","30");
        shared_temp_low = sf.getString("low_temp","0");
        shared_dust = sf.getString("compare_dust","20");
    }

    // shared 모드 값 저장 함수
    public void set_shared_boolean(Boolean value) {
        SharedPreferences sf = getSharedPreferences("autoset",0);
        SharedPreferences.Editor editor = sf.edit();
        editor.putBoolean("modestate", value);
        Log.d("00","modestate의 값 "+value+" 가 저장되었습니다");
        editor.commit();
    }
    // shared 수치 값 저장 함수
    public void set_shared_string(String key, String value) {
        SharedPreferences sf = getSharedPreferences("autoset",0);
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(key, value);
        editor.commit();
        Log.d("00",key+"의 값 "+value+" 가 저장되었습니다");
    }

    protected void onStop(){
        set_shared_boolean(modestate);
        super.onStop();
    }
}



















