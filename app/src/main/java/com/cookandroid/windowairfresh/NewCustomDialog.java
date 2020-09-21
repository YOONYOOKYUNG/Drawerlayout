package com.cookandroid.windowairfresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NewCustomDialog extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_custom_dialog);

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) findViewById(R.id.messagse);
        final Button okButton = (Button) findViewById(R.id.okButton);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputText =  message.getText().toString();
                if(!inputText.equals("")){
                    Toast.makeText(NewCustomDialog.this, "\"" + message.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("new_window_name", message.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();

                } else { //다이얼로그에 창문 이름은 안쓰고 OK한 경우 토스트
                    Toast.makeText(NewCustomDialog.this, "창문의 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewCustomDialog.this, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}