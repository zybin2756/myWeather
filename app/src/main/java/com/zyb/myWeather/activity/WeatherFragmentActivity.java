package com.zyb.myWeather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zyb.myWeather.R;
import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.utils.HttpRequestListener;
import com.zyb.myWeather.utils.HttpUtil;
import com.zyb.myWeather.utils.LogUtil;
import com.zyb.myWeather.utils.ParseUtil;


/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class WeatherFragmentActivity extends Fragment {
    private myWeatherDB db = null;
    private int id = 0;
    private String code = "";
    private String weather_code = "";


    private TextView txt_temp = null;
    private TextView txt_cityWeather = null;
    private TextView txt_updatetime = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id   = getArguments().getInt("id");
        code = getArguments().getString("code");
        weather_code = getArguments().getString("weather_code");

        db = myWeatherDB.getInstance(getContext());
    }

    private void loadWeatherInfo(){
        if(!weather_code.equals("0")){
            HttpUtil.loadWeatherInfo(weather_code,new HttpRequestListener(){

                @Override
                public void OnFinish(String response) {
                    updateHandler.sendEmptyMessage(1);
                }

                @Override
                public void OnError(Exception e) {

                }
            });
        }else{
            HttpUtil.loadUserCityWeather(code,new HttpRequestListener(){
                @Override
                public void OnFinish(String response) {
                    weather_code = ParseUtil.parseWeatherResponce(db,response);
                    if(weather_code != ""){
                        loadWeatherInfo();
                    }else{
                        updateHandler.sendEmptyMessage(0);
                    }
                }

                @Override
                public void OnError(Exception e) {

                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment,container,false);
        txt_temp = (TextView) view.findViewById(R.id.txt_temp);
        txt_cityWeather = (TextView) view.findViewById(R.id.txt_cityWeather);
        txt_updatetime = (TextView) view.findViewById(R.id.txt_updatetime);
        loadWeatherInfo();
        return view;
    }


    Handler updateHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:

                    break;

                case 0:
                    txt_cityWeather.setText("加载天气信息失败");
                    break;
            }
        }
    };

}
