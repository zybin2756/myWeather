package com.zyb.myWeather.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zyb.myWeather.utils.ActivityController;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
