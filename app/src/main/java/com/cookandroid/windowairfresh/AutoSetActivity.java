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
        shared_temp_high = sf.getString("High_temp","30");
        shared_temp_low = sf.getString("Low_temp","0");
        shared_dust = sf.getString("Compare_dust","20");

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
    // 미세먼지 창문닫기**지워야될거
    void set_dust() {
        final EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("미세먼지 개폐 수치 설정하기");
        builder.setMessage("외부와 내부가 몇 pm 이상 차이날 때 닫을까요?");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shared_dust=edittext.getText().toString();
                        Toast.makeText(getApplicationContext(),shared_dust+" 로 설정되었습니다." , Toast.LENGTH_LONG).show();
                        dust_txt.setText(shared_dust+" pm");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    //최고온도시 창문닫기**지워야될거
    void temp_High() {
        final EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        final int high_temp = Integer.parseInt(String.valueOf(edittext));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("최고 온도 설정하기");
        builder.setMessage("몇도 이상일때 창문을 닫기를 원하시나요?");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        shared_temp_high = edittext.getText().toString();
                        Toast.makeText(getApplicationContext(), shared_temp_high + " 로 설정되었습니다.", Toast.LENGTH_LONG).show();
                        high_temp_txt.setText(shared_temp_high+ " ℃");                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        set_dust();
                    }
                });
        builder.show();
    }

    //최저온도시 창문닫기 **지워야될거
    void temp_Low() {
        final EditText edittext2 = new EditText(this);
        final int low_temp = Integer.parseInt(String.valueOf(edittext2));
        edittext2.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("최저 온도 설정하기");
        builder.setMessage("몇도 이하일때 창문을 닫기를 원하시나요?");
        builder.setView(edittext2);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shared_temp_low=edittext2.getText().toString();
                        Toast.makeText(getApplicationContext(),shared_temp_low+" 로 설정되었습니다." , Toast.LENGTH_LONG).show();
                        low_temp_txt.setText(shared_temp_low+" ℃");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        if(requestCode==RESULT_TEST) {
//            if (resultCode == 1) {
//                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
//                low_temp_txt.setText(data.getStringExtra("low_temp"));
//                high_temp_txt.setText(data.getStringExtra("high_temp"));
//                shared_temp_low=low_temp_txt.getText().toString();
//                shared_temp_high=high_temp_txt.getText().toString();
//
//            }
//            else if(resultCode==2){
//                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
//                dust_txt.setText(data.getStringExtra("dust_num"));
//                shared_dust=dust_txt.getText().toString();
//            }
//        }
//    }

    protected void onStop(){
        super.onStop();
        SharedPreferences sf = getSharedPreferences("autoset",0);
        SharedPreferences.Editor editor =sf.edit();
        editor.clear();
        editor.putBoolean("modestate",modestate);
        editor.putString("High_temp",shared_temp_high);
        editor.putString("Low_temp",shared_temp_low);
        editor.putString("Compare_dust",shared_dust);
        editor.commit();
    }
}