package com.zyb.myWeather.model;

/**
 * Created by Administrator on 2017/3/2.
 */

public class UserCity {
    private int id;
    private  String county_code;
    private  String county_name;

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCounty_code() {
        return county_code;
    }

    public void setCounty_code(String county_code) {
        this.county_code = county_code;
    }
}
