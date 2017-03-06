package com.zyb.myWeather.activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.UserCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class MyApplication extends Application {
    Context context = null;

    private myWeatherDB db = null;
    private  List<UserCity> userCityList = null;
    public myWeatherDB getDb() {
        return db;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        db = myWeatherDB.getInstance(this.context);
        userCityList = new ArrayList<>();
        refreshData();
    }

    public List<UserCity> getUserCityList() {
        return userCityList;
    }

    public Context getContext(){
        return this.context;
    }

    public void  refreshData(){
        userCityList.clear();
        this.userCityList.addAll(db.loadUserCity());
    }
}
