package com.zyb.myWeather.utils;

/**
 * Created by Administrator on 2017/2/24 0024.
 */


/*
    访问网址的回调接口
 */
public interface HttpRequestListener {

    public void OnFinish(String response);

    public void OnError(Exception e);
}
