package com.zyb.myWeather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/2/24 0024.
 * 数据库帮助类
 */

public class myWeatherOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_PROVINCE = "Create table Province( province_id integer primary key autoincrement," +
                                                "province_name text,"+
                                                "province_code text )";

    private static final String CREATE_CITY = "Create table City( city_id integer primary key autoincrement," +
                                            "city_name text,"+
                                            "city_code text,"+
                                            "province_id integer )";

    private static final String CREATE_COUNTY = "Create table County( county_id integer primary key autoincrement," +
            "County_name text,"+
            "County_code text,"+
            "city_id integer )";

    public myWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }
}
