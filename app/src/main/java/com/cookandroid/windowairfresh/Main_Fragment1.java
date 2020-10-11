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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main_Fragment1 extends Fragment {
    ViewPager viewpager;
    TextView tvdate,thermometer1,humid1,micro1;
    RelativeLayout therlayout, dustlayout, humidlayout;
    String data, data2;

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


        thermometer1 = view.findViewById(R.id.thermometer1);
        micro1 = view.findViewById(R.id.micro1);
        humid1 = view.findViewById(R.id.humid1);

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
                customDialogPopup1.settemp(thermometer1.getText().toString());
                customDialogPopup1.calltemppopup();
            }
        });

        //click -> popup2_dust

        dustlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup customDialogPopup1 = new Popup(view.getContext());
                customDialogPopup1.setdust(micro1.getText().toString());
                customDialogPopup1.calldustpopup();
            }
        });

        //click -> popup3_humid

        humidlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup customDialogPopup1 = new Popup(view.getContext());
                customDialogPopup1.settemp(thermometer1.getText().toString());
                customDialogPopup1.sethumid(humid1.getText().toString());
                customDialogPopup1.callhumidpopup();
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                data= getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                data2=getXmlData2();

            }
        }).start();
        return view;
    }



    String getXmlData(){

        StringBuffer buffer=new StringBuffer();
        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        cal2.add(Calendar.DATE,0);
        String today = sdf2.format(cal2.getTime());



        String queryUrl= "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst\n" +
                "?serviceKey=pbjfdUXNOnav6q2Tb%2BrkkjcxUA4dZZVfL2joSHTUXE32G6h%2Fj8ZabTsIin%2Bn7DQ%2BwJt676jVMiEEui560v3UZA%3D%3D&numOfRows=10&pageNo=1\n" +
                "&base_date=" +today+
                "&base_time=0600&nx=55&ny=127";

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("category")){
                            buffer.append("자료구분문자");
                            xpp.next();
                            buffer.append(xpp.getText());
                        }
                        else if(tag.equals("obsrValue")){
                            buffer.append("예보 값");
                            xpp.next();
                            buffer.append(xpp.getText());//cpId
                            //buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과종료..줄바꿈

                        break;

                }
                eventType= xpp.next();
            }
            String st = buffer.substring(27,29);
            String te = buffer.substring(56,60);
            humid1.setText(st);
            thermometer1.setText(te);

        } catch (Exception e) {
            e.printStackTrace();
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환


    }
    String getXmlData2(){
        StringBuffer buffer=new StringBuffer();

        String queryUrl= "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/" +
                "getMsrstnAcctoRltmMesureDnsty?stationName=%EC%84%B1%EB%8F%99%EA%B5%AC&dataTerm=month&" +
                "pageNo=1&numOfRows=2&ServiceKey=pbjfdUXNOnav6q2Tb%2BrkkjcxUA4dZZVfL2joSHTUXE32G6h%2Fj8ZabTsIin%2Bn7DQ%2BwJt676jVMiEEui560v3UZA%3D%3D&ver=1.3";
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("pm10Value")){
                            buffer.append("수치");
                            xpp.next();
                            buffer.append(xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과종료..줄바꿈

                        break;

                }
                eventType= xpp.next();
            }
            String ts = buffer.substring(2,4);
            micro1.setText(ts);

        } catch (Exception e) {
            e.printStackTrace();
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환


    }




}
