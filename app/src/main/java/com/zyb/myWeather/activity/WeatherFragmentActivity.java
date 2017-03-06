package com.zyb.myWeather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyb.myWeather.R;
import com.zyb.myWeather.db.myWeatherDB;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class WeatherFragmentActivity extends Fragment {
    private myWeatherDB db = null;
    private int id = 0;
    private String code = "";
    private String weather_code = "";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id   = getArguments().getInt("id");
        code = getArguments().getString("code");
        weather_code = getArguments().getString("weather_code");
        if(weather_code != "0"){

        }else{

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment,container,false);
        return view;
    }
}
