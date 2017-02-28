package com.zyb.myWeather.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class ActivityController {
    public static List<Activity> listController = new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        listController.add(activity);
    }

    public static void removeActivity(Activity activity){
        listController.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity : listController){
            if(activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
