package com.zyb.myWeather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyb.myWeather.R;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class WeatherFragmentActivity extends Fragment {
    private String code = "";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment,container,false);
        code = getArguments().getString("code");
        ((TextView)view.findViewById(R.id.test)).setText(code);
        return view;
    }
}
