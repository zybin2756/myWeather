package com.zyb.myWeather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zyb.myWeather.model.City;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.Province;
import com.zyb.myWeather.model.UserCity;
import com.zyb.myWeather.utils.Constants;
import com.zyb.myWeather.utils.LogUtil;

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
        long id = 0;
        if(province != null){
            ContentValues value = new ContentValues();
            value.put("province_name",province.getProvince_name());
            value.put("province_code",province.getProvince_code());
            id = db.insert("Province",null,value);
            value = null;
        }
        return id;
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
                p.setProvince_code(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(p);
            }while(cursor.moveToNext());
        }
        return list;
    };

    public long saveCity(City city){
        long id = 0;
        if(city != null){
            ContentValues value = new ContentValues();
            value.put("city_name",city.getCity_name());
            value.put("city_code",city.getCity_code());
            value.put("province_id",city.getProvince_id());
            id = db.insert("City",null,value);
            value = null;
        }
        return id;
    }

    public int loadCityCount(int province_id){
        int count = 0;
        Cursor cursor = db.query("City",new String[]{"count(*)"},"province_id = ?",new String[]{String.valueOf(province_id)},null,null,null);
        if(cursor.moveToFirst()){
            count = cursor.getInt(cursor.getColumnIndex("count(*)"));
        }
        return count;
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
        cursor.close();
        return list;
    };

    public List<City> loadAllCity(){
        Cursor cursor = db.query("City",null,null,null,null,null,null);
        List<City> list = null;
        if(cursor.moveToFirst()){
            list = new ArrayList<City>();
            do{
                City c = new City();
                c.setCity_id(cursor.getInt(cursor.getColumnIndex("city_id")));
                c.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
                c.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
                list.add(c);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    };


    public long saveCounty(County county){
        long id = 0;
        if(county != null){
            ContentValues value = new ContentValues();
            value.put("county_name",county.getCounty_name());
            value.put("county_code",county.getCounty_code());
            value.put("city_id",county.getCity_id());
            id = db.insert("County",null,value);
            value = null;
        }
        return id;
    }


    public int loadCountyCount(int city_id){
        int count = 0;
        Cursor cursor = db.query("County",new String[]{"count(*)"},"city_id = ?",new String[]{String.valueOf(city_id)},null,null,null);
        if(cursor.moveToFirst()){
            count = cursor.getInt(cursor.getColumnIndex("count(*)"));
        }
        return count;
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
        cursor.close();
        return list;
    };


    public void clearData(int type, int id ){
        switch(type){
            case Constants.ALL:{
                db.delete("Province",null,null);
                db.delete("City",null,null);
                db.delete("County",null,null);
                break;
            }
            case Constants.PROVINCE:
                db.delete("Province",null,null);
                break;
            case Constants.CITY:
                if(id != 0){
                    db.delete("City", "province_id=?", new String []{String.valueOf(id)});
                }else {
                    db.delete("City", null, null);
                }
            case Constants.COUNTY:
                if(id != 0){
                    db.delete("County", "city_id=?", new String []{String.valueOf(id)});
                }else {
                    db.delete("County", null, null);
                }
                break;
        }
    }

    public List<County> searchCityByKey(String key){

        String sql = "select county_name,county_code,city_name,province_name from County as a,City as b,Province as c"
                    +" where a.city_id = b.city_id and b.province_id = c.province_id and a.county_name like '%"+key+"%'";
        Cursor cursor = db.rawQuery(sql,null);
        List<County> countyList = null;

        if(cursor.moveToFirst()){
            StringBuilder stringBuilder = new StringBuilder();
            countyList = new ArrayList<>();
            do{
                stringBuilder.setLength(0);
                stringBuilder.append(cursor.getString(0));
                stringBuilder.append("-");
                stringBuilder.append(cursor.getString(2));
                stringBuilder.append(", ");
                stringBuilder.append(cursor.getString(3));
                County county = new County();
                county.setCounty_name(stringBuilder.toString());
                county.setCounty_code(cursor.getString(1));
                countyList.add(county);
            }while(cursor.moveToNext());
            stringBuilder = null;
        }
        cursor.close();

        return countyList;
    }

    public void deleteUserCity(List<UserCity > list){
        if( list == null ) return;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("delete from UserCity where county_name not in ( ");
        for(UserCity userCity : list){
            stringBuilder.append("'"+userCity.getCounty_name()+"',");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(")");
        db.execSQL(stringBuilder.toString());
    }

    public List<UserCity> loadUserCity(){
        List<UserCity> list = new ArrayList<>();
        Cursor cursor = db.query("UserCity",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                UserCity userCity = new UserCity();
                userCity.setId(cursor.getInt(0));
                userCity.setCounty_name(cursor.getString(1));
                userCity.setCounty_code(cursor.getString(2));
                userCity.setWeather_code(cursor.getString(3));
                list.add(userCity);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean isExistCity(String code){
        Cursor cursor = db.query("UserCity",null,"county_code = ?",new String[]{code},null,null,null);

        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }
    public boolean saveUserCity(County county){
        if(county != null && (!isExistCity(county.getCounty_code()))) {
            ContentValues value = new ContentValues();
            value.put("county_code",county.getCounty_code());
            value.put("county_name",county.getCounty_name().split("-")[0]);
            value.put("weather_code","0");
            db.insert("UserCity",null,value);
            return true;
        }
        return false;
    };

    public void updateUserCity(String code,String weather_code){
        ContentValues value = new ContentValues();
        value.put("weather_code",weather_code);
        db.update("UserCity",value,"county_code = ?", new String[]{code});
    }
}
