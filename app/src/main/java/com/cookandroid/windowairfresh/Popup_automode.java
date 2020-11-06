package com.cookandroid.windowairfresh;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cookandroid.windowairfresh.R;

public class Popup_automode {
    private Context context;
    public Popup_automode(Context _context) {
        context = _context;

        //커스텀 Dialog 정의 위해 Dialog 클래스 생성
        dlg1 = new Dialog(context);
    }
    private int i;
    public void setnumber(int _i){
        i = _i;
    }

    //커스텀 Dialog 정의 위해 Dialog 클래스 생성
    private Dialog dlg1;
    //커스텀 다이얼로그의 각 위젯들 정의
    private TextView sentence1;
    private TextView sentence2;
    private ImageView con1;
    private Button popup1_close;

    public void callautomodepopup(){
        //타이틀바 숨김
        dlg1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg1.setContentView(R.layout.popup_automode);

        //커스텀 다이얼로그의 각 위젯들 정의
        sentence1 = dlg1.findViewById(R.id.sentence1);
        sentence2 = dlg1.findViewById(R.id.sentence2);
        con1 = dlg1.findViewById(R.id.con1);
        popup1_close = dlg1.findViewById(R.id.popup_close);

        //커스텀 다이얼로그를 노출한다.
        dlg1.show();

        switch(i){
            case 1:
                sentence1.setText("설정하신 수치에 따라");
                sentence2.setText("창문을 열었습니다.");
                con1.setImageResource(R.drawable.window_open);
                break;
            case 2:
                break;
            case 3:  sentence1.setText("설정하신 닫기 온도에 맞춰");
                break;
            case 4:  sentence1.setText("밖의 미세먼지가 높아");
                break;
            default:
                break;
        }

        popup1_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg1.dismiss();
            }
        });
    }
}

