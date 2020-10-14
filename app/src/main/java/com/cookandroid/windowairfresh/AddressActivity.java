package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;


import com.cookandroid.windowairfresh.R;

public class AddressActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    Button btn;
    private DatabaseManager databaseManager;
    Workbook workbook = null;
    Sheet sheet = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        databaseManager = DatabaseManager.getInstance(this);

        Boolean state_empty = databaseManager.isDbEmpty();
        // Location DB가 비어있다면 엑셀파일을 읽어 추가시킴.
        if (state_empty==Boolean.TRUE) {
            copyExcelDataToDatabase();
        }

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        btn = findViewById(R.id.btn_address_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pf = getSharedPreferences("address", MODE_PRIVATE);
                SharedPreferences.Editor editor = pf.edit();
                editor.putString("addr1", spinner1.getSelectedItem().toString());
                editor.putString("addr2", spinner2.getSelectedItem().toString());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();

            }
        });

    }


    private void copyExcelDataToDatabase() {
        Log.w("ExcelToDatabase", "copyExcelDataToDatabase()");

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

                        databaseManager.createNote(Address_si, Address_gu, Location_x, Location_y);
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