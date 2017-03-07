package com.zyb.myWeather.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.zyb.myWeather.activity.MyApplication;
import com.zyb.myWeather.activity.WeatherFragmentActivity;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.UserCity;
import com.zyb.myWeather.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private boolean[] isUpdate = null;
    private MyApplication app = null;
    private List<WeatherFragmentActivity> weatherList = null;
    public MainPagerAdapter(FragmentManager fm, MyApplication app) {
        super(fm);
        this.app = app;
        updateFrameList();
    }

    @Override
    public Fragment getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return app.getUserCityList().size();
    }

    public void updateFrameList() {
        weatherList = new ArrayList<>();
        for(UserCity c : app.getUserCityList()){
//            LogUtil.i(c.getCounty_name());
            WeatherFragmentActivity weatherFragmentActivity = new WeatherFragmentActivity();
            Bundle bundle = new Bundle();
            bundle.putInt("id",c.getId());
            bundle.putString("code",c.getCounty_code());
            bundle.putString("weather_code",c.getWeather_code());
            weatherFragmentActivity.setArguments(bundle);
            weatherList.add(weatherFragmentActivity);
        }
    }
}
