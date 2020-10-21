package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class popup_set_temp extends AppCompatActivity {
    EditText high_temp,low_temp;
    Button okbtn,cancelbtn;
    int low_num, high_num;
    String high_str, low_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_set_temp);
        high_temp = findViewById(R.id.high_temp);
        low_temp = findViewById(R.id.low_temp);
        okbtn = findViewById(R.id.okbtn);
        cancelbtn = findViewById(R.id.cancelbtn);

        high_temp.setInputType(InputType.TYPE_CLASS_NUMBER);
        low_temp.setInputType(InputType.TYPE_CLASS_NUMBER);

        SharedPreferences tp_sf = getSharedPreferences("sftemp",MODE_PRIVATE);
        String sf_high2 = tp_sf.getString("high","");
        high_temp.setText(sf_high2);
        String sf_low2 = tp_sf.getString("low","");
        low_temp.setText(sf_low2);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                high_str = high_temp.getText().toString();
                low_str = low_temp.getText().toString();

                if(high_str.isEmpty()||low_str.isEmpty()){
                    Toast.makeText(popup_set_temp.this, "수치를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    high_temp.setSelection(high_temp.length());
                }else {
                    low_num = Integer.parseInt(low_str);
                    high_num = Integer.parseInt(high_str);

                    if(low_num>=high_num){
                        Toast.makeText(popup_set_temp.this, "최저 온도는 최고 온도보다 높게 설정할 수 없습니다. 다시 설정해주세요.", Toast.LENGTH_SHORT).show();
                        high_temp.setSelection(high_temp.length());
                    }
                    else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("low_temp",low_str);
                        resultIntent.putExtra("high_temp",high_str);
                        setResult(1,resultIntent);
                        finish();
                    }
                }
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public boolean onnTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    public void onBackPressed(){
        return;
        //뒤로가기 버튼 강제로 막음.
    }
    public void onStop(){
        SharedPreferences dust = getSharedPreferences("sftemp",MODE_PRIVATE);
        SharedPreferences.Editor editor = dust.edit();
        String sf_low = low_temp.getText().toString();
        String sf_high = high_temp.getText().toString();
        editor.putString("low",sf_low);
        editor.putString("high",sf_high);
        editor.commit();

        super.onStop();
    }
}