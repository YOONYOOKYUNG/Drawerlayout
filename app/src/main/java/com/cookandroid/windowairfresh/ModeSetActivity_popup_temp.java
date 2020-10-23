package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModeSetActivity_popup_temp extends AppCompatActivity {
    EditText high_temp_edit,low_temp_edit;
    Button okbtn,cancelbtn;
    int low_num, high_num;
    String high_str, low_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modesetting_popup_temp);
        high_temp_edit = findViewById(R.id.high_temp);
        low_temp_edit = findViewById(R.id.low_temp);
        okbtn = findViewById(R.id.okbtn);
        cancelbtn = findViewById(R.id.cancelbtn);

        //edit 입력시 숫자 키패드
        high_temp_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        low_temp_edit.setInputType(InputType.TYPE_CLASS_NUMBER);

        //autoset activity에서 값 수신
        Intent intent = getIntent();
        String high_temp = intent.getExtras().getString("high_temp");
        final String low_temp = intent.getExtras().getString("low_temp");

        //edit 에 이전 입력값 띄우기
        high_temp_edit.setText(high_temp);
        low_temp_edit.setText(low_temp);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                high_str = high_temp_edit.getText().toString();
                low_str = low_temp_edit.getText().toString();

                //edit에 입력값이 없다면, 입력을 유도.
                if(high_str.isEmpty()||low_str.isEmpty()){
                    Toast.makeText(ModeSetActivity_popup_temp.this, "수치를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    high_temp_edit.setSelection(high_temp_edit.length());
                    low_temp_edit.setSelection(low_temp_edit.length());

                }else {
                    low_num = Integer.parseInt(low_str);
                    high_num = Integer.parseInt(high_str);

                    //최저온도가 최고온도보다 높을 시, 재입력 유도
                    if(low_num>=high_num){
                        Toast.makeText(ModeSetActivity_popup_temp.this, "최저 온도는 최고 온도보다 높게 설정할 수 없습니다. 다시 설정해주세요.", Toast.LENGTH_SHORT).show();
                        high_temp_edit.setSelection(high_temp_edit.length());
                        low_temp_edit.setSelection(low_temp_edit.length());
                    }else if(low_num==high_num){
                        Toast.makeText(ModeSetActivity_popup_temp.this, "최저 온도와 최고 온도는 같을 수 없습니다. 다시 설정해주세요.", Toast.LENGTH_SHORT).show();
                        high_temp_edit.setSelection(high_temp_edit.length());
                        low_temp_edit.setSelection(low_temp_edit.length());
                    }
                    else {
                        //올바른 입력값인 경우
                        // autoset activity로 값 전송 후 팝업 종료
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("high_temp",high_str);
                        resultIntent.putExtra("low_temp",low_str);
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

    //바깥레이어 클릭시 안닫히게
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    //뒤로가기 버튼 강제로 막음.
    public void onBackPressed(){
        return;

    }
    public void onStop(){
        super.onStop();
    }
}