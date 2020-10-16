package com.cookandroid.windowairfresh;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.windowairfresh.R;

public class AutoSetActivity extends AppCompatActivity {
    Button manual_mode, custom_mode;
    LinearLayout manual_layout;
    RelativeLayout custom_layout,dustbtn,tempLow_btn,tempHigh_btn;
    TextView high_temp_txt,low_temp_txt,dust_txt;
    Boolean state;
    String shared_temp_high,shared_temp_low,shared_dust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modesetting);
        manual_mode = findViewById(R.id.manual_mode);
        custom_mode = findViewById(R.id.custom_mode);
        manual_layout = findViewById(R.id.manual_layout);
        custom_layout = findViewById(R.id.custom_layout);
        dustbtn = findViewById(R.id.dustbtn);
        tempLow_btn = findViewById(R.id.tempLow_btn);
        tempHigh_btn = findViewById(R.id.tempHigh_btn);
        high_temp_txt = findViewById(R.id.high_temp_txt);
        low_temp_txt = findViewById(R.id.low_temp_txt);
        dust_txt = findViewById(R.id.dust_txt);


        SharedPreferences sf = getSharedPreferences("autoset",0);
        state = sf.getBoolean("state",false);
        shared_temp_high = sf.getString("High_temp","30 ℃");
        shared_temp_low = sf.getString("Low_temp","0 ℃");
        shared_dust = sf.getString("Compare_dust","20 pm");

        high_temp_txt.setText(shared_temp_high);
        low_temp_txt.setText(shared_temp_low);
        dust_txt.setText(shared_dust);

        if (state==false){ // 모드값 저장이 수동일떄 = 0
            manual_mode.setBackgroundResource(R.drawable.modebtn);
            custom_mode.setBackgroundResource(R.drawable.modeoffbtn);
            manual_layout.setVisibility(View.VISIBLE);
            custom_layout.setVisibility(View.INVISIBLE);
        }else{                // 모드값 저장이 자동일떄 = 1
            custom_layout.setVisibility(View.VISIBLE);
            manual_layout.setVisibility(View.INVISIBLE);
            custom_mode.setBackgroundResource(R.drawable.modebtn);
            manual_mode.setBackgroundResource(R.drawable.modeoffbtn);
        };
        manual_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manual_mode.setBackgroundResource(R.drawable.modebtn);
                custom_mode.setBackgroundResource(R.drawable.modeoffbtn);
                manual_layout.setVisibility(View.VISIBLE);
                custom_layout.setVisibility(View.INVISIBLE);
                state = false; //수동버튼 누르면 0 저장
            }
        });
        custom_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manual_layout.setVisibility(View.INVISIBLE);
                custom_layout.setVisibility(View.VISIBLE);
                manual_mode.setBackgroundResource(R.drawable.modeoffbtn);
                custom_mode.setBackgroundResource(R.drawable.modebtn);
                state = true; //자동버튼 누르면 1 저장
            }
        });

        //수치 입력버튼 이벤트
        tempLow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_Low();
            }
        });
        tempHigh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_High();
            }
        });
        dustbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_dust();
            }
        });
    }

    // 미세먼지 창문닫기
    void set_dust() {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("미세먼지 개폐 수치 설정하기");
        builder.setMessage("외부와 내부가 몇 pm 이상 차이날 때 닫을까요?");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shared_dust=edittext.getText().toString()+" pm";
                        Toast.makeText(getApplicationContext(),shared_dust+" 로 설정되었습니다." , Toast.LENGTH_LONG).show();
                        dust_txt.setText(shared_dust);

                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    //최고온도시 창문닫기
    void temp_High() {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("최고 온도 설정하기");
        builder.setMessage("몇도 이상일때 창문을 닫기를 원하시나요?");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shared_temp_high=edittext.getText().toString()+" ℃";
                        Toast.makeText(getApplicationContext(),shared_temp_high+" 로 설정되었습니다." , Toast.LENGTH_LONG).show();
                        high_temp_txt.setText(shared_temp_high);
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        set_dust();
                    }
                });
        builder.show();
    }

    //최저온도시 창문닫기
    void temp_Low() {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("최저 온도 설정하기");
        builder.setMessage("몇도 이하일때 창문을 닫기를 원하시나요?");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shared_temp_low=edittext.getText().toString()+" ℃";
                        Toast.makeText(getApplicationContext(),shared_temp_low+" 로 설정되었습니다." , Toast.LENGTH_LONG).show();
                        low_temp_txt.setText(shared_temp_low);
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }




    protected void onStop(){
        super.onStop();
        SharedPreferences sf = getSharedPreferences("autoset",0);
        SharedPreferences.Editor editor =sf.edit();
        editor.clear();
        editor.putBoolean("state",state);
        editor.putString("High_temp",shared_temp_high);
        editor.putString("Low_temp",shared_temp_low);
        editor.putString("Compare_dust",shared_dust);
        editor.commit();
    }
}