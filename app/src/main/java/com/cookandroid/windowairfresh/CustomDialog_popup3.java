package com.cookandroid.windowairfresh;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialog_popup3 {
    private Context context;

    public CustomDialog_popup3(Context context) {
        this.context = context;
    }

    //호출할 함수 정의
    public void callTemp() {
        //커스텀 Dialog 정의 위해 Dialog 클래스 생성
        final Dialog dlg3 = new Dialog(context);

        //타이틀바 숨김
        dlg3.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg3.setContentView(R.layout.popup3);

        //커스텀 다이얼로그를 노출한다.
        dlg3.show();

        //커스텀 다이얼로그의 각 위젯들 정의
        final TextView thermo = dlg3.findViewById(R.id.thermo);
        final TextView recommend = dlg3.findViewById(R.id.recommend);
        final ImageView con3 = dlg3.findViewById(R.id.con3);
        final Button popup3_close = dlg3.findViewById(R.id.popup3_close);

        Suchi suchi = new Suchi();
        suchi.setSuchi("18");
        thermo.setText(suchi.suchi);

        if(Integer.parseInt(thermo.getText().toString())<=4){
            recommend.setText("패딩, 기모, 목도리 등 겨울 옷");

        } else if(Integer.parseInt(thermo.getText().toString())<8){
            recommend.setText("코트, 가죽자켓, 기모");

        } else if(Integer.parseInt(thermo.getText().toString())<11){
            recommend.setText("자켓, 트렌치코트, 니트, 내복");

        } else if(Integer.parseInt(thermo.getText().toString())<16){
            recommend.setText("자켓, 청바지, 면바지, 후드티");

        } else if (Integer.parseInt(thermo.getText().toString())<19){
            recommend.setText("니트, 가디건, 맨투맨, 청바지, 슬랙스, 면바지");

        } else if(Integer.parseInt(thermo.getText().toString())<22){
            recommend.setText("긴팔티, 가디건, 슬랙스, 면바지, 청바지");

        } else if(Integer.parseInt(thermo.getText().toString())<26){
            recommend.setText("반팔티, 얇은 셔츠, 얇은 긴팔, 반바지");

        } else{
            recommend.setText("민소매, 반바지, 얇은 반팔티");
        }

        popup3_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg3.dismiss();
            }
        });
    }
}

