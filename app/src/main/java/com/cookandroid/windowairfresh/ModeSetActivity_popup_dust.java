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

public class ModeSetActivity_popup_dust extends AppCompatActivity {
    EditText dust_edit;
    Button okbtn, cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_modesetting_popup_dust);

        dust_edit=findViewById(R.id.dust_edit);
        okbtn=findViewById(R.id.okbtn);
        cancelbtn=findViewById(R.id.cancelbtn);


        //edit 입력시 숫자 키패드
        dust_edit.setInputType(InputType.TYPE_CLASS_NUMBER);

        //autoset activity에서 값 수신
        Intent intent = getIntent();
        String compare_dust = intent.getExtras().getString("compare_dust");

        //edit 에 이전 입력 값 띄우기
        dust_edit.setText(compare_dust);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit에 입력값이 없다면, 입력을 유도.
                String dust_str = dust_edit.getText().toString();
                if(dust_str.isEmpty()){
                    Toast.makeText(ModeSetActivity_popup_dust.this, "수치를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    dust_edit.setSelection(dust_edit.length());
                }else{
                    // 올바른 입력값인 경우
                    // autoset activity로 값 전송 후 팝업 종료
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("compare_dust",dust_str);
                    setResult(2,resultIntent);
                    finish();
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
    public boolean onTouchEvent(MotionEvent event) {
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
        super.onStop();
    }
}