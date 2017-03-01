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
                for(String p : provinces){
                    String[] tmp = p.split("\\|");
                    Province province = new Province();
                    province.setProvince_name(tmp[1]);
                    province.setProvince_code(tmp[0]);
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean parseCityResponce(myWeatherDB weatherDB, String response,int province_id){
        if(weatherDB != null && !TextUtils.isEmpty(response)) {
            String[] cities = response.split(",");
            if (cities != null && cities.length > 0) {
                for(String c : cities){
                    String[] tmp = c.split("\\|");
                    City city = new City();
                    city.setCity_name(tmp[1]);
                    city.setCity_code(tmp[0]);
                    city.setProvince_id(province_id);
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean parseCountyResponce(myWeatherDB weatherDB, String response,int city_id){
        if(weatherDB != null && !TextUtils.isEmpty(response)) {
            String[] counties = response.split(",");
            if (counties != null && counties.length > 0) {
                for(String c : counties){
                    String[] tmp = c.split("\\|");
                    County county = new County();
                    county.setCounty_name(tmp[0]);
                    county.setCounty_code(tmp[1]);
                    county.setCity_id(city_id);
                    LogUtil.i("countyname:"+tmp[0]);
                    LogUtil.i("countycode:"+tmp[1]);
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
