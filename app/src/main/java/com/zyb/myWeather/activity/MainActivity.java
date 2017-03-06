package com.zyb.myWeather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zyb.myWeather.R;
import com.zyb.myWeather.adapter.MainPagerAdapter;
import com.zyb.myWeather.utils.Constants;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class MainActivity extends FragmentActivity {

    private ViewPager weatherViewPager = null;
    private MainPagerAdapter pagerAdapter = null;

    private MyApplication app = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        weatherViewPager = (ViewPager) findViewById(R.id.weatherViewPager);
        app = (MyApplication) getApplication();
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),app);
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
                case Constants.FINISH_ADD_CITY:
                    pagerAdapter.updateFrameList();
                    pagerAdapter.notifyDataSetChanged();
                    weatherViewPager.setCurrentItem(app.getUserCityList().size());
                    break;
                case Constants.REFRESH_ADD_CITY:
                    weatherViewPager.setCurrentItem(data.getIntExtra("index",0));
                    break;
            }
        }
    }
}
