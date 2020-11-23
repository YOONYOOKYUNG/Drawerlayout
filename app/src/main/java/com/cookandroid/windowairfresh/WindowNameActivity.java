package com.cookandroid.windowairfresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class WindowNameActivity extends Activity {
    private DatabaseManager databaseManager;
    private String btaddress;
    private Boolean namestate = true;
    public ArrayList<WindowDetails> checklist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = DatabaseManager.getInstance(this);
        if (databaseManager != null) {
            checklist = databaseManager.getAll();
        }
        final Dialog inputnameDlg = new Dialog(this);
        //타이틀바 숨김
        inputnameDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inputnameDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //레이아웃 설정
        inputnameDlg.setContentView(R.layout.activity_windowname);
        //노출
        inputnameDlg.show();
        inputnameDlg.setCancelable(false);


        // 커스텀 다이얼로그의 각 위젯들을 정의
        final EditText message = (EditText) inputnameDlg.findViewById(R.id.messagse);
        final Button okButton = (Button) inputnameDlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) inputnameDlg.findViewById(R.id.cancelButton);

        //확인 버튼 클릭 시 창문등록 확인 버튼 클릭시 입력된 내용 전달
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btaddress = getIntent().getStringExtra("btaddress");
                int windownumber = checklist.size();
                String inputText = message.getText().toString();
                if (!inputText.equals("")) {
                    namestate = true;
                    for (int i = 0; i < windownumber; i++) {
                        if (inputText.equals(checklist.get(i).getName())) {
                            Toast.makeText(WindowNameActivity.this, "창문의 이름이 중복됩니다.", Toast.LENGTH_SHORT).show();
                            namestate = false;
                        }
                    }
                    if (namestate) {
                        Intent splashintent = new Intent(WindowNameActivity.this, WindowSplashActivity.class);
                        splashintent.putExtra("windowname", inputText);
                        splashintent.putExtra("btaddress", btaddress);
                        startActivity(splashintent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        inputnameDlg.dismiss();
                        WindowNameActivity.this.finish();
                    }
                } else { //다이얼로그에 창문 이름은 안쓰고 OK한 경우 토스트
                    Toast.makeText(WindowNameActivity.this, "창문의 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //취소 버튼 클릭 시
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputnameDlg.dismiss();
                Toast.makeText(WindowNameActivity.this, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();

            }
        });

    }
}