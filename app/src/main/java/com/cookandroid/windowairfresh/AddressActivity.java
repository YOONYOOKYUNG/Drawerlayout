package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;


public class AddressActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> addr_spin1, addr_spin2;
    Spinner spinner1, spinner2;
    Button btn;
    private DatabaseManager databaseManager;
    static final String Location_TABLE_NAME = "Location"; //Table 이름
    static final String Station_TABLE_NAME = "Station"; //Table 이름
    Workbook workbook = null;
    Sheet sheet = null;
    String location,station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        databaseManager = DatabaseManager.getInstance(this);

        Boolean state_empty_location = databaseManager.isDbEmpty(Location_TABLE_NAME);
        // Location DB가 비어있다면 엑셀파일을 읽어 추가시킴.
        if (state_empty_location==Boolean.TRUE) {
            copyExcelDataToDatabase_location();
        }
        Boolean state_empty_station = databaseManager.isDbEmpty(Station_TABLE_NAME);
        // Station DB가 비어있다면 엑셀파일을 읽어 추가시킴.
        if (state_empty_station==Boolean.TRUE) {
            copyExcelDataToDatabase_station();
        }


        //시,도,구 에 맞춰 xml 변경
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);

        addr_spin1 = ArrayAdapter.createFromResource(this,R.array.spinner_do,android.R.layout.simple_spinner_dropdown_item);
        addr_spin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(addr_spin1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(addr_spin1.getItem(i).equals("서울특별시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_seoul, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("경기도")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_gyunggi, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("인천광역시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_incheon, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("광주광역시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_gwangju, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("대구광역시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_daegu, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("대전광역시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_daejeon, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("부산광역시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_busan, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("울산광역시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_ulsan, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("강원도")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_gangwon, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("경상남도")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_gyeongsangnam, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("경상북도")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_gyeongsangbuk, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("전라남도")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_jeonlanam, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("전라북도")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_jeonlabuk, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("충청남도")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_chungcheongnam, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("충청북도")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_chungcheongbuk, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("세종특별자치시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_sejong, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                } else if(addr_spin1.getItem(i).equals("제주특별자치시")){
                    addr_spin2 = ArrayAdapter.createFromResource(AddressActivity.this, R.array.spinner_do_jeju, android.R.layout.simple_spinner_dropdown_item);
                    addr_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(addr_spin2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn = findViewById(R.id.btn_address_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_si = spinner1.getSelectedItem().toString();
                String user_gu = spinner2.getSelectedItem().toString();

                location = databaseManager.selectNote_location(user_si,user_gu);
                station = databaseManager.selectNote_station(user_si,user_gu);

                Log.d("00",location);
                Log.d("00",station);

                String location2[] = location.split(",");

                SharedPreferences pf = getSharedPreferences("address", MODE_PRIVATE);
                SharedPreferences.Editor editor = pf.edit();
                editor.putString("addr1", location2[0]);
                editor.putString("addr2", location2[1]);
                editor.putString("station",station);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

    }


    private void copyExcelDataToDatabase_location() {
        AssetManager am = getResources().getAssets();

        try {
            //assets폴더에 있는 엑셀파일을 가져옴.(폴더가 없다면 생성해야 함) xls 파일만 지원원
            InputStream is = am.open("location.xls");
            workbook = Workbook.getWorkbook(is); //엑셀파일 인식


            if (workbook != null) { //엑셀파일이 있다면
                sheet = workbook.getSheet(0); //엑셀파일에서 첫번째 sheet 인식

                if (sheet != null) {
                    int nRowStartIndex = 0; //실제 데이터가 시작되는 ROW 지점
                    //주입할 column 갯수 (type,num,location) *rowid는
                    // 테이블 생성시 자동 생성되므로 excel data row만 주입
                    int nMaxColumn = 4;
                    //실제 데이터가 끝나나는ROW 지점
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0; //실제 데이터가 시작되는 Column 지점

                    for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) { //DB 주입

                        String Address_si = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String Address_gu = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                        String Location_x = sheet.getCell(nColumnStartIndex + 2, nRow).getContents();
                        String Location_y = sheet.getCell(nColumnStartIndex + 3, nRow).getContents();

                        databaseManager.createNote_location(Address_si, Address_gu, Location_x, Location_y);
                    }
                } else {
                    System.out.println("Sheet is null");
                }
            } else {
                System.out.println("WorkBook is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
    private void copyExcelDataToDatabase_station() {
        AssetManager am = getResources().getAssets();

        try {
            //assets폴더에 있는 엑셀파일을 가져옴.(폴더가 없다면 생성해야 함) xls 파일만 지원원
            InputStream is = am.open("station.xls");
            workbook = Workbook.getWorkbook(is); //엑셀파일 인식


            if (workbook != null) { //엑셀파일이 있다면
                sheet = workbook.getSheet(0); //엑셀파일에서 첫번째 sheet 인식

                if (sheet != null) {
                    int nRowStartIndex = 0; //실제 데이터가 시작되는 ROW 지점
                    //주입할 column 갯수 (type,num,location) *rowid는
                    // 테이블 생성시 자동 생성되므로 excel data row만 주입
                    int nMaxColumn = 3;
                    //실제 데이터가 끝나나는ROW 지점
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0; //실제 데이터가 시작되는 Column 지점

                    for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) { //DB 주입

                        String Station_si = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String Station_gu = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                        String Station_name = sheet.getCell(nColumnStartIndex + 2, nRow).getContents();

                        databaseManager.createNote_station(Station_si, Station_gu, Station_name);
                    }
                } else {
                    System.out.println("Sheet is null");
                }
            } else {
                System.out.println("WorkBook is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}