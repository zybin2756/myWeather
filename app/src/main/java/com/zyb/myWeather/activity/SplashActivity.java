package com.zyb.myWeather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zyb.myWeather.R;
import com.zyb.myWeather.adapter.CityManagerAdapter;
import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.City;
import com.zyb.myWeather.model.Province;
import com.zyb.myWeather.utils.Constants;
import com.zyb.myWeather.utils.HttpRequestListener;
import com.zyb.myWeather.utils.HttpUtil;
import com.zyb.myWeather.utils.LogUtil;
import com.zyb.myWeather.utils.ParseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class SplashActivity extends BaseActivity {
    private  myWeatherDB db = null;
    private  int countOfCounty = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        prepare();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private void prepare(){
        db = myWeatherDB.getInstance(this);
        long start = System.currentTimeMillis();
        prepareProvinceInfo();
        prepareCityInfo();
        prepareCountyInfo();
        long end = System.currentTimeMillis();
        LogUtil.i("load time :"+ (end - start));
        handler.sendEmptyMessageDelayed(0,3000);
    }

    //加载各省信息
    private void prepareProvinceInfo(){
        LogUtil.i("prepareProvinceInfo..");
        List<Province> provinceList = db.loadProvince();
        if(provinceList == null){
            db.clearData(Constants.PROVINCE, 0);
            loadProvinceInfo();
        }
        provinceList = null;
        System.gc();
    }

    private void loadProvinceInfo(){
        LogUtil.i("loadProvinceInfo..");
        String address = "http://www.weather.com.cn/data/list3/city.xml";
        HttpUtil.sendHttpRequset(address, new HttpRequestListener() {
            @Override
            public void OnFinish(String response) {
                ParseUtil.parseProvinceResponce(db,response);
                prepareCityInfo();
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void prepareCityInfo(){
        List<Province> provinceList = db.loadProvince();

        if(provinceList == null) return;

        for(Province province : provinceList){
            int p_id = province.getProvince_id();
            if(db.loadCityCount(p_id) == 0){
                countOfCounty++;
                db.clearData(Constants.CITY, p_id);
                loadCityInfo(p_id,province.getProvince_code());
            }
        }
    }

    //加载各城市信息
    private void loadCityInfo(final int p_id, String code){
        String address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
        HttpUtil.sendHttpRequset(address, new HttpRequestListener() {
            @Override
            public void OnFinish(String response) {
                ParseUtil.parseCityResponce(db,response,p_id);
                countOfCounty--;
                if(countOfCounty == 0){
                    LogUtil.i("begin to prepareCountyInfo after loadCityInfo");
                    prepareCountyInfo();
                }
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void prepareCountyInfo(){
        List<City> cityList = db.loadAllCity();

        if(cityList == null){
            LogUtil.i("null .................");
            return;
        }
        for(City city :cityList){
            int c_id = city.getCity_id();
            if(db.loadCountyCount(c_id) == 0){
                db.clearData(Constants.COUNTY,c_id);
                loadCuntyInfo(c_id,city.getCity_code());
            }
        }
    }


    //加载各县信息
    private void loadCuntyInfo(final int c_id, String code){
        String address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
        HttpUtil.sendHttpRequset(address, new HttpRequestListener() {
            @Override
            public void OnFinish(String response) {
                ParseUtil.parseCountyResponce(db,response,c_id);
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
