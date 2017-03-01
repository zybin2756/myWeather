package com.zyb.myWeather.utils;

import android.text.TextUtils;

import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.City;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.Province;

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
                    county.setCounty_name(tmp[0]);
                    county.setCounty_code(tmp[1]);
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
}
