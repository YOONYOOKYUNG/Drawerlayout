package com.cookandroid.windowairfresh;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AutoSetActivity extends AppCompatActivity {
    Button manual_mode, auto_mode, custom_mode;
    LinearLayout manual_layout,auto_layout;
    RelativeLayout custom_layout,dustbtn,tempLow_btn,tempHigh_btn;
    TextView high_temp_txt,low_temp_txt,dust_txt;
    String suchi="file";
    String state ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modesetting);
        manual_mode = findViewById(R.id.manual_mode);
        auto_mode = findViewById(R.id.auto_mode);
        custom_mode = findViewById(R.id.custom_mode);
        manual_layout = findViewById(R.id.manual_layout);
        auto_layout = findViewById(R.id.auto_layout);
        custom_layout = findViewById(R.id.custom_layout);
        dustbtn = findViewById(R.id.dustbtn);
        tempLow_btn = findViewById(R.id.tempLow_btn);
        tempHigh_btn = findViewById(R.id.tempHigh_btn);
        high_temp_txt = findViewById(R.id.high_temp_txt);
        low_temp_txt = findViewById(R.id.low_temp_txt);
        dust_txt = findViewById(R.id.dust_txt);


        SharedPreferences sp_suchi = getSharedPreferences(suchi,0);
        state = sp_suchi.getString("state","");
        high_temp_txt.setText(sp_suchi.getString("High_temp",""));
        low_temp_txt.setText(sp_suchi.getString("Low_temp",""));
        dust_txt.setText(sp_suchi.getString("compare_dust",""));

        manual_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manual_layout.setVisibility(View.VISIBLE);
                auto_layout.setVisibility(View.INVISIBLE);
                custom_layout.setVisibility(View.INVISIBLE);
                manual_mode.setBackgroundResource(R.drawable.modebtn);
                auto_mode.setBackgroundResource(R.drawable.modeoffbtn);
                custom_mode.setBackgroundResource(R.drawable.modeoffbtn);
            }
        });
        auto_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_layout.setVisibility(View.VISIBLE);
                manual_layout.setVisibility(View.INVISIBLE);
                custom_layout.setVisibility(View.INVISIBLE);
                auto_mode.setBackgroundResource(R.drawable.modebtn);
                manual_mode.setBackgroundResource(R.drawable.modeoffbtn);
                custom_mode.setBackgroundResource(R.drawable.modeoffbtn);
            }
        });
        custom_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom_layout.setVisibility(View.VISIBLE);
                auto_layout.setVisibility(View.INVISIBLE);
                manual_layout.setVisibility(View.INVISIBLE);
                custom_mode.setBackgroundResource(R.drawable.modebtn);
                manual_mode.setBackgroundResource(R.drawable.modeoffbtn);
                auto_mode.setBackgroundResource(R.drawable.modeoffbtn);
            }
        });
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
    void temp_High()
    {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("최고 온도 설정하기");
        builder.setMessage("몇도 이상일때 창문을 열기를 원하시나요?");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString()+" ℃ 로 설정되었습니다." , Toast.LENGTH_LONG).show();
                        high_temp_txt.setText(edittext.getText()+" ℃");
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
    void temp_Low()
    {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("최저 온도 설정하기");
        builder.setMessage("몇도 이하일때 창문을 닫기를 원하시나요?");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString()+" ℃ 로 설정되었습니다." , Toast.LENGTH_LONG).show();
                        low_temp_txt.setText(edittext.getText()+" ℃");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
    void set_dust()
    {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("미세먼지 개폐 수치 설정하기");
        builder.setMessage("외부와 내부가 몇 pm 이상 차이날 때 닫을까요?");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString()+" pm 로 설정되었습니다." , Toast.LENGTH_LONG).show();
                        dust_txt.setText(edittext.getText()+" pm");
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
        SharedPreferences set_text = getSharedPreferences(suchi,0);
        SharedPreferences.Editor editor_suchi =set_text.edit();
        editor_suchi.clear();
        editor_suchi.putString("state",state);
        editor_suchi.putString("High_temp",high_temp_txt.toString());
        editor_suchi.putString("Low_temp",low_temp_txt.toString());
        editor_suchi.putString("compare_dust",dust_txt.toString());
        editor_suchi.commit();
    }

}