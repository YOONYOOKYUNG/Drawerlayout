package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.os.Bundle;
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
    TextView tvdate,temp1,humid1,micro1;
    RelativeLayout templayout, dustlayout, humidlayout;
    int Start_index,End_index;
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


        temp1 = view.findViewById(R.id.temp1);
        micro1 = view.findViewById(R.id.micro1);
        humid1 = view.findViewById(R.id.humid1);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
        cal.add(Calendar.DATE,0);
        String today = sdf.format(cal.getTime());
        tvdate = view.findViewById(R.id.tvdate);
        tvdate.setText(today);

        templayout = view.findViewById(R.id.templayout);
        dustlayout = view.findViewById(R.id.dustlayout);
        humidlayout = view.findViewById(R.id.humidlayout);


        //click -> popup1_temp
        templayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup customDialogPopup1 = new Popup(view.getContext());
                customDialogPopup1.settemp(temp1.getText().toString());
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
                customDialogPopup1.settemp(temp1.getText().toString());
                customDialogPopup1.sethumid(humid1.getText().toString());
                customDialogPopup1.callhumidpopup();
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                data= getXmlData1();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                Start_index = data.indexOf("PTY:");
                End_index = data.indexOf("/",Start_index);
                String pty = data.substring(Start_index+4,End_index);

                Start_index = data.indexOf("REH:");
                End_index = data.indexOf("/",Start_index);
                String reh = data.substring(Start_index+4,End_index);

                Start_index = data.indexOf("T1H:");
                End_index = data.indexOf("/",Start_index);
                String t1h = data.substring(Start_index+4,End_index);

                data2=getXmlData2();

                temp1.setText(t1h);
                humid1.setText(reh);
                micro1.setText(data2);

            }
        }).start();
        return view;
    }



    String getXmlData1(){

        StringBuffer buffer1=new StringBuffer();
        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        cal2.add(Calendar.DATE,0);
        String today = sdf2.format(cal2.getTime());

        String queryUrl1= "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst?serviceKey=pbjfdUXNOnav6q2Tb%2BrkkjcxUA4dZZVfL2joSHTUXE32G6h%2Fj8ZabTsIin%2Bn7DQ%2BwJt676jVMiEEui560v3UZA%3D%3D&numOfRows=10&pageNo=1&base_date=" +today+ "&base_time=0600&nx=55&ny=127";


        try {
            URL url1= new URL(queryUrl1);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is1= url1.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is1, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer1.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("category")){
                            xpp.next();
                            buffer1.append(xpp.getText());
                            buffer1.append(":");
                        }
                        else if(tag.equals("obsrValue")){
                            xpp.next();
                            buffer1.append(xpp.getText());//cpId
                            buffer1.append("/");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer1.toString();//StringBuffer 문자열 객체 반환
    }


    String getXmlData2(){
        StringBuffer buffer2=new StringBuffer();

        String queryUrl2= "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&pageNo=1&numOfRows=1&ServiceKey=pbjfdUXNOnav6q2Tb%2BrkkjcxUA4dZZVfL2joSHTUXE32G6h%2Fj8ZabTsIin%2Bn7DQ%2BwJt676jVMiEEui560v3UZA%3D%3D&ver=1.3";
        try {
            URL url2= new URL(queryUrl2);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is2= url2.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is2, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("pm10Value")){
                            xpp.next();
                            buffer2.append(xpp.getText());
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


        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer2.toString();//StringBuffer 문자열 객체 반환
    }

}
