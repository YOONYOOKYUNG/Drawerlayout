package com.cookandroid.windowairfresh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {
    static final String DB_NAME = "Window.db";   //DB이름
    static final String TABLE_NAME = "Windows"; //Table 이름
    static final int DB_VERSION = 1;			//DB 버전

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

    private DatabaseManager(Context context)
    {
        myContext = context;

        //DB Open
        mydatabase = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE,null);

        //Table 생성
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Address TEXT," +
                "State Boolean);");
    }

    public long insert(ContentValues addRowValue)
    {
        return mydatabase.insert(TABLE_NAME, null, addRowValue);
    }

    public Integer delete (String name)
    {
        return mydatabase.delete(TABLE_NAME,
                "name = ?", new String[] { name });
    }

    public ArrayList<WindowListAdapter> getAll()
    {
        ArrayList<WindowListAdapter> array_list = new ArrayList<WindowListAdapter>();

        String sqlSelect = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;

        cursor = mydatabase.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {
            WindowListAdapter newAdapter = new WindowListAdapter();

            newAdapter.setName(cursor.getString(1));
            newAdapter.setAddress(cursor.getString(2));
            newAdapter.setState(Boolean.parseBoolean(cursor.getString(3)));

            array_list.add(newAdapter);
        }

        return array_list;
    }
}
