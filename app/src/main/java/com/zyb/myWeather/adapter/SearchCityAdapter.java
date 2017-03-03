package com.zyb.myWeather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zyb.myWeather.R;
import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.County;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class SearchCityAdapter extends BaseAdapter {

    List<County> countyList = new ArrayList<>();
    Context ctx = null;
    myWeatherDB db = null;
    public SearchCityAdapter(Context ctx)  {
        super();
        this.ctx = ctx;
        db = myWeatherDB.getInstance(ctx);
    }

    public List<County> getCountyList() {
        return countyList;
    }

    public void setCountyList(List<County> countyList) {
        this.countyList = null;
        this.countyList = countyList;
    }

    @Override
    public int getCount() {
        return countyList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = View.inflate(ctx, R.layout.city_item,null);
            vh.txt_cityName = (TextView) convertView.findViewById(R.id.txt_cityName);
            vh.txt_curCity = (TextView) convertView.findViewById(R.id.txt_curCity);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        if(db.isExistCity(countyList.get(position).getCounty_code())){
            vh.txt_curCity.setVisibility(View.VISIBLE);
            vh.txt_curCity.setText("已选择");
        }
        vh.txt_cityName.setText(countyList.get(position).getCounty_name());
        return convertView;
    }

    class ViewHolder{
        TextView txt_cityName;
        TextView txt_curCity;
    }
}
