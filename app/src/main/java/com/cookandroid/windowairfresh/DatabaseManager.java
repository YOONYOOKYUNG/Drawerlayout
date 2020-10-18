package com.cookandroid.windowairfresh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {
    static final String DB_NAME = "Window.db";   //DB이름
    static final String Window_TABLE_NAME = "Windows"; //Table 이름
    static final String Location_TABLE_NAME = "Location"; //Table 이름
    static final String Station_TABLE_NAME = "Station"; //Table 이름
    static final int DB_VERSION = 1;         //DB 버전

    Context myContext = null;

    private static DatabaseManager myDBManager = null;
    private SQLiteDatabase mydatabase = null;

    //MovieDatabaseManager 싱글톤 패턴으로 구현
    public static DatabaseManager getInstance(Context context)
    {
        if(myDBManager == null)
        {
            myDBManager = new DatabaseManager(context);
        }

        return myDBManager;
    }

    //창문 테이블 만듬.
    private DatabaseManager(Context context) {
        myContext = context;

        //DB Open
        mydatabase = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE,null);


        //창문 Table 생성
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Window_TABLE_NAME +
                "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Address TEXT," +
                "State Boolean);");

        //위치 Table 생성
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Location_TABLE_NAME +
                "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "si TEXT," +
                "gu TEXT," +
                "x TEXT," +
                "y TEXT);");

        //측정소 Table 생성
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Station_TABLE_NAME +
                "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "si TEXT," +
                "gu TEXT," +
                "station TEXT);");

    }


    //Location/Station table 비어있는지 확인
    public boolean isDbEmpty(String TableName) {
        try {
            Cursor c = mydatabase.rawQuery("SELECT * FROM " + TableName, null);
            if (c.moveToFirst()) {
                Log.d("00", "isDbEmpty: not empty");
                return false;
            }
            c.close();
        } catch (SQLiteException e) {
            Log.d("00", "isDbEmpty: doesn't exist");
            return true;
        }
        return true;
    }

    public static final String Station_si = "si";
    public static final String Station_gu = "gu";
    public static final String Station_name = "station";

    //Location 항목 추가 (위도/경도 엑셀db화)
    public long createNote_station(String si, String gu, String name) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(Station_si, si);
        initialValues.put(Station_gu, gu);
        initialValues.put(Station_name, name);

        return mydatabase.insert(Station_TABLE_NAME, null, initialValues);
    }


    public String selectNote_station(String si, String gu) {

        String station = null;
        String sqlSelect = "SELECT * FROM " + Station_TABLE_NAME;
        Cursor cursor = null;

        cursor = mydatabase.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {

            if(si.equals(cursor.getString(1))){
                Log.d("00", "성공1 ");
                if(gu.equals(cursor.getString(2))){
                    station = cursor.getString(3);
                    Log.d("00","location : "+station);
                    break;
                }

            }
        }
        cursor.close();
        return station;
    }



    public static final String Address_si = "si";
    public static final String Address_gu = "gu";
    public static final String Location_x = "x";
    public static final String Location_y = "y";

    //Location 항목 추가 (위도/경도 엑셀db화)
    public long createNote_location(String si, String gu, String x, String y) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(Address_si, si);
        initialValues.put(Address_gu, gu);
        initialValues.put(Location_x, x);
        initialValues.put(Location_y, y);

        return mydatabase.insert(Location_TABLE_NAME, null, initialValues);
    }


    public String selectNote_location(String si, String gu) {

        String location = null;
        String sqlSelect = "SELECT * FROM " + Location_TABLE_NAME;
        Cursor cursor = null;

        cursor = mydatabase.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {

            if(si.equals(cursor.getString(1))){
                Log.d("00", "성공1 ");
                if(gu.equals(cursor.getString(2))){
                    location = cursor.getString(3)+","+cursor.getString(4);
                    Log.d("00","location : "+location);
                    break;
                }

            }
        }
        cursor.close();
        return location;
    }


    //창문추가
    public long insert(ContentValues addRowValue) {
        return mydatabase.insert(Window_TABLE_NAME, null, addRowValue);
    }

    //창문삭제
    public Integer delete (String name) {
        return mydatabase.delete(Window_TABLE_NAME,
                "name = ?", new String[] { name });
    }

    //창문 상태 업데이트
    public int update(ContentValues updateRowValue,
                      String name)
    {
        return mydatabase.update(Window_TABLE_NAME,
                updateRowValue,
                "name= ?",
                new String[] { name });
    }

    //창문db얻어오기
    public ArrayList<WindowDetails> getAll() {
        ArrayList<WindowDetails> array_list = new ArrayList<WindowDetails>();

        String sqlSelect = "SELECT * FROM " + Window_TABLE_NAME;
        Cursor cursor = null;

        cursor = mydatabase.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {
            WindowDetails newAdapter = new WindowDetails();
            newAdapter.setName(cursor.getString(1));
            newAdapter.setAddress(cursor.getString(2));
            newAdapter.setState(Boolean.parseBoolean(cursor.getString(3)));

            array_list.add(newAdapter);
        }

        cursor.close();
        return array_list;
    }
}