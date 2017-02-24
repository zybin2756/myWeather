package com.zyb.myWeather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zyb.myWeather.model.City;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/24 0024.
 * 数据库访问类
 */

public class myWeatherDB{

    private static final String DB_NAME = "myWeather";
    private static final int VERSION = 1;
    private static SQLiteDatabase db = null;
    public static myWeatherDB weatherDB = null;

    private myWeatherDB(Context ctx){
        db = new myWeatherOpenHelper(ctx,DB_NAME,null,VERSION).getWritableDatabase();
    }

    public synchronized static myWeatherDB getInstance(Context ctx){
        if(weatherDB == null){
            weatherDB = new myWeatherDB(ctx);
        }
        return weatherDB;
    }

    public long saveProvince(Province province){
        if(province != null){
            ContentValues value = new ContentValues();
            value.put("province_name",province.getProvince_name());
            value.put("province_code",province.getProvince_code());
            return db.insert("Province",null,value);
        }
        return 0;
    }

    public List<Province> loadProvince(){
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        List<Province> list = null;
        if(cursor.moveToFirst()){
            list = new ArrayList<Province>();
            do{
                Province p = new Province();
                p.setProvince_id(cursor.getInt(cursor.getColumnIndex("province_id")));
                p.setProvince_name(cursor.getString(cursor.getColumnIndex("province_name")));
                p.setProvince_name(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(p);
            }while(cursor.moveToNext());
        }
        return list;
    };

    public long saveCity(City city){
        if(city != null){
            ContentValues value = new ContentValues();
            value.put("city_name",city.getCity_name());
            value.put("city_code",city.getCity_code());
            value.put("province_id",city.getProvince_id());
            return db.insert("City",null,value);
        }
        return 0;
    }

    public List<City> loadCity(int province_id){
        Cursor cursor = db.query("City",null,"province_id = ?",new String[]{String.valueOf(province_id)},null,null,null);
        List<City> list = null;
        if(cursor.moveToFirst()){
            list = new ArrayList<City>();
            do{
                City c = new City();
                c.setCity_id(cursor.getInt(cursor.getColumnIndex("city_id")));
                c.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
                c.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
                c.setProvince_id(province_id);
                list.add(c);
            }while(cursor.moveToNext());
        }
        return list;
    };


    public long saveCounty(County county){
        if(county != null){
            ContentValues value = new ContentValues();
            value.put("county_name",county.getCounty_name());
            value.put("county_code",county.getCounty_code());
            value.put("city_id",county.getCity_id());
            return db.insert("City",null,value);
        }
        return 0;
    }

    public List<County> loadCounty(int city_id){
        Cursor cursor = db.query("County",null,"city_id = ?",new String[]{String.valueOf(city_id)},null,null,null);
        List<County> list = null;
        if(cursor.moveToFirst()){
            list = new ArrayList<County>();
            do{
                County c = new County();
                c.setCounty_id(cursor.getInt(cursor.getColumnIndex("county_id")));
                c.setCounty_name(cursor.getString(cursor.getColumnIndex("county_name")));
                c.setCounty_code(cursor.getString(cursor.getColumnIndex("county_code")));
                c.setCity_id(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(c);
            }while(cursor.moveToNext());
        }
        return list;
    };
}
