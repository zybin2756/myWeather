package com.zyb.myWeather.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class MyApplication extends Application {
    Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public Context getContext(){
        return this.context;
    }
}
