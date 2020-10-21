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

public class popup_set_dust extends AppCompatActivity {
        EditText dust_edit;
        Button okbtn, cancelbtn;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.popup_set_dust);

            dust_edit=findViewById(R.id.dust_edit);
            okbtn=findViewById(R.id.okbtn);
            cancelbtn=findViewById(R.id.cancelbtn);

            dust_edit.setInputType(InputType.TYPE_CLASS_NUMBER);

            SharedPreferences dust_sf = getSharedPreferences("dust",MODE_PRIVATE);
            String sf_dust_text = dust_sf.getString("dust","");
            dust_edit.setText(sf_dust_text);

            okbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dust_str = dust_edit.getText().toString();
                    if(dust_str.isEmpty()){
                        Toast.makeText(popup_set_dust.this, "수치를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        dust_edit.setSelection(dust_edit.length());
                    }else{
                        int dust_num = Integer.parseInt(dust_str);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("dust_num",dust_str);
                        setResult(2,resultIntent);
                        finish();
                    }
                }
            });
            cancelbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish(); }
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
        SharedPreferences dust = getSharedPreferences("dust",MODE_PRIVATE);
        SharedPreferences.Editor editor = dust.edit();
        String shared_dust =  dust_edit.getText().toString();
        editor.putString("dust",shared_dust);
        editor.commit();

        super.onStop();
        }
}