package com.cookandroid.windowairfresh;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Popup {
    private Context context;
    public Popup(Context context) {
        this.context = context;
    }

    private String temp;
    public void settemp(String _temp) {
        temp = _temp;
    }

    private String dust;
    public void setdust(String _dust){
        dust = _dust;
    }

    private String humid;
    public void sethumid(String _humid) {
        humid = _humid;
    }

    //popup1_temp 호출
    public void calltemppopup() {
        //커스텀 Dialog 정의 위해 Dialog 클래스 생성
        final Dialog dlg1 = new Dialog(context);
        //타이틀바 숨김
        dlg1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg1.setContentView(R.layout.popup1_temp);
        //커스텀 다이얼로그를 노출한다.
        dlg1.show();

        //커스텀 다이얼로그의 각 위젯들 정의
        final TextView thermo = dlg1.findViewById(R.id.thermo);
        final TextView recommend = dlg1.findViewById(R.id.recommend);
        final ImageView con1 = dlg1.findViewById(R.id.con1);
        final Button popup1_close = dlg1.findViewById(R.id.popup1_close);

        thermo.setText(temp);

        if(Integer.parseInt(temp)<=4){
            recommend.setText("패딩, 기모, 목도리 등 겨울 옷");
            con1.setImageResource(R.drawable.cloth_4);

        } else if(Integer.parseInt(temp)<8){
            recommend.setText("코트, 가죽자켓, 기모");
            con1.setImageResource(R.drawable.cloth_8);

        } else if(Integer.parseInt(temp)<11){
            recommend.setText("자켓, 트렌치코트, 니트, 내복");
            con1.setImageResource(R.drawable.cloth_11);

        } else if(Integer.parseInt(temp)<16){
            recommend.setText("후드티, 자켓, 청바지, 면바지");
            con1.setImageResource(R.drawable.cloth_16);

        } else if (Integer.parseInt(temp)<19){
            recommend.setText("니트, 가디건, 맨투맨, 청바지, 슬랙스, 면바지");
            con1.setImageResource(R.drawable.cloth_19);

        } else if(Integer.parseInt(temp)<22){
            recommend.setText("반팔티, 얇은 셔츠, 얇은 긴팔, 반바지");
            con1.setImageResource(R.drawable.cloth_22);

        } else if(Integer.parseInt(temp)<26){
            recommend.setText("긴팔티, 가디건, 슬랙스, 면바지, 청바지");
            con1.setImageResource(R.drawable.cloth_26);

        } else{
            recommend.setText("민소매, 반바지, 얇은 반팔티");
            con1.setImageResource(R.drawable.cloth_else);
        }

        popup1_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg1.dismiss();
            }
        });
    }



    // popup2_dust 호출
    public void calldustpopup() {
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg2 = new Dialog(context);
        // 액티비티의 타이틀바를 숨긴다.
        dlg2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg2.setContentView(R.layout.popup2_dust);
        // 커스텀 다이얼로그를 노출한다.
        dlg2.show();
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView boldtext = dlg2.findViewById(R.id.boldtext);
        final ImageView con2 = dlg2.findViewById(R.id.con2);
        final TextView suchigood = dlg2.findViewById(R.id.suchigood);
        final TextView suchisoso = dlg2.findViewById(R.id.suchisoso);
        final TextView suchibad = dlg2.findViewById(R.id.suchibad);
        final ImageView pointgood = dlg2.findViewById(R.id.pointgood);
        final ImageView pointsoso = dlg2.findViewById(R.id.pointsoso);
        final ImageView pointbad = dlg2.findViewById(R.id.pointbad);
        final LinearLayout con_good = dlg2.findViewById(R.id.con_good);
        final LinearLayout con_soso = dlg2.findViewById(R.id.con_soso);
        final LinearLayout con_bad = dlg2.findViewById(R.id.con_bad);
        final Button popup2_close = (Button) dlg2.findViewById(R.id.popup2_close);

        suchigood.setText(dust);
        suchisoso.setText(dust);
        suchibad.setText(dust);

        if (Integer.parseInt(dust)<30){
            boldtext.setText("좋음");
            con2.setImageResource(R.drawable.darkhappy);
            suchisoso.setVisibility(View.INVISIBLE);
            suchibad.setVisibility(View.INVISIBLE);
            pointbad.setVisibility(View.INVISIBLE);
            pointsoso.setVisibility(View.INVISIBLE);
            con_good.setVisibility(View.VISIBLE);
            con_soso.setVisibility(View.INVISIBLE);
            con_bad.setVisibility(View.INVISIBLE);

        }else if (Integer.parseInt(dust)<80){
            boldtext.setText("보통");
            con2.setImageResource(R.drawable.darksoso);
            suchigood.setVisibility(View.INVISIBLE);
            suchibad.setVisibility(View.INVISIBLE);
            pointbad.setVisibility(View.INVISIBLE);
            pointgood.setVisibility(View.INVISIBLE);
            con_good.setVisibility(View.INVISIBLE);
            con_soso.setVisibility(View.VISIBLE);
            con_bad.setVisibility(View.INVISIBLE);

        }else if (Integer.parseInt(dust)>=80){
            boldtext.setText("나쁨");
            con2.setImageResource(R.drawable.darkbad);
            suchigood.setVisibility(View.INVISIBLE);
            suchisoso.setVisibility(View.INVISIBLE);
            pointgood.setVisibility(View.INVISIBLE);
            pointsoso.setVisibility(View.INVISIBLE);
            con_good.setVisibility(View.INVISIBLE);
            con_soso.setVisibility(View.INVISIBLE);
            con_bad.setVisibility(View.VISIBLE);

        }
        popup2_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg2.dismiss();
            }
        });
    }



    // popup3_humid 호출
    public void callhumidpopup(){
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg3 = new Dialog(context);
        // 액티비티의 타이틀바를 숨긴다.
        dlg3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg3.setContentView(R.layout.popup3_humid);
        // 커스텀 다이얼로그를 노출한다.
        dlg3.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView text1 = dlg3.findViewById(R.id.text1);
        final TextView text2 = dlg3.findViewById(R.id.text2);
        final TextView text3 = dlg3.findViewById(R.id.text3);
        final ImageView pointgood = dlg3.findViewById(R.id.pointgood);
        final ImageView pointsoso = dlg3.findViewById(R.id.pointsoso);
        final ImageView pointlittlebad = dlg3.findViewById(R.id.pointlittlebad);
        final ImageView pointbad = dlg3.findViewById(R.id.pointbad);

        final Button popup3_close = dlg3.findViewById(R.id.popup3_close);


        double d_temp = Double.parseDouble(temp);
        double d_humid = Double.parseDouble(humid)*0.01;
        double thi = 1.8*d_temp-0.55*(1-d_humid)*(1.8*d_temp-26)+32;

        int thi_int = Integer.parseInt(String.valueOf(Math.round(thi)));
        text1.setText(String.valueOf(thi_int));


        if(thi_int<65){
            text2.setText("전원 쾌적함을 느끼는 수치입니다.");
            pointsoso.setVisibility(View.INVISIBLE);
            pointlittlebad.setVisibility(View.INVISIBLE);
            pointbad.setVisibility(View.INVISIBLE);
        }
        else if(thi_int<75){
            text2.setText("10%의 사람이 불쾌감을 느끼는 수치입니다.");
            text3.setText("야외활동시 가벼운 옷을 입고,\n수분을 충분히 섭취합니다.");
            pointgood.setVisibility(View.INVISIBLE);
            pointlittlebad.setVisibility(View.INVISIBLE);
            pointbad.setVisibility(View.INVISIBLE);
        }
        else if(thi_int<80){
            text2.setText("50%의 사람이 불쾌감을 느끼는 수치입니다.");
            text3.setText("에어컨이나 제습기를 이용해\n실내 온습도를 조절하면 좋습니다.");
            pointgood.setVisibility(View.INVISIBLE);
            pointsoso.setVisibility(View.INVISIBLE);
            pointbad.setVisibility(View.INVISIBLE);
        }
        else{
            text2.setText("전원 불쾌감을 느끼는 수치입니다.");
            text3.setText("야외활동을 자제해주세요!");
            pointgood.setVisibility(View.INVISIBLE);
            pointsoso.setVisibility(View.INVISIBLE);
            pointlittlebad.setVisibility(View.INVISIBLE);
        }

        popup3_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg3.dismiss();
            }
        });
    }


}

