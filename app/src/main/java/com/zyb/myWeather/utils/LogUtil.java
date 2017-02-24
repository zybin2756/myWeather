package com.zyb.myWeather.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class LogUtil {
    private static final int VERBOSE = 6;
    private static final int DEBUG   = 5;
    private static final int INFO    = 4;
    private static final int WARN    = 3;
    private static final int ERROR   = 2;
    private static final int FORBID  = 1;
    private static final int DEFAULT = 7;

    public static void i(final String info){
        if(DEFAULT > INFO){
            Log.i("info",info);
        }
    }

    public static void e(final String info){
        if(DEFAULT > ERROR){
            Log.e("error",info);
        }
    }
}
