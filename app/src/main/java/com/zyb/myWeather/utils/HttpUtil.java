package com.zyb.myWeather.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class HttpUtil {

    /*
        arg1: 要访问的地址
        arg2: 回调接口
        作用: 访问指定网址,获取到网址的源代码后返回
     */
    public static void sendHttpRequset(final String address, final HttpRequestListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream in = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    StringBuilder response = new StringBuilder();
                    String line = "";
                    while((line = bufferedReader.readLine())!=null){
                        response.append(line);
                    }

                    if(listener != null){
                        listener.OnFinish(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(listener != null){
                        listener.OnError(e);
                    }
                } finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
