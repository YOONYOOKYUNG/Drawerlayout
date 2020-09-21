package com.cookandroid.windowairfresh;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Popup1_dust {
    private Context context;
    private String msmjsuchi;

    public Popup1_dust(Context context) {
        this.context = context;
    }
    public void setmsmjsuchi(String _msmj){
        msmjsuchi = _msmj;
    }
    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.popup1_dust);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView boldtext = dlg.findViewById(R.id.boldtext);
        final ImageView con1 = dlg.findViewById(R.id.con1);
        final TextView suchigood = dlg.findViewById(R.id.suchigood);
        final TextView suchisoso = dlg.findViewById(R.id.suchisoso);
        final TextView suchibad = dlg.findViewById(R.id.suchibad);
        final ImageView pointgood = dlg.findViewById(R.id.pointgood);
        final ImageView pointsoso = dlg.findViewById(R.id.pointsoso);
        final ImageView pointbad = dlg.findViewById(R.id.pointbad);
        final LinearLayout con_good = dlg.findViewById(R.id.con_good);
        final LinearLayout con_soso = dlg.findViewById(R.id.con_soso);
        final LinearLayout con_bad = dlg.findViewById(R.id.con_bad);
        final Button btnclose = (Button) dlg.findViewById(R.id.btnclose);




        Suchi suchi = new Suchi();
        suchi.setSuchi(msmjsuchi);
        suchigood.setText(suchi.suchi);
        suchibad.setText(suchi.suchi);
        suchisoso.setText(suchi.suchi);
        if (Integer.parseInt(suchigood.getText().toString())<30){
            boldtext.setText("좋음");
            con1.setImageResource(R.drawable.darkhappy);
            suchisoso.setVisibility(View.INVISIBLE);
            suchibad.setVisibility(View.INVISIBLE);
            pointbad.setVisibility(View.INVISIBLE);
            pointsoso.setVisibility(View.INVISIBLE);
            con_good.setVisibility(View.VISIBLE);
            con_soso.setVisibility(View.INVISIBLE);
            con_bad.setVisibility(View.INVISIBLE);

        }else if (Integer.parseInt(suchisoso.getText().toString())<80){
            boldtext.setText("보통");
            con1.setImageResource(R.drawable.darksoso);
            suchigood.setVisibility(View.INVISIBLE);
            suchibad.setVisibility(View.INVISIBLE);
            pointbad.setVisibility(View.INVISIBLE);
            pointgood.setVisibility(View.INVISIBLE);
            con_good.setVisibility(View.INVISIBLE);
            con_soso.setVisibility(View.VISIBLE);
            con_bad.setVisibility(View.INVISIBLE);

        }else if (Integer.parseInt(suchibad.getText().toString())>=80){
            boldtext.setText("나쁨");
            con1.setImageResource(R.drawable.darkbad);
            suchigood.setVisibility(View.INVISIBLE);
            suchisoso.setVisibility(View.INVISIBLE);
            pointgood.setVisibility(View.INVISIBLE);
            pointsoso.setVisibility(View.INVISIBLE);
            con_good.setVisibility(View.INVISIBLE);
            con_soso.setVisibility(View.INVISIBLE);
            con_bad.setVisibility(View.VISIBLE);

        }


        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

    }



}

