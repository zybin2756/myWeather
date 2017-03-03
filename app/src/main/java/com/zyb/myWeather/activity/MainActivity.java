package com.zyb.myWeather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zyb.myWeather.R;
import com.zyb.myWeather.adapter.MainPagerAdapter;
import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class MainActivity extends FragmentActivity {

    private ViewPager weatherViewPager = null;
    private MainPagerAdapter pagerAdapter = null;
    private List<Fragment> fragmentList = null;
    private myWeatherDB db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        weatherViewPager = (ViewPager) findViewById(R.id.weatherViewPager);
        db = myWeatherDB.getInstance(this);
        fragmentList = new ArrayList<>();
        List<County> countyList = db.loadUserCity();
        for(County county : countyList){
            WeatherFragmentActivity weatherFragmentActivity = new WeatherFragmentActivity();
            Bundle bundle = new Bundle();
            bundle.putString("code",county.getCounty_code());
            weatherFragmentActivity.setArguments(bundle);
            fragmentList.add(weatherFragmentActivity);
        }

        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),fragmentList);
        weatherViewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_exit:
                finish();
                break;

            case R.id.menu_manageCity:{
                Intent intent = new Intent(this,CityManagerActivity.class);
                startActivityForResult(intent, Constants.START_ADD_CITY);
                break;
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.START_ADD_CITY){
            switch (resultCode){

            }
        }
    }
}
