package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity_Fragment2 extends Fragment {

    ViewPager2 viewpager;
    TextView tvdate, temp, humid, micro, location_address;
    RelativeLayout templayout, dustlayout, humidlayout, bgbg;
    Boolean nowrain;
    String pty;

    public MainActivity_Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_main_fragment2, container, false);
        viewpager = getActivity().findViewById(R.id.viewpager);



        temp = view.findViewById(R.id.temp);
        micro = view.findViewById(R.id.micro);
        humid = view.findViewById(R.id.humid);
        bgbg = view.findViewById(R.id.bgbg);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
        cal.add(Calendar.DATE,0);
        String today = sdf.format(cal.getTime());
        tvdate = view.findViewById(R.id.tvdate);
        tvdate.setText(today);

        location_address = view.findViewById(R.id.location_address);
        SharedPreferences pf2 = getContext().getSharedPreferences("address",getContext().MODE_PRIVATE);
        location_address.setText(pf2.getString("addr0","서울시 성동구"));


        SharedPreferences pf1 = getContext().getSharedPreferences("fragment2",getContext().MODE_PRIVATE);
        temp.setText(pf1.getString("temp","20"));
        micro.setText(pf1.getString("dust","15"));
        humid.setText(pf1.getString("humid","38"));
        pty=pf1.getString("pty","0");
        if (Integer.parseInt(pty)!=0){
            bgbg.setBackgroundResource(R.drawable.fragment1_rain);
        }

        templayout = view.findViewById(R.id.templayout);
        dustlayout = view.findViewById(R.id.dustlayout);
        humidlayout = view.findViewById(R.id.humidlayout);


        // 주소창 클릭 시 주소 변경
        location_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddressActivity.class));
            }
        });

        //click -> popup1_temp
        templayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity_Popup customDialogPopup1 = new MainActivity_Popup(view.getContext());
                customDialogPopup1.settemp(temp.getText().toString());
                customDialogPopup1.calltemppopup();
            }
        });

        //click -> popup2_dust
        dustlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity_Popup customDialogPopup1 = new MainActivity_Popup(view.getContext());
                customDialogPopup1.setdust(micro.getText().toString());
                customDialogPopup1.calldustpopup();
            }
        });

        //click -> popup3_humid
        humidlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity_Popup customDialogPopup1 = new MainActivity_Popup(view.getContext());
                customDialogPopup1.settemp(temp.getText().toString());
                customDialogPopup1.sethumid(humid.getText().toString());
                customDialogPopup1.callhumidpopup();
            }
        });
        return view;


    }
}
