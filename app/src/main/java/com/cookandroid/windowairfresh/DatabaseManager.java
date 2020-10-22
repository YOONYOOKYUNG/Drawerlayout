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
    static final String Timeline_TABLE_NAME = "Timelines"; //Table 이름
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

        //mydatabase.execSQL("DROP TABLE " + Timeline_TABLE_NAME );

        //활동 기록 Table 생성
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Timeline_TABLE_NAME +
                "("  + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Date TEXT," +
                "Time TEXT," +
                "Content TEXT," +
                "State TEXT);");

    }

    // Window 추가
    public long insert(ContentValues addRowValue) {
        return mydatabase.insert(Window_TABLE_NAME, null, addRowValue);
    }

    // Window 삭제
    public Integer delete (String name) {
        return mydatabase.delete(Window_TABLE_NAME, "name = ?", new String[] { name });
    }

    // Window 상태 업데이트
    public int update(ContentValues updateRowValue, String name) {
        return mydatabase.update(Window_TABLE_NAME, updateRowValue, "name= ?", new String[] { name });
    }
    // Window db얻어오기
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


    //Location 항목 추가 (위도/경도 엑셀db화)
    public static final String Address_si = "si";
    public static final String Address_gu = "gu";
    public static final String Location_x = "x";
    public static final String Location_y = "y";

    public long createNote_location(String si, String gu, String x, String y) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(Address_si, si);
        initialValues.put(Address_gu, gu);
        initialValues.put(Location_x, x);
        initialValues.put(Location_y, y);

        return mydatabase.insert(Location_TABLE_NAME, null, initialValues);
    }

    //Location 항목 조회
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


    //Station 항목 추가 (위도/경도 엑셀db화)
    public static final String Station_si = "si";
    public static final String Station_gu = "gu";
    public static final String Station_name = "station";

    public long createNote_station(String si, String gu, String name) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(Station_si, si);
        initialValues.put(Station_gu, gu);
        initialValues.put(Station_name, name);

        return mydatabase.insert(Station_TABLE_NAME, null, initialValues);
    }

    //Station 항목 조회
    public String selectNote_station(String si, String gu) {

        String station = null; //station에 임의의 값 넣기

        String sqlSelect = "SELECT * FROM " + Station_TABLE_NAME; //측정소 데이터 조회
        Cursor cursor = null; //커서 : 데이터를 읽음

        cursor = mydatabase.rawQuery(sqlSelect, null); //위에서 조회한 측정소 데이터를 읽음

        while (cursor.moveToNext()) { //커서를 다음 행으로 이동

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



    // Timeline 추가
    public static final String Timeline_data = "Date";
    public static final String Timeline_time = "Time";
    public static final String Timeline_content = "Content";
    public static final String Timeline_state = "State";

    public long timeline_insert(String date, String time, String content, String state){

        ContentValues timeValues = new ContentValues();
        timeValues.put(Timeline_data, date);
        timeValues.put(Timeline_time, time);
        timeValues.put(Timeline_content, content);
        timeValues.put(Timeline_state, state);
        return mydatabase.insert(Timeline_TABLE_NAME, null, timeValues);
    }

    // Timeline 조회
    public ArrayList<TimelineDetails> timeline_select() {
        ArrayList<TimelineDetails> timeline_list = new ArrayList<TimelineDetails>();

        String sqlSelect = "SELECT * FROM " + Timeline_TABLE_NAME;
        Cursor cursor = null;

        cursor = mydatabase.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {
            TimelineDetails newAdapter = new TimelineDetails();
            newAdapter.setDate(cursor.getString(1));
            newAdapter.setTime(cursor.getString(2));
            newAdapter.setContent(cursor.getString(3));
            newAdapter.setState(cursor.getString(4));
            timeline_list.add(0, newAdapter);
        }

        cursor.close();
        return timeline_list;
    }

}