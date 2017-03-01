package com.zyb.myWeather.activity;

import android.os.Bundle;

import com.zyb.myWeather.R;
import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.City;
import com.zyb.myWeather.model.Province;
import com.zyb.myWeather.utils.Constants;
import com.zyb.myWeather.utils.HttpRequestListener;
import com.zyb.myWeather.utils.HttpUtil;
import com.zyb.myWeather.utils.ParseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class SplashActivity extends BaseActivity {
    private  myWeatherDB db = null;
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

    private void prepare(){
        db = myWeatherDB.getInstance(this);
        prepareProvinceInfo();
        prepareCityInfo();
    }

    //加载各省信息
    private void prepareProvinceInfo(){
        List<Province> provinceList = db.loadProvince();
        if(provinceList == null){
            db.clearData(Constants.PROVINCE, 0);
            loadProvinceInfo();
        }
        provinceList = null;
        System.gc();
    }

    private void loadProvinceInfo(){
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
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void prepareCountyInfo(){

    }

}
