package com.cookandroid.windowairfresh;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialog_popup2 {
    private Context context;
    private String temhumid;

    public CustomDialog_popup2(Context context){
        this.context = context;
    }

    public void setTemhumid(String temhumid) {
        this.temhumid = temhumid;
    }

    //호출할 다이얼로그 함수 정의
    public void callHumid(){
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg2 = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg2.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg2.setContentView(R.layout.popup2);

        // 커스텀 다이얼로그를 노출한다.
        dlg2.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final ImageView con2 = dlg2.findViewById(R.id.con2);
        final TextView text1 = dlg2.findViewById(R.id.text1);
        final TextView text2 = dlg2.findViewById(R.id.text2);
        final Button popup2_close = dlg2.findViewById(R.id.popup2_close);

        Suchi suchi = new Suchi();
        suchi.setSuchi(temhumid);
        text1.setText(suchi.suchi);

        if(Integer.parseInt(text1.getText().toString())<68){
            con2.setImageResource(R.drawable.darkhappy);
            text2.setText("쾌적해요!");
        }else if(Integer.parseInt(text1.getText().toString())<75){
            con2.setImageResource(R.drawable.darksmile);
            text2.setText("불쾌지수가 보통이에요");
        }else if(Integer.parseInt(text1.getText().toString())<80){
            con2.setImageResource(R.drawable.darksoso);
            text2.setText("불쾌지수가 높은 편이에요");
        }else if(Integer.parseInt(text1.getText().toString())>=80){
            con2.setImageResource(R.drawable.darkbad);
            text2.setText("불쾌지수가 매우 높아요!");
        }

        popup2_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg2.dismiss();
            }
        });
    }
}
