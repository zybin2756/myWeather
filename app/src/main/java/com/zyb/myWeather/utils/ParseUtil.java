package com.zyb.myWeather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.City;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/2/24 0024.
 *
 * 解释服务器返回的城市信息,并保存到数据库
 */

public class ParseUtil {

    public synchronized static boolean parseProvinceResponce(myWeatherDB weatherDB, String response){
        if(weatherDB != null && !TextUtils.isEmpty(response)) {
            String[] provinces = response.split(",");
            if (provinces != null && provinces.length > 0) {
                Province province = new Province();
                for(String p : provinces) {
                    String[] tmp = p.split("\\|");
                    province.setProvince_name(tmp[1]);
                    province.setProvince_code(tmp[0]);
                    weatherDB.saveProvince(province);
                    tmp = null;
                }
                province = null;
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean parseCityResponce(myWeatherDB weatherDB, String response,int province_id){
        if(weatherDB != null && !TextUtils.isEmpty(response)) {
            String[] cities = response.split(",");
            if (cities != null && cities.length > 0) {
                    City city = new City();
                for(String c : cities){
                    String[] tmp = c.split("\\|");
                    city.setCity_name(tmp[1]);
                    city.setCity_code(tmp[0]);
                    city.setProvince_id(province_id);
                    weatherDB.saveCity(city);
                    tmp = null;
                }
                city = null;
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean parseCountyResponce(myWeatherDB weatherDB, String response,int city_id){
        if(weatherDB != null && !TextUtils.isEmpty(response)) {
            String[] counties = response.split(",");
            if (counties != null && counties.length > 0) {
                County county = new County();
                for(String c : counties){
                    String[] tmp = c.split("\\|");
                    county.setCounty_name(tmp[1]);
                    county.setCounty_code(tmp[0]);
                    county.setCity_id(city_id);
                    weatherDB.saveCounty(county);
                    tmp = null;
                }
                county = null;
                return true;
            }
        }
        return false;
    }

    public synchronized static String parseWeatherResponce(myWeatherDB weatherDB, String response){
        if(weatherDB != null && !TextUtils.isEmpty(response)){
            String[] codes = response.split("\\|");
            weatherDB.updateUserCity(codes[0],codes[1]);
            return codes[1];
        }
        return "";
    }

    public synchronized static boolean parswWeatherInfo(Context ctx,String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            jsonObject = jsonObject.getJSONObject("weatherinfo");
            String code = jsonObject.getString("cityid");
            String city = jsonObject.getString("city");
            String temp1 = jsonObject.getString("temp1");
            String temp2 = jsonObject.getString("temp2");
            String weather = jsonObject.getString("weather");
            String ptime = jsonObject.getString("ptime");

            SharedPreferences sharedPreferences = ctx.getSharedPreferences(code,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("city",city);
            editor.putString("temp1",temp1);
            editor.putString("temp2",temp2);
            editor.putString("weather",weather);
            editor.putString("ptime",ptime);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }
}
