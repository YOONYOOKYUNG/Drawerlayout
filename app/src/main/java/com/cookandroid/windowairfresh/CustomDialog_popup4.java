package com.cookandroid.windowairfresh;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.sql.BatchUpdateException;

public class CustomDialog_popup4 {
    private Context context;
    public CustomDialog_popup4(Context context) {
        this.context = context;
    }
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        ToggleButton tbtn;
        AutoSetActivity autotb = new AutoSetActivity();
        tbtn=autotb.getTbtn();

        ListView listView;
        WindowlistActivity winlist = new WindowlistActivity();


        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.popup);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final ImageView iconnn = dlg.findViewById(R.id.iconnn);
        final TextView mode1 = dlg.findViewById(R.id.mode1);
        final TextView mode2 = dlg.findViewById(R.id.mode2);
        final TextView mode3 = dlg.findViewById(R.id.mode3);
        final TextView mode4 = dlg.findViewById(R.id.mode4);
        final Button closewar = dlg.findViewById(R.id.closewar);



        closewar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });







    }



}
