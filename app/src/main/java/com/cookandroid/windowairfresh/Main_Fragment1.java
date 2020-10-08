package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main_Fragment1 extends Fragment {
    ViewPager viewpager;
    TextView tvdate,thermometer,humid,micro;
    RelativeLayout therlayout, dustlayout, humidlayout;

    public Main_Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        int a=1;
        if(a==1) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try { Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();} //1초뒤 다이얼로그 띄우기
                    Intent intent = new Intent (getContext(), HelpActivity.class);
                    startActivity(intent);
                }
            }).start();

        }
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_main_fragment1, container, false);
        viewpager = getActivity().findViewById(R.id.viewpager);


        thermometer = view.findViewById(R.id.thermometer);
        micro = view.findViewById(R.id.micro);
        humid = view.findViewById(R.id.humid);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
        cal.add(Calendar.DATE,0);
        String today = sdf.format(cal.getTime());
        tvdate = view.findViewById(R.id.tvdate);
        tvdate.setText(today);

        therlayout = view.findViewById(R.id.therlayout);
        dustlayout = view.findViewById(R.id.dustlayout);
        humidlayout = view.findViewById(R.id.humidlayout);


        //click -> popup1_temp
        therlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup customDialogPopup1 = new Popup(view.getContext());
                customDialogPopup1.settemp(thermometer.getText().toString());
                customDialogPopup1.calltemppopup();
            }
        });

        //click -> popup2_dust

        dustlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup customDialogPopup1 = new Popup(view.getContext());
                customDialogPopup1.setdust(micro.getText().toString());
                customDialogPopup1.calldustpopup();
            }
        });

        //click -> popup3_humid

        humidlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup customDialogPopup1 = new Popup(view.getContext());
                customDialogPopup1.settemp(thermometer.getText().toString());
                customDialogPopup1.sethumid(humid.getText().toString());
                customDialogPopup1.callhumidpopup();
            }
        });


        return view;
    }

}
