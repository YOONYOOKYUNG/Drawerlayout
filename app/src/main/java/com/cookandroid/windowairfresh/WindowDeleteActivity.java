package com.cookandroid.windowairfresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class WindowDeleteActivity extends Activity {

    WindowListAdapter adapter = new WindowListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //커스텀 다이얼로그를 정의하기 위해 Dialog 클래스를 생성한다.
        final Dialog inputnameDlg = new Dialog(this);
        //타이틀바 숨김
        inputnameDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inputnameDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //레이아웃 설정
        inputnameDlg.setContentView(R.layout.activity_windowdelete);
        //노출
        inputnameDlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button okButton = (Button) inputnameDlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) inputnameDlg.findViewById(R.id.cancelButton);



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                int pos = intent.getExtras().getInt("position");

                Log.d("0000", "delete pos : "+ String.valueOf(pos));

                adapter.removeitem(pos);
                adapter.notifyDataSetChanged();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }
}