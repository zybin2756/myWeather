package com.zyb.myWeather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class myWeatherOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_PROVINCE = "Create table Province( id integer primary key auto_increment," +
                                                "province_name text not null,"+
                                                "province_code text not null";

    private static final String CREATE_CITY = "Create table City( id integer primary key auto_increment," +
                                            "city_name text not null,"+
                                            "city_code text not null"+
                                            "province_id integer";

    private static final String CREATE_COUNTY = "Create table County( id integer primary key auto_increment," +
            "County_name text not null,"+
            "County_code text not null"+
            "city_id integer";

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
